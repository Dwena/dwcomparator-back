package fr.dawan.dwcomparator.dto;

import java.time.LocalDateTime;

public class IndexedSessionDto {

	private long id;
	private int version;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	private String location;

	private String subscriptionUrl;

	private IndexedPageDto indexedPage;

	private boolean guaranteed;

	private float price;

	public boolean isGuaranteed() {
		return guaranteed;
	}

	public void setGuaranteed(boolean guaranteed) {
		this.guaranteed = guaranteed;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSubscriptionUrl() {
		return subscriptionUrl;
	}

	public void setSubscriptionUrl(String subscriptionUrl) {
		this.subscriptionUrl = subscriptionUrl;
	}

	public IndexedPageDto getIndexedPage() {
		return indexedPage;
	}

	public void setIndexedPage(IndexedPageDto indexedPage) {
		this.indexedPage = indexedPage;
	}

}
