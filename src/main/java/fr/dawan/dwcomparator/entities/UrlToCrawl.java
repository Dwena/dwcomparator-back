package fr.dawan.dwcomparator.entities;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Version;

import fr.dawan.dwcomparator.interceptors.Auditable;

@Entity
public class UrlToCrawl implements Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Version
	private int version; // 1
	@ManyToOne
	private TrainingCenter trainingCenter; // orsys

	private boolean newsUrl;

	@Column(columnDefinition = "VARCHAR(512)")
	private String url; // /formations/technologies-numeriques

	/**
	 * define many actions to do before crawling the page (click on cookiesPopup,
	 * click on blocks to expand etc...)
	 * 
	 * many css selectors separatedBy ;
	 */
	private String preActions;

	/**
	 * define many actions to after crawling the page to access the program
	 * 
	 * many css selectors separatedBy ;
	 */
	private String preProgramActions;

	/**
	 * define many actions to after crawling the page to access the list of sessions
	 * 
	 * many css selectors separatedBy ;
	 */
	private String preSessionsActions;

	/**
	 * define many actions to after crawling the page to access the url of
	 * subscription
	 * 
	 * many css selectors separatedBy ;
	 */
	private String preSubscriptionUrlActions;

	private int subLevelNumber; // nombre de sous niveaux à parcourir //1

	@ElementCollection
	@CollectionTable(name = "search_expression_level", joinColumns = {
			@JoinColumn(name = "url_to_crawl_id", referencedColumnName = "id") })
	@MapKeyColumn(name = "level")
	@Column(name = "search_expression")
	private Map<Integer, String> searchExpressionByLevel;
	// ici, a avec un href contenant /formations
	// et possédant un h2 avec la classe class="c_bleuVert

	private String referenceSearchExpression;
	private String referenceReplaceExpression;
	
	private String titleSearchExpression;
	private String titleReplaceExpression;
	
	private String durationSearchExpression;
	private String durationReplaceExpression;
	
	private String priceSearchExpression;
	private String priceReplaceExpression;

	private String trainingProgramSearchExpression;
	private String trainingProgramReplaceExpression;
	
	private String objectivesSearchExpression;
	private String objectivesReplaceExpression;
	
	private String prerequisitesSearchExpression;
	private String prerequisitesReplaceExpression;
	
	private String audienceSearchExpression;
	private String audienceReplaceExpression;
	
	private String certificationSearchExpression;
	private String certificationReplaceExpression;
	
	private String eligibleForCPFSearchExpression;
	private String eligibleForCPFReplaceExpression;

	/**
	 * css selector to access sessions after preSessionsActions
	 */
	private String sessionsSearchExpression;
	private String sessionsReplaceExpression;
	
	private String startDateSearchExpression;
	private String startDateReplaceExpression;
	
	private String endDateSearchExpression;
	private String endDateReplaceExpression;
	
	private String dateFormatExpression;
	
	private String locationSearchExpression;
	private String locationReplaceExpression;
	
	private String guaranteedSearchExpression;
	private String guaranteedReplaceExpression;
	
	private String sessionPriceSearchExpression;
	private String sessionPriceReplaceExpression;
	
	private String subscriptionUrlSearchExpression;
	private String subscriptionUrlReplaceExpression;

	/**
	 * Constructor
	 */
	public UrlToCrawl() {

	}

	public String getGuaranteedReplaceExpression() {
		return guaranteedReplaceExpression;
	}

	public void setGuaranteedReplaceExpression(String guaranteedReplaceExpression) {
		this.guaranteedReplaceExpression = guaranteedReplaceExpression;
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

	public String getPreSubscriptionUrlActions() {
		return preSubscriptionUrlActions;
	}

	public void setPreSubscriptionUrlActions(String preSubscriptionUrlActions) {
		this.preSubscriptionUrlActions = preSubscriptionUrlActions;
	}

	public TrainingCenter getTrainingCenter() {
		return trainingCenter;
	}

	public void setTrainingCenter(TrainingCenter trainingCenter) {
		this.trainingCenter = trainingCenter;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Url complète avec la baseUrl du trainingcenter
	 * 
	 * @return
	 */
	public String getFullUrl() {
		return trainingCenter.getBaseSiteUrl() + url;
	}

	public int getSubLevelNumber() {
		return subLevelNumber;
	}

	public void setSubLevelNumber(int subLevelNumber) {
		this.subLevelNumber = subLevelNumber;
	}

	public Map<Integer, String> getSearchExpressionByLevel() {
		return searchExpressionByLevel;
	}

	public void setSearchExpressionByLevel(Map<Integer, String> searchExpressionByLevel) {
		this.searchExpressionByLevel = searchExpressionByLevel;
	}

	public String getReferenceSearchExpression() {
		return referenceSearchExpression;
	}

	public void setReferenceSearchExpression(String referenceSearchExpression) {
		this.referenceSearchExpression = referenceSearchExpression;
	}

	public String getTitleSearchExpression() {
		return titleSearchExpression;
	}

	public void setTitleSearchExpression(String titleSearchExpression) {
		this.titleSearchExpression = titleSearchExpression;
	}

	public String getDurationSearchExpression() {
		return durationSearchExpression;
	}

	public void setDurationSearchExpression(String durationSearchExpression) {
		this.durationSearchExpression = durationSearchExpression;
	}

	public String getPriceSearchExpression() {
		return priceSearchExpression;
	}

	public void setPriceSearchExpression(String priceSearchExpression) {
		this.priceSearchExpression = priceSearchExpression;
	}

	public String getReferenceReplaceExpression() {
		return referenceReplaceExpression;
	}

	public void setReferenceReplaceExpression(String referenceReplaceExpression) {
		this.referenceReplaceExpression = referenceReplaceExpression;
	}

	public String getTitleReplaceExpression() {
		return titleReplaceExpression;
	}

	public void setTitleReplaceExpression(String titleReplaceExpression) {
		this.titleReplaceExpression = titleReplaceExpression;
	}

	public String getDurationReplaceExpression() {
		return durationReplaceExpression;
	}

	public void setDurationReplaceExpression(String durationReplaceExpression) {
		this.durationReplaceExpression = durationReplaceExpression;
	}

	public String getPriceReplaceExpression() {
		return priceReplaceExpression;
	}

	public void setPriceReplaceExpression(String priceReplaceExpression) {
		this.priceReplaceExpression = priceReplaceExpression;
	}

	public String getPreActions() {
		return preActions;
	}

	public void setPreActions(String preActions) {
		this.preActions = preActions;
	}

	public String getTrainingProgramSearchExpression() {
		return trainingProgramSearchExpression;
	}

	public void setTrainingProgramSearchExpression(String trainingProgramSearchExpression) {
		this.trainingProgramSearchExpression = trainingProgramSearchExpression;
	}

	public String getObjectivesSearchExpression() {
		return objectivesSearchExpression;
	}

	public void setObjectivesSearchExpression(String objectivesSearchExpression) {
		this.objectivesSearchExpression = objectivesSearchExpression;
	}

	public String getPrerequisitesSearchExpression() {
		return prerequisitesSearchExpression;
	}

	public void setPrerequisitesSearchExpression(String prerequisitesSearchExpression) {
		this.prerequisitesSearchExpression = prerequisitesSearchExpression;
	}

	public String getAudienceSearchExpression() {
		return audienceSearchExpression;
	}

	public void setAudienceSearchExpression(String audienceSearchExpression) {
		this.audienceSearchExpression = audienceSearchExpression;
	}

	public String getCertificationSearchExpression() {
		return certificationSearchExpression;
	}

	public void setCertificationSearchExpression(String certificationSearchExpression) {
		this.certificationSearchExpression = certificationSearchExpression;
	}

	public String getEligibleForCPFSearchExpression() {
		return eligibleForCPFSearchExpression;
	}

	public void setEligibleForCPFSearchExpression(String eligibleForCPFSearchExpression) {
		this.eligibleForCPFSearchExpression = eligibleForCPFSearchExpression;
	}

	public String getTrainingProgramReplaceExpression() {
		return trainingProgramReplaceExpression;
	}

	public void setTrainingProgramReplaceExpression(String trainingProgramReplaceExpression) {
		this.trainingProgramReplaceExpression = trainingProgramReplaceExpression;
	}

	public String getObjectivesReplaceExpression() {
		return objectivesReplaceExpression;
	}

	public void setObjectivesReplaceExpression(String objectivesReplaceExpression) {
		this.objectivesReplaceExpression = objectivesReplaceExpression;
	}

	public String getPrerequisitesReplaceExpression() {
		return prerequisitesReplaceExpression;
	}

	public void setPrerequisitesReplaceExpression(String prerequisitesReplaceExpression) {
		this.prerequisitesReplaceExpression = prerequisitesReplaceExpression;
	}

	public String getAudienceReplaceExpression() {
		return audienceReplaceExpression;
	}

	public void setAudienceReplaceExpression(String audienceReplaceExpression) {
		this.audienceReplaceExpression = audienceReplaceExpression;
	}

	public String getCertificationReplaceExpression() {
		return certificationReplaceExpression;
	}

	public void setCertificationReplaceExpression(String certificationReplaceExpression) {
		this.certificationReplaceExpression = certificationReplaceExpression;
	}

	public String getEligibleForCPFReplaceExpression() {
		return eligibleForCPFReplaceExpression;
	}

	public void setEligibleForCPFReplaceExpression(String eligibleForCPFReplaceExpression) {
		this.eligibleForCPFReplaceExpression = eligibleForCPFReplaceExpression;
	}

	public String getSessionsSearchExpression() {
		return sessionsSearchExpression;
	}

	public void setSessionsSearchExpression(String sessionsSearchExpression) {
		this.sessionsSearchExpression = sessionsSearchExpression;
	}

	public String getPreSessionsActions() {
		return preSessionsActions;
	}

	public void setPreSessionsActions(String preSessionsActions) {
		this.preSessionsActions = preSessionsActions;
	}

	public String getStartDateSearchExpression() {
		return startDateSearchExpression;
	}

	public void setStartDateSearchExpression(String startDateSearchExpression) {
		this.startDateSearchExpression = startDateSearchExpression;
	}

	public String getStartDateReplaceExpression() {
		return startDateReplaceExpression;
	}

	public void setStartDateReplaceExpression(String startDateReplaceExpression) {
		this.startDateReplaceExpression = startDateReplaceExpression;
	}

	public String getEndDateSearchExpression() {
		return endDateSearchExpression;
	}

	public void setEndDateSearchExpression(String endDateSearchExpression) {
		this.endDateSearchExpression = endDateSearchExpression;
	}

	public String getLocationSearchExpression() {
		return locationSearchExpression;
	}

	public void setLocationSearchExpression(String locationSearchExpression) {
		this.locationSearchExpression = locationSearchExpression;
	}

	public String getSubscriptionUrlSearchExpression() {
		return subscriptionUrlSearchExpression;
	}

	public void setSubscriptionUrlSearchExpression(String subscriptionUrlSearchExpression) {
		this.subscriptionUrlSearchExpression = subscriptionUrlSearchExpression;
	}

	public String getSessionsReplaceExpression() {
		return sessionsReplaceExpression;
	}

	public void setSessionsReplaceExpression(String sessionsReplaceExpression) {
		this.sessionsReplaceExpression = sessionsReplaceExpression;
	}

	public String getEndDateReplaceExpression() {
		return endDateReplaceExpression;
	}

	public void setEndDateReplaceExpression(String endDateReplaceExpression) {
		this.endDateReplaceExpression = endDateReplaceExpression;
	}

	public String getLocationReplaceExpression() {
		return locationReplaceExpression;
	}

	public void setLocationReplaceExpression(String locationReplaceExpression) {
		this.locationReplaceExpression = locationReplaceExpression;
	}

	public String getSubscriptionUrlReplaceExpression() {
		return subscriptionUrlReplaceExpression;
	}

	public String getGuaranteedSearchExpression() {
		return guaranteedSearchExpression;
	}

	public void setGuaranteedSearchExpression(String guaranteedSearchExpression) {
		this.guaranteedSearchExpression = guaranteedSearchExpression;
	}

	public void setSubscriptionUrlReplaceExpression(String subscriptionUrlReplaceExpression) {
		this.subscriptionUrlReplaceExpression = subscriptionUrlReplaceExpression;
	}

	public boolean isNewsUrl() {
		return newsUrl;
	}

	public void setNewsUrl(boolean newsUrl) {
		this.newsUrl = newsUrl;
	}

	public String getDateFormatExpression() {
		return dateFormatExpression;
	}

	public void setDateFormatExpression(String dateFormatExpression) {
		this.dateFormatExpression = dateFormatExpression;
	}

	public String getPreProgramActions() {
		return preProgramActions;
	}

	public void setPreProgramActions(String preProgramActions) {
		this.preProgramActions = preProgramActions;
	}

	public String getSessionPriceSearchExpression() {
		return sessionPriceSearchExpression;
	}

	public void setSessionPriceSearchExpression(String sessionPriceSearchExpression) {
		this.sessionPriceSearchExpression = sessionPriceSearchExpression;
	}

	public String getSessionPriceReplaceExpression() {
		return sessionPriceReplaceExpression;
	}

	public void setSessionPriceReplaceExpression(String sessionPriceReplaceExpression) {
		this.sessionPriceReplaceExpression = sessionPriceReplaceExpression;
	}

}
