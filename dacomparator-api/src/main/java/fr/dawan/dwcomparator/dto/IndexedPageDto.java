package fr.dawan.dwcomparator.dto;

import java.time.LocalDateTime;


public class IndexedPageDto {
	private long id;

	private int version;

	private LocalDateTime creationDate;

	
	private CrawlerDto crawler;

	private String url;

	//private String pageContent;

	private String reference;
	private String title;
	private String duration;
	private float price;
	private boolean taxesIncluded;

	private String trainingProgram;
	private String objectives;
	private String prerequisites;
	private String audience;
	private String certification;
	private boolean eligibleForCPF;

	private boolean published;
	public IndexedPageDto() {
		creationDate = LocalDateTime.now();
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

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public CrawlerDto getCrawler() {
		return crawler;
	}

	public void setCrawler(CrawlerDto crawler) {
		this.crawler = crawler;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

//	public String getPageContent() {
//		return pageContent;
//	}
//
//	public void setPageContent(String pageContent) {
//		this.pageContent = pageContent;
//	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public boolean isTaxesIncluded() {
		return taxesIncluded;
	}

	public void setTaxesIncluded(boolean taxesIncluded) {
		this.taxesIncluded = taxesIncluded;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getTrainingProgram() {
		return trainingProgram;
	}

	public void setTrainingProgram(String trainingProgram) {
		this.trainingProgram = trainingProgram;
	}

	public String getObjectives() {
		return objectives;
	}

	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}

	public String getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(String prerequisites) {
		this.prerequisites = prerequisites;
	}

	public String getAudience() {
		return audience;
	}

	public void setAudience(String audience) {
		this.audience = audience;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public boolean isEligibleForCPF() {
		return eligibleForCPF;
	}

	public void setEligibleForCPF(boolean eligibleForCPF) {
		this.eligibleForCPF = eligibleForCPF;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}
	
	

}
