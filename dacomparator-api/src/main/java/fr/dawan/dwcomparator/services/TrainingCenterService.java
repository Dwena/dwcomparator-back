package fr.dawan.dwcomparator.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import fr.dawan.dwcomparator.dto.CountDto;
import fr.dawan.dwcomparator.dto.TrainingCenterDto;

public interface TrainingCenterService {

	List<TrainingCenterDto> getAll(int page, int max, String search);
	TrainingCenterDto getById(long id);
	ResponseEntity<?> deleteById( long id);
	TrainingCenterDto saveOrUpdate(@RequestBody TrainingCenterDto cDto);
	CountDto count(String search);
	List<TrainingCenterDto> getAll();

}
