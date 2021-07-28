package fr.dawan.dwcomparator.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.dawan.dwcomparator.dto.CountDto;
import fr.dawan.dwcomparator.dto.DtoTools;
import fr.dawan.dwcomparator.dto.TrainingCenterDto;
import fr.dawan.dwcomparator.dto.UrlToCrawlDto;
import fr.dawan.dwcomparator.entities.TrainingCenter;
import fr.dawan.dwcomparator.entities.UrlToCrawl;
import fr.dawan.dwcomparator.repositories.UrlToCrawlRepository;

@Service
@Transactional
public class UrlToCrawlServiceImpl implements UrlToCrawlService {

	@Autowired
	private UrlToCrawlRepository urlToCrawlRepository;

	@Override
	public List<UrlToCrawlDto> getAll(int page, int max) {
		List<UrlToCrawl> lst = urlToCrawlRepository.findAll(PageRequest.of(page, max, Sort.by(Sort.Direction.ASC, "id"))).getContent();
		List<UrlToCrawlDto> result = new ArrayList<UrlToCrawlDto>();
		for (UrlToCrawl c : lst) {
			result.add(DtoTools.convert(c, UrlToCrawlDto.class));
		}

		return result;
	}

	@Override
	public UrlToCrawlDto getById(long id) {
		Optional<UrlToCrawl> c = urlToCrawlRepository.findById(id);
		if (c.isPresent())
			return DtoTools.convert(c.get(), UrlToCrawlDto.class);

		return null;
	}

	@Override
	public UrlToCrawlDto saveOrUpdate(UrlToCrawlDto cDto) {
		UrlToCrawl c = DtoTools.convert(cDto, UrlToCrawl.class);
		c = urlToCrawlRepository.saveAndFlush(c);
		return DtoTools.convert(c, UrlToCrawlDto.class);
	}

	@Override
	public ResponseEntity<?> deleteById(long id) {
		urlToCrawlRepository.deleteById(id);
		return null;
	}

	@Override
	public CountDto count() {
		return new CountDto(urlToCrawlRepository.count());
	}

	@Override
	public List<UrlToCrawlDto> getByTrainingCenterId(long id) {
		List<UrlToCrawl> lst = urlToCrawlRepository.findByTrainingCenterId(id);
		List<UrlToCrawlDto> result = new ArrayList<UrlToCrawlDto>();
		for (UrlToCrawl c : lst) {
			result.add(DtoTools.convert(c, UrlToCrawlDto.class));
		}
		return result;
	}

	@Override
	public List<UrlToCrawlDto> getAllBySearch(int page, int max, String search) {
		List<UrlToCrawl> lst = urlToCrawlRepository.findAllByUrlContaining(search, PageRequest.of(page, max))
				.get().collect(Collectors.toList());
		List<UrlToCrawlDto> result = new ArrayList<UrlToCrawlDto>();
		for (UrlToCrawl c : lst) {
			result.add(DtoTools.convert(c, UrlToCrawlDto.class));
		}

		return result;
	}
	
	@Override
	public CountDto count(String search) {
		return new CountDto(urlToCrawlRepository.countByUrlContaining(search));
	}


}
