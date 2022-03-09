package fr.dawan.dwcomparator.tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.dawan.dwcomparator.services.CrawlerService;

@Component
public class ScheduledTasks {

	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private CrawlerService crawlerService;
	
	
	/**
	 * Update not completed crawler
	 * Every day at 04 am
	 * Can be triggered by API
	 */
	@Scheduled(cron = "0 0 4 1/1 * ?") //Every day at 04 am (voir CronMaker.com)
	@Async("taskExecutor")
	public void updateNotCompletedCrawlers() {
		logger.info("updateNotCompletedCrawlers : ", dateFormat.format(new Date()));
		try {
			crawlerService.taskUpdateNotCompletedCrawlers();
		} catch (Exception e) {
			logger.error("TASK : updateNotCompletedCrawlers", e);
		}
	}
	
	//TODO définir un tâche planifiée qui se déclenche chaque jour à 2h du matin
	//récupérer chercher le dernier crawler lancé par TrainingCenter
	//vérifier la date ne dépasse la fréquence de crawl et elle dépasse, on relance le crawl
}