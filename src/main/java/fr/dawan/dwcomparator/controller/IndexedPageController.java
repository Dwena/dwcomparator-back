package fr.dawan.dwcomparator.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.dwcomparator.dto.CountDto;
import fr.dawan.dwcomparator.dto.IndexedPageDto;
import fr.dawan.dwcomparator.services.IndexedPageService;

@RestController
@RequestMapping("/api/indexedpage")
public class IndexedPageController {

	@Autowired
	private IndexedPageService indexedPageService;

	// GET : /api/trainingcenters/{page}/{size}
	@GetMapping(value = "/{page}/{size}", produces = "application/json")
	public @ResponseBody List<IndexedPageDto> getAllByPage(@PathVariable("page") int page,
														   @PathVariable(value = "size") int size) {
		return indexedPageService.getAll(page, size);
	}

	// GET : /api/indexedpages/{keyword}/{page}/{size}
	@GetMapping(value = "/{keyword}/{page}/{size}", produces = "application/json")
	public @ResponseBody List<IndexedPageDto> getByKeyword(@PathVariable("keyword") String keyword,
														   @PathVariable("page") int page,
														   @PathVariable(value = "size") int size) {
		return indexedPageService.getAllByKeyword("%"+keyword+"%", page, size);
	}

	// GET : /api/trainingcenters/{id}
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
	public IndexedPageDto getById(@PathVariable("id") long id) {
		return indexedPageService.getById(id);
	}


	// DELETE : api/trainingcenters/{id}
	@DeleteMapping(value = "/{id}", produces = "text/plain")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") long id) {
		try {
			indexedPageService.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("suppression effectuée");
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("suppression non réalisée");
		}
	}
	
	@GetMapping(value = "/count", produces = "application/json")
	public CountDto count() {
		return indexedPageService.count();
	}
	
	@GetMapping(value = "/count/{search}", produces = "application/json")
	public CountDto count(@PathVariable(value = "search", required = false) Optional<String> search) {
		if(search.isPresent())
			return indexedPageService.countByKeyword(search.get());
		else
			return indexedPageService.count();
	}

	// /api/indexedpages/search-by/1/1/20?price_min=30&price_max=120&audience=admin&keyword=java&order=price+ASC
		@GetMapping(value = "/search-by/{published}/{page}/{size}", produces = "application/json")
		public @ResponseBody List<IndexedPageDto> searchBy(@PathVariable("published") int published,
														   @PathVariable("page") int page,
				   										   @PathVariable(value = "size") int size,
				   										   HttpServletRequest request) {
			
			Map<String, String[]> paramsMap = request.getParameterMap();
			return indexedPageService.searchBy(page, size, paramsMap, (published>=1));
		}
		
		// /api/indexedpages/count-by/1/?price_min=30&price_max=120&audience=admin&keyword=java&order=price+ASC
			@GetMapping(value = "/count-by/{published}", produces = "application/json")
			public @ResponseBody CountDto countBy(@PathVariable("published") int published,
												 HttpServletRequest request) {
				Map<String, String[]> paramsMap = request.getParameterMap();
				return indexedPageService.countByParams(paramsMap, (published>=1));
			}

	}

