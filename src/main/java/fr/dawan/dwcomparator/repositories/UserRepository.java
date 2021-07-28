package fr.dawan.dwcomparator.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.dwcomparator.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("FROM User u WHERE u.email = :email")
	User findByEmail(@Param("email") String email);

	@Query("FROM User u WHERE u.firstname like :firstOrLastname OR u.lastname like :firstOrLastname")
	List<User> findByName(@Param("firstOrLastname") String firstOrLastname);

	
	Page<User> findAllByFirstnameContainingOrLastnameContainingOrEmailContaining(
			String firstname, 
			String lastname,
			String email, 
			Pageable pageable);

	long countByFirstnameContainingOrLastnameContainingOrEmailContaining(
			String firstname, 
			String lastname,
			String email);
}
