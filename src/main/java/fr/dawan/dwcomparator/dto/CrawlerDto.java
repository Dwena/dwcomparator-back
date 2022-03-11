package fr.dawan.dwcomparator.dto;

import java.time.LocalDateTime;


public class CrawlerDto {
	private long id;

	private int version;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	private TrainingCenterDto trainingCenter;

	private int indexedPages;// nombre de pages indexées

	private String errorLog;
	
	public CrawlerDto() {
		// 
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public TrainingCenterDto getTrainingCenter() {
		return trainingCenter;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public void setTrainingCenter(TrainingCenterDto trainingCenter) {
		this.trainingCenter = trainingCenter;
	}

	public int getIndexedPages() {
		return indexedPages;
	}

	public void setIndexedPages(int indexedPages) {
		this.indexedPages = indexedPages;
	}

	public String getErrorLog() {
		return errorLog;
	}

	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}

	
}
