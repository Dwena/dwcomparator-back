package fr.dawan.dwcomparator.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import fr.dawan.dwcomparator.dto.CountDto;
import fr.dawan.dwcomparator.dto.TrainingCenterDto;
import fr.dawan.dwcomparator.dto.UrlToCrawlDto;

public interface UrlToCrawlService {
	List<UrlToCrawlDto> getAllBySearch(int page, int max, String search);

    List<UrlToCrawlDto> getAll(int page, int max);
    UrlToCrawlDto getById(long id);
    ResponseEntity<?> deleteById(long id);
    UrlToCrawlDto saveOrUpdate(@RequestBody UrlToCrawlDto cDto);
    CountDto count();
	CountDto count(String search);


    List<UrlToCrawlDto> getByTrainingCenterId(long id);
}
