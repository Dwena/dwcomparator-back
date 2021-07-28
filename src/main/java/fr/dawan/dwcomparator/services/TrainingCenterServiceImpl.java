package fr.dawan.dwcomparator.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.dawan.dwcomparator.dto.CountDto;
import fr.dawan.dwcomparator.dto.DtoTools;
import fr.dawan.dwcomparator.dto.TrainingCenterDto;
import fr.dawan.dwcomparator.entities.TrainingCenter;
import fr.dawan.dwcomparator.repositories.TrainingCenterRepository;

@Service
@Transactional
public class TrainingCenterServiceImpl implements TrainingCenterService {

	@Autowired
	private TrainingCenterRepository trainingCenterRepository;

	@Override
	public List<TrainingCenterDto> getAll(int page, int max, String search) {
		List<TrainingCenter> lst = trainingCenterRepository.findAllByNameContaining(search, PageRequest.of(page, max))
				.get().collect(Collectors.toList());
		List<TrainingCenterDto> result = new ArrayList<TrainingCenterDto>();
		for (TrainingCenter c : lst) {
			result.add(DtoTools.convert(c, TrainingCenterDto.class));
		}

		return result;
	}

	@Override
	public TrainingCenterDto getById(long id) {
		Optional<TrainingCenter> c = trainingCenterRepository.findById(id);
		if (c.isPresent())
			return DtoTools.convert(c.get(), TrainingCenterDto.class);

		return null;
	}

	@Override
	public TrainingCenterDto saveOrUpdate(TrainingCenterDto cDto) {
		TrainingCenter c = DtoTools.convert(cDto, TrainingCenter.class);
		c = trainingCenterRepository.saveAndFlush(c);
		return DtoTools.convert(c, TrainingCenterDto.class);
	}

	@Override
	public ResponseEntity<?> deleteById(long id) {
		trainingCenterRepository.deleteById(id);
		return null;
	}

	@Override
	public CountDto count(String search) {
		return new CountDto(trainingCenterRepository.countByNameContaining(search));
	}

	@Override
	public List<TrainingCenterDto> getAll() {
		List<TrainingCenter> lst = trainingCenterRepository.findAll();
		List<TrainingCenterDto> result = new ArrayList<TrainingCenterDto>();
		for (TrainingCenter c : lst) {
			result.add(DtoTools.convert(c, TrainingCenterDto.class));
		}

		return result;
	}

}
