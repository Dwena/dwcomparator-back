package fr.dawan.dwcomparator.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Version;

import fr.dawan.dwcomparator.interceptors.Auditable;

@Entity
@Table
public class TrainingCenter implements Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Version
	private int version; 
	
	private String name;          //orsys
    private String baseSiteUrl;         //https://www.orsys.fr
    private String searchUrl; //https://www.orsys.fr/Recherche/Search
    
    public enum SearchMethod { GET, POST };
    private SearchMethod  searchFormMethod; //POST
    private String searchParameters; //s
    private int indexingFrequency; //nombre de jours
	
    private boolean pricesIncludingTaxes; //false
    
    @Lob
    private String logoFileBase64;
    
    public TrainingCenter() {
    	
    }
  
    
    public TrainingCenter(String name, String baseSiteUrl, String searchUrl,
			SearchMethod searchFormMethod, String searchParameters, int indexingFrequency, boolean pricesIncludingTaxes,
			String logoFileBase64) {
		super();
		this.name = name;
		this.baseSiteUrl = baseSiteUrl;
		this.searchUrl = searchUrl;
		this.searchFormMethod = searchFormMethod;
		this.searchParameters = searchParameters;
		this.indexingFrequency = indexingFrequency;
		this.pricesIncludingTaxes = pricesIncludingTaxes;
		this.logoFileBase64 = logoFileBase64;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBaseSiteUrl() {
		return baseSiteUrl;
	}
	public void setBaseSiteUrl(String baseSiteUrl) {
		this.baseSiteUrl = baseSiteUrl;
	}
	public String getSearchUrl() {
		return searchUrl;
	}
	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}
	public SearchMethod getSearchFormMethod() {
		return searchFormMethod;
	}
	public void setSearchFormMethod(SearchMethod searchFormMethod) {
		this.searchFormMethod = searchFormMethod;
	}
	public String getSearchParameters() {
		return searchParameters;
	}
	public void setSearchParameters(String searchParameters) {
		this.searchParameters = searchParameters;
	}
	public int getIndexingFrequency() {
		return indexingFrequency;
	}
	public void setIndexingFrequency(int indexingFrequency) {
		this.indexingFrequency = indexingFrequency;
	}
	public boolean isPricesIncludingTaxes() {
		return pricesIncludingTaxes;
	}
	public void setPricesIncludingTaxes(boolean pricesIncludingTaxes) {
		this.pricesIncludingTaxes = pricesIncludingTaxes;
	}
	
	
	public String getLogoFileBase64() {
		return logoFileBase64;
	}

	public void setLogoFileBase64(String logoFileBase64) {
		this.logoFileBase64 = logoFileBase64;
	}
    
    
}
