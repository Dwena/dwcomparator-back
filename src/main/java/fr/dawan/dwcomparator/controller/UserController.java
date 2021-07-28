package fr.dawan.dwcomparator.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.dwcomparator.dto.CountDto;
import fr.dawan.dwcomparator.dto.UserDto;
import fr.dawan.dwcomparator.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	// /api/users/{page}/{size}
	@GetMapping(value = "/{page}/{size}", produces = "application/json")
	public @ResponseBody List<UserDto> getAllByPage(@PathVariable("page") int page,
			@PathVariable(value = "size") int size) {
		return userService.getAllUsers(page, size,"");
	}
	
	@GetMapping(value = "/{page}/{size}/{search}", produces = "application/json")
	public @ResponseBody List<UserDto> getAllByPage(@PathVariable("page") int page,
			@PathVariable(value = "size") int size, @PathVariable(value = "search", required = false) Optional<String> search) {
		if(search.isPresent())
			return userService.getAllUsers(page, size, search.get());
		else
			return userService.getAllUsers(page, size, "");
	}

	// api/users/{id}
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
	public UserDto getById(@PathVariable("id") long id) {
		return userService.getById(id);
	}

	// POST
	@PostMapping(consumes = "application/json", produces = "application/json")
	public UserDto save(@RequestBody UserDto cDto) {
		return userService.saveOrUpdate(cDto);
	}
	
	// PUT
	@PutMapping(consumes = "application/json", produces = "application/json")
	public UserDto update(@RequestBody UserDto cDto) {
		return userService.saveOrUpdate(cDto);
	}

	// searchByEmail
	// api/users/search?email=xxxxx
	@GetMapping(value = "/search", produces = { "application/json", "application/xml" })
	public UserDto getByEmail(@RequestParam("email") String email) {
		return userService.findByEmail(email);
	}

	@GetMapping(value = "/count", produces = "application/json")
	public CountDto count() {
		return userService.count("");
	}
	@GetMapping(value = "/count/{search}", produces = "application/json")
	public CountDto count(@PathVariable(value = "search", required = false) Optional<String> search) {
		if(search.isPresent())
			return userService.count(search.get());
		else
			return userService.count("");
	}
	
	// api/users/search-by?name=xxxxx
	@GetMapping(value = "/search-by/{name}", produces = "application/json")
	public @ResponseBody List<UserDto> searchByName(@RequestParam("name") String firstOrLastname) {
		return userService.findByName(firstOrLastname);
	}
	
	// DELETE : api/users/{id}
		@DeleteMapping(value = "/{id}", produces = { "application/json", "application/xml" })
		public void deleteById(@PathVariable("id") long id) {
			userService.delete(id);;
		}

}
