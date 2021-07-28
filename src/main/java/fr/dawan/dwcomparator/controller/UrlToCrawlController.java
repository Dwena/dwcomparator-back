package fr.dawan.dwcomparator.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.dwcomparator.dto.CountDto;
import fr.dawan.dwcomparator.dto.UrlToCrawlDto;
import fr.dawan.dwcomparator.dto.UserDto;
import fr.dawan.dwcomparator.services.UrlToCrawlService;

@RestController
@RequestMapping("api/urlToCrawl")
public class UrlToCrawlController {

	@Autowired
	UrlToCrawlService urlToCrawlService;

	// ##################################################
	// # CRUD #
	// ##################################################

	@GetMapping(value = "/{page}/{size}",produces = "application/json")
	public List<UrlToCrawlDto> getAll(@PathVariable("page") int page,
			@PathVariable(value = "size") int size) {
		return urlToCrawlService.getAll(page,size);
	}
	
	@GetMapping(value = "/trainingCenter/{id}",produces = "application/json")
	public List<UrlToCrawlDto> getByTrainingCenterId(@PathVariable("id") int id){
		return urlToCrawlService.getByTrainingCenterId(id);
	}
	

	@GetMapping(value = "/{id}", produces = "application/json")
	public UrlToCrawlDto getById(@PathVariable("id") long id) {
		return urlToCrawlService.getById(id);
	}

	@PostMapping(consumes = "application/json", produces = "application/json")
	public UrlToCrawlDto save(@RequestBody UrlToCrawlDto urlDto) {
		return urlToCrawlService.saveOrUpdate(urlDto);
	}

	@DeleteMapping(value = "/{id}", produces = "text/plain")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") long id) {
		try {
			urlToCrawlService.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("suppression effectuée");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("suppression non réalisée");
		}
	}

	@PutMapping(consumes = "application/json", produces = "application/json")
	public UrlToCrawlDto update(@RequestBody UrlToCrawlDto urlDto) {
		return urlToCrawlService.saveOrUpdate(urlDto);
	}
	
	 @GetMapping(value = "/count", produces = "application/json")
	    public CountDto count() {
	        return urlToCrawlService.count();
	    }
	 
//ttttt

	@GetMapping(value = "/{page}/{size}/{search}", produces = "application/json")
		public @ResponseBody List<UrlToCrawlDto> getAllByPage(@PathVariable("page") int page,
				@PathVariable(value = "size") int size,
				@PathVariable(value = "search", required = false) Optional<String> search) {
			if (search.isPresent())
				return urlToCrawlService.getAllBySearch(page, size, search.get());
			else
				return urlToCrawlService.getAllBySearch(page, size, "");
		}

	@GetMapping(value = "/count/{search}", produces = "application/json")
	public CountDto count(@PathVariable(value = "search", required = false) Optional<String> search) {
		if (search.isPresent())
			return urlToCrawlService.count(search.get());
		else
			return urlToCrawlService.count("");
	}

}
