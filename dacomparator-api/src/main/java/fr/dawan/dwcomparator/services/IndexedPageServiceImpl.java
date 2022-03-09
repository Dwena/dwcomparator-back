package fr.dawan.dwcomparator.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.dawan.dwcomparator.dto.CountDto;
import fr.dawan.dwcomparator.dto.DtoTools;
import fr.dawan.dwcomparator.dto.IndexedPageDto;
import fr.dawan.dwcomparator.entities.IndexedPage;
import fr.dawan.dwcomparator.repositories.IndexedPageCustomRepository;
import fr.dawan.dwcomparator.repositories.IndexedPageRepository;

@Service
@Transactional
public class IndexedPageServiceImpl implements IndexedPageService {

	@Autowired
	private IndexedPageRepository indexedPageRepository;

	@Autowired
	private IndexedPageCustomRepository indexedPageCustomRepository;


	@Override
	public List<IndexedPageDto> getAll(int page, int max) {
		List<IndexedPage> lst = indexedPageRepository.findAll(PageRequest.of(page, max))
				.get().collect(Collectors.toList());
		List<IndexedPageDto> result = new ArrayList<IndexedPageDto>();
		for(IndexedPage c : lst) {
			result.add(DtoTools.convert(c, IndexedPageDto.class));
		}

		return result;
	}

	@Override
	public List<IndexedPageDto> getAllByKeyword(String keyword, int page, int max) {
		List<IndexedPage> lst = indexedPageRepository.findAllByKeyword(keyword, PageRequest.of(page, max))
				.get().collect(Collectors.toList());
		List<IndexedPageDto> result = new ArrayList<IndexedPageDto>();
		for(IndexedPage c : lst) {
			result.add(DtoTools.convert(c, IndexedPageDto.class));
		}

		return result;
	}

	@Override
	public IndexedPageDto getById(long id) {
		Optional<IndexedPage> c = indexedPageRepository.findById(id);
		if(c.isPresent())
			return DtoTools.convert(c.get(), IndexedPageDto.class);

		return null;
	}

	@Override
	public ResponseEntity<?> deleteById(long id) {
		indexedPageRepository.deleteById(id);
		return null;
	}

	@Override
	public CountDto count() {
		return new CountDto(indexedPageRepository.count());
	}

	@Override
    public CountDto countByKeyword(String keyword) {
        long n = indexedPageRepository.countByKeyword("%" + keyword + "%");
        return new CountDto(n);
    }
	
	@Override
	public List<IndexedPageDto> searchBy(int page, int size, Map<String, String[]> paramsMap, boolean published) {
		System.out.println("published ::::> " + published);
		List<IndexedPage> lst = indexedPageCustomRepository.searchBy(paramsMap, page, size, published);
		List<IndexedPageDto> result = new ArrayList<IndexedPageDto>();
		for(IndexedPage c : lst) {
			result.add(DtoTools.convert(c, IndexedPageDto.class));
		}

		return result;
	}
	
	@Override
    public CountDto countByParams(Map<String, String[]> paramsMap, boolean published) {
		System.out.println("published ::::> " + published);
        long n = indexedPageCustomRepository.countByCriterias(paramsMap, published);
        return new CountDto(n);
    }

	


}

