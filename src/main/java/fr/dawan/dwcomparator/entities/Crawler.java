package fr.dawan.dwcomparator.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import fr.dawan.dwcomparator.interceptors.Auditable;

@Entity
@Table
public class Crawler implements Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Version
	private int version;

	@Column
	private LocalDateTime startDate;

	@Column
	private LocalDateTime endDate;

	@ManyToOne
	private TrainingCenter trainingCenter;

	private int indexedPages;// nombre de pages indexées

	@Column
	
	private String errorLog;
	
	public Crawler() {
		// startDate = new Date();
		startDate = LocalDateTime.now();
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

	public TrainingCenter getTrainingCenter() {
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

	public void setTrainingCenter(TrainingCenter trainingCenter) {
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
