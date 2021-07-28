package fr.dawan.dwcomparator.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.dawan.dwcomparator.entities.TrainingCenter;

@Repository
public interface TrainingCenterRepository extends JpaRepository<TrainingCenter, Long> {


	long countByNameContaining(String name);

	Page<TrainingCenter> findAllByNameContaining(String name, Pageable pageable);

}
