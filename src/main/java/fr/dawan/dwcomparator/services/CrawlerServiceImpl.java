package fr.dawan.dwcomparator.services;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import fr.dawan.dwcomparator.dto.CountDto;
import fr.dawan.dwcomparator.dto.CrawlerDto;
import fr.dawan.dwcomparator.dto.DtoTools;
import fr.dawan.dwcomparator.entities.Crawler;
import fr.dawan.dwcomparator.entities.IndexedPage;
import fr.dawan.dwcomparator.entities.IndexedSession;
import fr.dawan.dwcomparator.entities.UrlToCrawl;
import fr.dawan.dwcomparator.repositories.CrawlerRepository;
import fr.dawan.dwcomparator.repositories.IndexedPageRepository;
import fr.dawan.dwcomparator.repositories.TrainingCenterRepository;
import fr.dawan.dwcomparator.repositories.UrlToCrawlRepository;

@Service
@Transactional
public class CrawlerServiceImpl implements CrawlerService {

	/**
	 * chemin vers le Web driver chrome ou firefox ou autre (voir
	 * application.properties)
	 */
	@Value("${webdriver.chrome.driver}")
	private String chromeDriverLocation;

	/**
	 * instanciation du webdriver pour avoir un singleton
	 */
	@Bean
	public WebDriver webDriver() {
		System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setJavascriptEnabled(true);
		ChromeOptions opt = new ChromeOptions();
		// opt.addArguments("--disable-notifications");
		opt.merge(cap);
		return new ChromeDriver(opt);
	};

	@Autowired
	private WebDriver webDriver;

	@Autowired
	private TrainingCenterRepository trainingCenterRepository;

	@Autowired
	private CrawlerRepository crawlerRepository;

	@Autowired
	private UrlToCrawlRepository urlToCrawlRepository;

	@Autowired
	private IndexedPageRepository indexedPageRepository;

	@Override
	public void crawlTrainingCenter(long trainingCenterId) throws Exception {
		// logger.info(">>> crawlTrainingCenter Id :" + trainingCenterId);
		Crawler currentCrawler = new Crawler();
		currentCrawler.setTrainingCenter(trainingCenterRepository.findById(trainingCenterId).get());
		crawlerRepository.saveAndFlush(currentCrawler);

		StringBuilder errorLog = new StringBuilder();
		// aller chercher l'ensemble des UrlToCrawl associées au centre
		List<Long> urls = urlToCrawlRepository.findIdsByTrainingCenterId(trainingCenterId);
		// puis appeler crawlUrl
		CompletableFuture<?>[] cfs = new CompletableFuture<?>[urls.size()];
		int i = 0;
		for (Long urlToCrawlId : urls) {
			try {
				cfs[i] = crawlUrl(urlToCrawlId, currentCrawler);

			} catch (Exception e) {
				PrintWriter sw = new PrintWriter(new StringWriter());
				e.printStackTrace(sw);
				errorLog.append(sw.toString());
			}
			i++;
		}
		// On attends que toutes les URLs soient indexées
		try {
			CompletableFuture.allOf(cfs).join();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		currentCrawler.setEndDate(LocalDateTime.now());
		currentCrawler.setErrorLog(errorLog.toString());
		currentCrawler.setIndexedPages(indexedPageRepository.countByCrawlerId(currentCrawler.getId()));
		crawlerRepository.saveAndFlush(currentCrawler);

	}

	@Override
	@Async("taskExecutor")
	public CompletableFuture<Integer> crawlUrl(long urlToCrawlId, Crawler currentCrawler) throws Exception {
		// logger.info(">>> crawling url Id :" + urlToCrawlId);
		webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		UrlToCrawl urlToCrawl = null;
		Optional<UrlToCrawl> opt = urlToCrawlRepository.findById(urlToCrawlId);
		if (opt.isPresent()) {
			urlToCrawl = opt.get();

			boolean isNewCrawler = false;
			if (currentCrawler == null) {
				currentCrawler = new Crawler();
				currentCrawler.setTrainingCenter(urlToCrawl.getTrainingCenter());
				crawlerRepository.saveAndFlush(currentCrawler);
				isNewCrawler = true;
			}

			int nb = 0;
			if (urlToCrawl.getSubLevelNumber() == 0) {
				webDriver.get(urlToCrawl.getFullUrl());
				String pageContent = webDriver.getPageSource();
				saveIndexPage(currentCrawler, urlToCrawl, urlToCrawl.getFullUrl(), pageContent,
						Jsoup.parse(pageContent));
				nb = 1;
			} else {
				int level = 1;
				String baseUrl = urlToCrawl.getTrainingCenter().getBaseSiteUrl();
				String urlLocation = urlToCrawl.getFullUrl();

				crawlUrlRec(level, urlToCrawl.getSubLevelNumber(), baseUrl, urlToCrawl, urlLocation, currentCrawler);
			}
			if (isNewCrawler) {
				currentCrawler.setEndDate(LocalDateTime.now());
				nb = indexedPageRepository.countByCrawlerId(currentCrawler.getId());
				currentCrawler.setIndexedPages(nb);
				crawlerRepository.saveAndFlush(currentCrawler);
			}

			return CompletableFuture.completedFuture(nb);
		}

		return CompletableFuture.completedFuture(0);

	}

	public void crawlUrlRec(int level, int subLevelNumber, String baseUrl, UrlToCrawl urlToCrawl, String urlLocation,
			Crawler currentCrawler) {
		if (level > subLevelNumber)
			return;

		webDriver.get(urlLocation);

		if (urlToCrawl.getPreActions() != null && level == 1) {
			String[] preActionSelectors = urlToCrawl.getPreActions().split(";");
			for (String cssSelector : preActionSelectors) {
				List<WebElement> eltsSelenium = webDriver.findElements(By.cssSelector(cssSelector));
				for (WebElement webElement : eltsSelenium) {
					webElement.click();
				}
			}
		}

		String pageContent = webDriver.getPageSource();
		Document htmlDoc = Jsoup.parse(pageContent, urlLocation);

		Elements elts = htmlDoc.select(urlToCrawl.getSearchExpressionByLevel().get(level));
		for (Element element : elts) {
			String url = element.attr("href");
			if (!url.startsWith("http://") && !url.startsWith("https://"))
				url = baseUrl + url;

			// System.out.println(">>URL Elt : "+ url);
			webDriver.get(url);
			htmlDoc = Jsoup.parse(webDriver.getPageSource(), url);

			saveIndexPage(currentCrawler, urlToCrawl, htmlDoc.location(), pageContent, htmlDoc);

			if ((level + 1) <= subLevelNumber)
				crawlUrlRec(level + 1, subLevelNumber, baseUrl, urlToCrawl, url, currentCrawler);
		}
	}

	public void saveIndexPage(Crawler currentCrawler, UrlToCrawl urlToCrawl, String crawledUrl, String pageContent,
			Document htmlDoc) {
		// page formation
		IndexedPage indexedPage = new IndexedPage();
		try {
			String title = null;
			String[] titleExpressions = urlToCrawl.getTitleSearchExpression().split(";");
			for (String titleExp : titleExpressions) {
				title = applyReplaceExpression(urlToCrawl.getTitleReplaceExpression(),
						htmlDoc.select(titleExp).first().text().trim());
				if (title != null && title.startsWith("Formation") && title.length() > 10) {
					title = title.substring(10);
					break;
				}
			}
			indexedPage.setTitle(title);

		} catch (Exception e) {
			// title not found
			// e.printStackTrace();
		}

		if (indexedPage.getTitle() != null && !indexedPage.getTitle().equals("")) {
			indexedPage.setCrawler(currentCrawler);
			indexedPage.setUrl(crawledUrl);
			indexedPage.setPageContent(pageContent);
			indexedPage.setTaxesIncluded(urlToCrawl.getTrainingCenter().isPricesIncludingTaxes());

			try {
				String reference = null;
				String[] referenceExpressions = urlToCrawl.getReferenceSearchExpression().split(";");
				for (String referenceExp : referenceExpressions) {
					reference = applyReplaceExpression(urlToCrawl.getReferenceReplaceExpression(),
							htmlDoc.select(referenceExp).first().text().trim());
					if (reference != null && !reference.trim().equals("")) {
						break;
					}
				}
				indexedPage.setReference(reference);
			} catch (Exception e) {
				// ref not found
				// e.printStackTrace();
			}

			try {
				String duration = null;
				String[] durationExpressions = urlToCrawl.getDurationSearchExpression().split(";");
				for (String durationExp : durationExpressions) {
					duration = applyReplaceExpression(urlToCrawl.getDurationReplaceExpression(),
							htmlDoc.select(durationExp).first().text().trim());
					if (duration != null && !duration.trim().equals("")) {
						break;
					}
				}
				indexedPage.setDuration(duration);
			} catch (Exception e) {
				// duration not found
				// e.printStackTrace();
			}

			try {
				String priceStr = applyReplaceExpression(urlToCrawl.getPriceReplaceExpression(),
						htmlDoc.select(urlToCrawl.getPriceSearchExpression()).first().text());

				priceStr = priceStr.replaceAll(",", ".").replaceAll(" ", "").replaceAll("\\s+", "")
						.replaceAll("\\u00A0", "").trim();
				float price = 0F;
				if (!priceStr.equals("")) {
					price = Float.parseFloat(priceStr);
					indexedPage.setPrice(price);
				}
			} catch (Exception e) {
				// price not found or mal formatted
				// e.printStackTrace();
			}

			try {
				String prerequisites = null;
				String[] prerequisitesExpressions = urlToCrawl.getPrerequisitesSearchExpression().split(";");
				for (String prerequisitesExp : prerequisitesExpressions) {
					prerequisites = applyReplaceExpression(urlToCrawl.getPrerequisitesReplaceExpression(),
							htmlDoc.select(prerequisitesExp).first().text().trim());
					if (prerequisites != null && !prerequisites.trim().equals("")
							&& !prerequisites.trim().equalsIgnoreCase("Prérequis :")
							&& !prerequisites.trim().equalsIgnoreCase("Prérequis"))
						break;
				}
				indexedPage.setPrerequisites(prerequisites);
			} catch (Exception e) {
				// prerequis not found
				// e.printStackTrace();
			}

			try {
				String audience = null;
				String[] audienceExpressions = urlToCrawl.getAudienceSearchExpression().split(";");
				for (String audienceExp : audienceExpressions) {
					audience = applyReplaceExpression(urlToCrawl.getAudienceReplaceExpression(),
							htmlDoc.select(audienceExp).first().text().trim());
					if (audience != null && !audience.trim().equals("") && !audience.trim().equalsIgnoreCase("Public :")
							&& !audience.trim().equalsIgnoreCase("Public"))
						break;
				}
				indexedPage.setAudience(audience);
			} catch (Exception e) {
				// audience not found
				// e.printStackTrace();
			}

			try {
				String objectives = null;
				String[] objectivesExpressions = urlToCrawl.getObjectivesSearchExpression().split(";");
				for (String objectivesExp : objectivesExpressions) {
					objectives = applyReplaceExpression(urlToCrawl.getObjectivesReplaceExpression(),
							htmlDoc.select(objectivesExp).first().text().trim());
					if (objectives != null && !objectives.trim().equals("")) {
						break;
					}
				}
				indexedPage.setObjectives(objectives);
			} catch (Exception e) {
				// objectives not found
				// e.printStackTrace();
			}

			try {
				String certification = null;
				String[] certificationExpressions = urlToCrawl.getCertificationSearchExpression().split(";");
				for (String certificationExp : certificationExpressions) {
					certification = applyReplaceExpression(urlToCrawl.getCertificationReplaceExpression(),
							htmlDoc.select(certificationExp).first().text().trim());
					if (certification != null && !certification.trim().equals("")) {
						break;
					}
				}
				indexedPage.setCertification(certification);
			} catch (Exception e) {
				// certification not found
				// e.printStackTrace();
			}
			// cpf
			try {
				String certifCPF = null;
				String[] certifCPFExpressions = urlToCrawl.getEligibleForCPFSearchExpression().split(";");
				for (String certifCPFExp : certifCPFExpressions) {
					certifCPF = applyReplaceExpression(urlToCrawl.getEligibleForCPFReplaceExpression(),
							htmlDoc.select(certifCPFExp).first().text().trim());
					if (certifCPF != null && certifCPF.length() > 0
							&& (certifCPF.equalsIgnoreCase("CPF") || indexedPage.getCertification().contains("cpf"))) {
						indexedPage.setEligibleForCPF(true);
						break;
					}
				}

			} catch (Exception e) {
				// eligible CPF
				// e.printStackTrace();
			}

			// PreAction to reach Program
			try {
				if (urlToCrawl.getPreProgramActions() != null) {
					String[] preProgSelectors = urlToCrawl.getPreProgramActions().split(";");
					for (String cssSelector : preProgSelectors) {
						List<WebElement> eltsSelenium = webDriver.findElements(By.cssSelector(cssSelector));
						for (WebElement webElement : eltsSelenium) {
							webElement.click();
						}
					}
				}

				String trainingProgram = null;
				String[] trainingProgramExpressions = urlToCrawl.getTrainingProgramSearchExpression().split(";");
				for (String trainingProgramExp : trainingProgramExpressions) {
					trainingProgram = applyReplaceExpression(urlToCrawl.getTrainingProgramReplaceExpression(),
							htmlDoc.select(trainingProgramExp).html().trim());
					if (trainingProgram != null && !trainingProgram.trim().equals("")) {
						break;
					}
				}
				indexedPage.setTrainingProgram(trainingProgram);

			} catch (Exception e) {
				// program not found
				// e.printStackTrace();
			}
			try {
				// PreSessionsActions pour capturer les sessions
				if (urlToCrawl.getPreSessionsActions() != null) {
					String[] preActionSelectors = urlToCrawl.getPreSessionsActions().split(";");
					for (String cssSelector : preActionSelectors) {
						List<WebElement> eltsSelenium = webDriver.findElements(By.cssSelector(cssSelector));
						for (WebElement webElement : eltsSelenium) {
							webElement.click();
						}
					}
				}

				// Preaction to see subscription url
				if (urlToCrawl.getPreSubscriptionUrlActions() != null) {
					String[] preSubSelectors = urlToCrawl.getPreSubscriptionUrlActions().split(";");
					for (String cssSelector : preSubSelectors) {
						List<WebElement> eltsSelenium = webDriver.findElements(By.cssSelector(cssSelector));
						for (WebElement webElement : eltsSelenium) {
							webElement.click();
						}
					}
				}

				// training sessions
				Elements sessionsElts = htmlDoc.select(urlToCrawl.getSessionsSearchExpression());

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(urlToCrawl.getDateFormatExpression(),
						new Locale("fr", "FR"));

				for (Element sessionElt : sessionsElts) {
					try {
						IndexedSession indexedSession = new IndexedSession();
						indexedSession.setIndexedPage(indexedPage);

						// location
						if (urlToCrawl.getLocationSearchExpression() != null
								&& !urlToCrawl.getLocationSearchExpression().equals("")) {
							try {
								indexedSession.setLocation(applyReplaceExpression(
										urlToCrawl.getLocationReplaceExpression(),
										sessionElt.select(urlToCrawl.getLocationSearchExpression()).text().trim()));
							} catch (Exception ex) {
								// ex.printStackTrace();
								//global locations if not available inside session
								indexedSession.setLocation(applyReplaceExpression(
										urlToCrawl.getLocationReplaceExpression(),
										htmlDoc.select(urlToCrawl.getLocationSearchExpression()).text().trim()));
							}
						}

						// startDate
						if (urlToCrawl.getStartDateSearchExpression() != null
								&& !urlToCrawl.getStartDateSearchExpression().equals("")) {
							try {
								String startDateStr = applyReplaceExpression(urlToCrawl.getStartDateReplaceExpression(),
										sessionElt.select(urlToCrawl.getStartDateSearchExpression()).text().trim());
								// TODO + "00:00:00" pour gérer l'horaire ou sinon supprimer l'heure
								LocalDateTime startDate = LocalDateTime.parse(startDateStr, formatter);
								indexedSession.setStartDate(startDate);
							} catch (Exception ex) {
								// ex.printStackTrace();
							}
						}

						// endDate
						if (urlToCrawl.getEndDateSearchExpression() != null
								&& !urlToCrawl.getEndDateSearchExpression().equals("")) {
							try {
								String endDateStr = applyReplaceExpression(urlToCrawl.getEndDateReplaceExpression(),
										sessionElt.select(urlToCrawl.getEndDateSearchExpression()).text().trim());
								// TODO + "00:00:00" pour gérer l'horaire ou sinon supprimer l'heure
								LocalDateTime endDate = LocalDateTime.parse(endDateStr, formatter);
								indexedSession.setEndDate(endDate);
							} catch (Exception ex) {
								// ex.printStackTrace();
							}
						}

						// guaranteed
						if (urlToCrawl.getGuaranteedSearchExpression() != null
								&& !urlToCrawl.getGuaranteedSearchExpression().equals("")) {
							try {
								String guaranteedStr = null;
								String[] guaranteedSearchExpressions = urlToCrawl.getGuaranteedSearchExpression()
										.split(";");
								for (String guaranteedExp : guaranteedSearchExpressions) {
									guaranteedStr = applyReplaceExpression(urlToCrawl.getEligibleForCPFReplaceExpression(),
											sessionElt.select(guaranteedExp).first().text().trim());
									if (guaranteedStr != null && guaranteedStr.length() > 0) {
										indexedSession.setGuaranteed(true);
										break;
									}
								}
							} catch (Exception e) {
								// e.printStackTrace();
							}
						}

						// price
						if (urlToCrawl.getSessionPriceSearchExpression() == null
								|| urlToCrawl.getSessionPriceSearchExpression().trim().equals("")) {
							indexedSession.setPrice(indexedPage.getPrice());
						} else {
							try {
								String priceStr = applyReplaceExpression(urlToCrawl.getPriceReplaceExpression(),
										sessionElt.select(urlToCrawl.getPriceSearchExpression()).first().text());

								priceStr = priceStr.replaceAll(",", ".").replaceAll(" ", "").replaceAll("\\s+", "")
										.replaceAll("\\u00A0", "").trim();
								float price = 0F;
								if (!priceStr.equals("")) {
									price = Float.parseFloat(priceStr);
									indexedPage.setPrice(price);
								}
							} catch (Exception ex) {
								// ex.printStackTrace();
							}
						}

						// TODO subscriptionUrl
						if (urlToCrawl.getSubscriptionUrlSearchExpression() != null
								&& !urlToCrawl.getSubscriptionUrlSearchExpression().equals("")) {
							try {
								String subscriptionUrl = applyReplaceExpression(urlToCrawl.getSubscriptionUrlReplaceExpression(),
										sessionElt.select(urlToCrawl.getSubscriptionUrlSearchExpression()).text().trim());
								indexedSession.setSubscriptionUrl(subscriptionUrl);
							} catch (Exception ex) {
								// ex.printStackTrace();
							}
						}
						
						indexedPage.getSessions().add(indexedSession);
					} catch (Exception e) {
						// session error
						// e.printStackTrace();
					}
				}
			} catch (Exception e) {
				// sessions not found
				// e.printStackTrace();
			}
			indexedPageRepository.saveAndFlush(indexedPage);
		}
	}

	@Override
	public void updateIndexedPagesNumber(long crawlerId) {
		int nb = indexedPageRepository.countByCrawlerId(crawlerId);
		Crawler crawler = crawlerRepository.findById(crawlerId).get();
		crawler.setIndexedPages(nb);
		crawler.setEndDate(LocalDateTime.now());
		crawler.setErrorLog("Not completed crawler - updated by automatic task");
		crawlerRepository.saveAndFlush(crawler);
	}

	@Override
	public List<Long> getNotCompletedCrawlersIds() {
		return crawlerRepository.getNotCompletedCrawlersIds();
	}

	@Override
	public boolean taskUpdateNotCompletedCrawlers() throws Exception {
		List<Long> crawlerIds = getNotCompletedCrawlersIds();
		for (Long id : crawlerIds) {
			updateIndexedPagesNumber(id);
		}
		return true;
	}

	/**
	 * Apply a pattern to replace many expressions (to separate with ;) in an input
	 * string by empty string
	 * 
	 * @param replaceExpression
	 * @param input
	 * @return
	 */
	public String applyReplaceExpression(String replaceExpression, String input) {
		if (replaceExpression != null) {
			String[] regPatterns = replaceExpression.split(";");
			for (String regex : regPatterns) {
				input = input.replaceAll(regex, "");
			}
		}
		return input.trim();
	}

	@Override
	public List<CrawlerDto> getAll(int page, int max, String search) {
		List<Crawler> lst = crawlerRepository.findAllByTrainingCenterNameContaining(search, PageRequest.of(page, max))
				.get().collect(Collectors.toList());
		List<CrawlerDto> result = new ArrayList<CrawlerDto>();
		for (Crawler c : lst) {
			result.add(DtoTools.convert(c, CrawlerDto.class));
		}

		return result;
	}

	@Override
	public CrawlerDto getById(long id) {
		Optional<Crawler> c = crawlerRepository.findById(id);
		if (c.isPresent())
			return DtoTools.convert(c.get(), CrawlerDto.class);

		return null;
	}

	@Override
	public CountDto count(String search) {
		return new CountDto(crawlerRepository.countByTrainingCenterNameContaining(search));
	}

}
