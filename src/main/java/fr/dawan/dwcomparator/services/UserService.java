package fr.dawan.dwcomparator.services;

import java.util.List;

import fr.dawan.dwcomparator.dto.CountDto;
import fr.dawan.dwcomparator.dto.UserDto;

public interface UserService {

	List<UserDto> getAllUsers(int page, int max, String search);

	UserDto getById(long id);

	UserDto findByEmail(String email);

	UserDto saveOrUpdate(UserDto cDto);

	CountDto count(String search);

	List<UserDto> findByName(String firstOrLastname);

	List<UserDto> getAll();

	void delete(long id);


}
