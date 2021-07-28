package fr.dawan.dwcomparator.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.dwcomparator.dto.CountDto;
import fr.dawan.dwcomparator.dto.TrainingCenterDto;
import fr.dawan.dwcomparator.services.TrainingCenterService;

@RestController
@RequestMapping("/api/trainingcenters")
public class TrainingCenterController {

	@Autowired
	private TrainingCenterService trainingCenterService;

	@Value("${app.storagefolder}")
	private String storagefolder;

	// /api/users/{page}/{size}
	@GetMapping(value = "/{page}/{size}", produces = "application/json")
	public @ResponseBody List<TrainingCenterDto> getAllByPage(@PathVariable("page") int page,
			@PathVariable(value = "size") int size) {
		return trainingCenterService.getAll(page, size, "");
	}
	
	@GetMapping(produces = "application/json")
	public @ResponseBody List<TrainingCenterDto> getAll(){
		return trainingCenterService.getAll();
	}

	@GetMapping(value = "/{page}/{size}/{search}", produces = "application/json")
	public @ResponseBody List<TrainingCenterDto> getAllByPage(@PathVariable("page") int page,
			@PathVariable(value = "size") int size,
			@PathVariable(value = "search", required = false) Optional<String> search) {
		if (search.isPresent())
			return trainingCenterService.getAll(page, size, search.get());
		else
			return trainingCenterService.getAll(page, size, "");
	}

	// GET : /api/trainingcenters/{id}
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
	public TrainingCenterDto getById(@PathVariable("id") long id) {
		return trainingCenterService.getById(id);
	}

	// POST
	@PostMapping(consumes = "application/json", produces = "application/json")
	public TrainingCenterDto save(@RequestBody TrainingCenterDto cDto) {
		return trainingCenterService.saveOrUpdate(cDto);
	}

	// DELETE : api/trainingcenters/{id}
	@DeleteMapping(value = "/{id}", produces = "text/plain")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") long id) {
		try {
			trainingCenterService.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("suppression effectuée");
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("suppression non réalisée");
		}
	}

	@GetMapping(value = "/count", produces = "application/json")
	public CountDto count() {
		return trainingCenterService.count("");
	}

	@GetMapping(value = "/count/{search}", produces = "application/json")
	public CountDto count(@PathVariable(value = "search", required = false) Optional<String> search) {
		if (search.isPresent())
			return trainingCenterService.count(search.get());
		else
			return trainingCenterService.count("");
	}

	

}
