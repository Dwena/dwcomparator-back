package fr.dawan.dwcomparator.dto;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;

public class UrlToCrawlDto {

	private long id;
	private int version; // 1
	private TrainingCenterDto trainingCenter; // orsys

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

	private int subLevelNumber;

	@ElementCollection
	private Map<Integer, String> searchExpressionByLevel;

	private String referenceSearchExpression;
	private String titleSearchExpression;
	private String durationSearchExpression;
	private String priceSearchExpression;

	private String referenceReplaceExpression;
	private String titleReplaceExpression;
	private String durationReplaceExpression;
	private String priceReplaceExpression;

	private String trainingProgramSearchExpression;
	private String objectivesSearchExpression;
	private String prerequisitesSearchExpression;
	private String audienceSearchExpression;
	private String certificationSearchExpression;
	private String eligibleForCPFSearchExpression;

	private String trainingProgramReplaceExpression;
	private String objectivesReplaceExpression;
	private String prerequisitesReplaceExpression;
	private String audienceReplaceExpression;
	private String certificationReplaceExpression;
	private String eligibleForCPFReplaceExpression;

	/**
	 * css selector to access sessions after preSessionsActions
	 */
	private String sessionsSearchExpression;

	private String startDateSearchExpression;
	private String endDateSearchExpression;
	private String dateFormatExpression;
	private String locationSearchExpression;
	private String guaranteedSearchExpression;
	private String sessionPriceSearchExpression;
	private String subscriptionUrlSearchExpression;

	private String sessionsReplaceExpression;
	private String startDateReplaceExpression;
	private String endDateReplaceExpression;
	private String locationReplaceExpression;
	private String guaranteedReplaceExpression;
	private String sessionPriceReplaceExpression;
	private String subscriptionUrlReplaceExpression;

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

	public void setTrainingCenter(TrainingCenterDto trainingCenter) {
		this.trainingCenter = trainingCenter;
	}

	public boolean isNewsUrl() {
		return newsUrl;
	}

	public void setNewsUrl(boolean newsUrl) {
		this.newsUrl = newsUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPreActions() {
		return preActions;
	}

	public void setPreActions(String preActions) {
		this.preActions = preActions;
	}

	public String getPreProgramActions() {
		return preProgramActions;
	}

	public void setPreProgramActions(String preProgramActions) {
		this.preProgramActions = preProgramActions;
	}

	public String getPreSessionsActions() {
		return preSessionsActions;
	}

	public void setPreSessionsActions(String preSessionsActions) {
		this.preSessionsActions = preSessionsActions;
	}

	public String getPreSubscriptionUrlActions() {
		return preSubscriptionUrlActions;
	}

	public void setPreSubscriptionUrlActions(String preSubscriptionUrlActions) {
		this.preSubscriptionUrlActions = preSubscriptionUrlActions;
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

	public String getStartDateSearchExpression() {
		return startDateSearchExpression;
	}

	public void setStartDateSearchExpression(String startDateSearchExpression) {
		this.startDateSearchExpression = startDateSearchExpression;
	}

	public String getEndDateSearchExpression() {
		return endDateSearchExpression;
	}

	public void setEndDateSearchExpression(String endDateSearchExpression) {
		this.endDateSearchExpression = endDateSearchExpression;
	}

	public String getDateFormatExpression() {
		return dateFormatExpression;
	}

	public void setDateFormatExpression(String dateFormatExpression) {
		this.dateFormatExpression = dateFormatExpression;
	}

	public String getLocationSearchExpression() {
		return locationSearchExpression;
	}

	public void setLocationSearchExpression(String locationSearchExpression) {
		this.locationSearchExpression = locationSearchExpression;
	}

	public String getGuaranteedSearchExpression() {
		return guaranteedSearchExpression;
	}

	public void setGuaranteedSearchExpression(String guaranteedSearchExpression) {
		this.guaranteedSearchExpression = guaranteedSearchExpression;
	}

	public String getSessionPriceSearchExpression() {
		return sessionPriceSearchExpression;
	}

	public void setSessionPriceSearchExpression(String sessionPriceSearchExpression) {
		this.sessionPriceSearchExpression = sessionPriceSearchExpression;
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

	public String getStartDateReplaceExpression() {
		return startDateReplaceExpression;
	}

	public void setStartDateReplaceExpression(String startDateReplaceExpression) {
		this.startDateReplaceExpression = startDateReplaceExpression;
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

	public String getGuaranteedReplaceExpression() {
		return guaranteedReplaceExpression;
	}

	public void setGuaranteedReplaceExpression(String guaranteedReplaceExpression) {
		this.guaranteedReplaceExpression = guaranteedReplaceExpression;
	}

	public String getSessionPriceReplaceExpression() {
		return sessionPriceReplaceExpression;
	}

	public void setSessionPriceReplaceExpression(String sessionPriceReplaceExpression) {
		this.sessionPriceReplaceExpression = sessionPriceReplaceExpression;
	}

	public String getSubscriptionUrlReplaceExpression() {
		return subscriptionUrlReplaceExpression;
	}

	public void setSubscriptionUrlReplaceExpression(String subscriptionUrlReplaceExpression) {
		this.subscriptionUrlReplaceExpression = subscriptionUrlReplaceExpression;
	}

}
