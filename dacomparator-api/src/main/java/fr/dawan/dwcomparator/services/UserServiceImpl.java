package fr.dawan.dwcomparator.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.dawan.dwcomparator.dto.CountDto;
import fr.dawan.dwcomparator.dto.DtoTools;
import fr.dawan.dwcomparator.dto.UserDto;
import fr.dawan.dwcomparator.entities.User;
import fr.dawan.dwcomparator.repositories.UserRepository;
import fr.dawan.dwcomparator.tools.HashTools;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserDto> getAllUsers(int page, int max, String search) {
		List<User> lst = userRepository.findAllByFirstnameContainingOrLastnameContainingOrEmailContaining(search,search,search, PageRequest.of(page, max)).get().collect(Collectors.toList());
		List<UserDto> result = new ArrayList<UserDto>();
		for (User c : lst) {
			result.add(DtoTools.convert(c, UserDto.class));
		}

		return result;
	}

	@Override
	public UserDto getById(long id) {
		Optional<User> c = userRepository.findById(id);
		if (c.isPresent())
			return DtoTools.convert(c.get(), UserDto.class);

		return null;
	}

	@Override
	public UserDto findByEmail(String email) {
		User c = userRepository.findByEmail(email);
		if (c != null)
			return DtoTools.convert(c, UserDto.class);

		return null;
	}

	@Override
	public UserDto saveOrUpdate(UserDto cDto) {
		User c = DtoTools.convert(cDto, User.class);
		try {
			if(c.getId()==0) {
				c.setPassword(HashTools.hashSHA512(c.getPassword()));
			}else {
				UserDto userInDB = getById(c.getId());
				if(!userInDB.getPassword().equals(c.getPassword())) {
					c.setPassword(HashTools.hashSHA512(c.getPassword()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		c = userRepository.saveAndFlush(c);
		return DtoTools.convert(c, UserDto.class);
	}

	
	@Override
	public CountDto count(String search) {
		return new CountDto(userRepository.countByFirstnameContainingOrLastnameContainingOrEmailContaining(search, search, search));
	}

	@Override
	public List<UserDto> findByName(String firstOrLastname) {
		List<User> users = userRepository.findByName(firstOrLastname);
		List<UserDto> usersdto = new ArrayList<UserDto>();
		if (users != null)
			for (User user : users) {
				usersdto.add(DtoTools.convert(user, UserDto.class));

			}
		return usersdto;
	}

	@Override
	public List<UserDto> getAll() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = new ArrayList<UserDto>();
		for (User user : users) {
			userDtos.add(DtoTools.convert(user, UserDto.class));
		}
		return userDtos;
	}

	@Override
	public void delete(long id) {
		userRepository.deleteById(id);
	}

}