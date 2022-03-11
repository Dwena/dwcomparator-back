package fr.dawan.dwcomparator.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import fr.dawan.dwcomparator.dto.CountDto;
import fr.dawan.dwcomparator.dto.IndexedPageDto;

public interface IndexedPageService {

	List<IndexedPageDto> getAll(int page, int max);
	List<IndexedPageDto> getAllByKeyword(String keyword, int page, int max);
	IndexedPageDto getById(long id);
	ResponseEntity<?> deleteById(long id);
	CountDto count();
	CountDto countByKeyword(String keyword);

	CountDto countByParams(Map<String, String[]> paramsMap, boolean published);
	List<IndexedPageDto> searchBy(int page, int size, Map<String, String[]> paramsMap, boolean published);
}

