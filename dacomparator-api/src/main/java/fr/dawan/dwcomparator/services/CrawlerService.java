package fr.dawan.dwcomparator.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import fr.dawan.dwcomparator.dto.CountDto;
import fr.dawan.dwcomparator.dto.CrawlerDto;
import fr.dawan.dwcomparator.entities.Crawler;

public interface CrawlerService {

void crawlTrainingCenter(long trainingCenterId) throws Exception;
	
	CompletableFuture<Integer> crawlUrl(long urlToCrawlId, Crawler currentCrawler) throws Exception;
	
	boolean taskUpdateNotCompletedCrawlers() throws Exception;
	
	void updateIndexedPagesNumber(long crawlerId);
	
	List<Long> getNotCompletedCrawlersIds();
	
	String applyReplaceExpression(String replaceExpression, String input);

	List<CrawlerDto> getAll(int page, int size, String search);

	CrawlerDto getById(long id);
	
	CountDto count(String search);
}
