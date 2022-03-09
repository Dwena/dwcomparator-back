package fr.dawan.dwcomparator.controller;

import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-crawl")
public class CrawlTestController {

	@GetMapping(produces = "text/plain")
	public String test() {
		String res = "";

		// exemple1 l jsoup
		try {
			// naviguer avec Selenium
			System.setProperty("webdriver.chrome.driver", "c:/tempBidon/chromedriver_win32/chromedriver.exe");

			long start1 = System.currentTimeMillis();
			// Create driver object for CHROME browser
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setJavascriptEnabled(true);
			ChromeOptions opt = new ChromeOptions();
			opt.merge(cap);
			WebDriver driver = new ChromeDriver(opt);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			// driver.manage().window().maximize();
			driver.get("https://www.cegos.fr/search?q=java");
			res = driver.getPageSource();

			driver.close();
			Document htmlDoc = Jsoup.parse(res);

			// Autre solution avec JSoup (attention : jsoup est plus rapide mais n'exécute
			// pas le JS)
			// risque de ne pas pouvoir

			Elements elts = htmlDoc.select("div.card--result");
			for (Element element : elts) {
				System.out.println(element.select(".card__title").text());
				System.out.println(element.select(".card__price").text());
				System.out.println("--------------------------------");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}

