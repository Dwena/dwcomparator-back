package fr.dawan.dwcomparator.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table
public class IndexedPage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Version
	private int version;

	@Column
	private LocalDateTime creationDate;

	@ManyToOne
	private Crawler crawler;

	@Column(columnDefinition = "VARCHAR(512)")
	private String url;

	@Lob
	private String pageContent;

	private String reference;
	private String title;
	private String duration;
	private float price;
	private boolean taxesIncluded;
	
	@Column(columnDefinition = "TEXT")
	private String trainingProgram;
	
	@Column(columnDefinition = "TEXT")
	private String objectives;
	
	@Column(columnDefinition = "TEXT")
	private String prerequisites;
	
	@Column(columnDefinition = "TEXT")
	private String audience;
	
	@Column(columnDefinition = "TEXT")
	private String certification;
	
	@Column
	private boolean eligibleForCPF;
	
	@Column //default=true
	private boolean published;
	
	/**
	 * bi-directional mapping to use cascade
	 */
	@OneToMany(mappedBy = "indexedPage", cascade = CascadeType.ALL)
	private List<IndexedSession> sessions;
	

	public IndexedPage() {
		creationDate = LocalDateTime.now();
		sessions = new ArrayList<IndexedSession>();
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

	public Crawler getCrawler() {
		return crawler;
	}

	public void setCrawler(Crawler crawler) {
		this.crawler = crawler;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPageContent() {
		return pageContent;
	}

	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}

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

	public List<IndexedSession> getSessions() {
		return sessions;
	}

	public void setSessions(List<IndexedSession> sessions) {
		this.sessions = sessions;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}
	
	

}
