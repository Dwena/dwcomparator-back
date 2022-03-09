package fr.dawan.dwcomparator.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.dwcomparator.dto.CountDto;
import fr.dawan.dwcomparator.dto.CrawlerDto;
import fr.dawan.dwcomparator.services.CrawlerService;

@RestController
@RequestMapping("/api/crawlers")
public class CrawlerController {

	@Autowired
	private CrawlerService crawlerService;

	@GetMapping(value = "/trainingcenter/{id}", produces = "text/plain")
	public String crawlTrainingCenter(@PathVariable("id") long id) {
		try {
			crawlerService.crawlTrainingCenter(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "CRAWL TrainingCenter " + id + " IN PROGRESS";
	}

	@GetMapping(value = "/url/{urlToCrawlId}", produces = "text/plain")
	public String crawlUrl(@PathVariable("urlToCrawlId") long urlToCrawlId) {
		try {
			crawlerService.crawlUrl(urlToCrawlId, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "CRAWL URL " + urlToCrawlId + " IN PROGRESS";
	}

	@GetMapping(value = "/update-not-completed", produces = "text/plain")
	public String updateNotCompletedCrawlers() throws Exception {
		crawlerService.taskUpdateNotCompletedCrawlers();
		return "OK";
	}
	
	@GetMapping(value = "/{page}/{size}", produces = "application/json")
	public @ResponseBody List<CrawlerDto> getAllByPage(@PathVariable("page") int page,
			@PathVariable(value = "size") int size) {
		return crawlerService.getAll(page, size,"");
	}
	
	@GetMapping(value = "/{page}/{size}/{search}", produces = "application/json")
	public @ResponseBody List<CrawlerDto> getAllByPage(@PathVariable("page") int page,
			@PathVariable(value = "size") int size, 
			@PathVariable(value = "search", required = false) Optional<String> search) {
		if(search.isPresent())
			return crawlerService.getAll(page, size, search.get());
		else
			return crawlerService.getAll(page, size, "");
	}

	// api/crawlers/{id}
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
	public CrawlerDto getById(@PathVariable("id") long id) {
		return crawlerService.getById(id);
	}

	@GetMapping(value = "/count", produces = "application/json")
	public CountDto count() {
		return crawlerService.count("");
	}
	
	@GetMapping(value = "/count/{search}", produces = "application/json")
	public CountDto count(@PathVariable(name = "search", required = false) Optional<String> search) {
		if (search.isPresent())
			return crawlerService.count(search.get());
		else
			return crawlerService.count("");
	}
	
}
