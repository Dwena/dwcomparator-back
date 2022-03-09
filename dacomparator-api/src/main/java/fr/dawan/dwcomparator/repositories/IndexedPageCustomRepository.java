package fr.dawan.dwcomparator.repositories;

import java.util.List;
import java.util.Map;

import fr.dawan.dwcomparator.entities.IndexedPage;

public interface IndexedPageCustomRepository {
	
	long countByCriterias(Map<String, String[]> paramsMap, boolean published);
    
    List<IndexedPage> searchBy(Map<String, String[]> paramsMap, int page, int size, boolean published);
    
    void removeOldUnpublishedPages(int days);

}
