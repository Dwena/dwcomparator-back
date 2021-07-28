package fr.dawan.dwcomparator.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.dawan.dwcomparator.entities.Crawler;

@Repository
public interface CrawlerRepository extends JpaRepository<Crawler, Long> {

	@Query(value="SELECT c.id FROM Crawler c WHERE c.endDate is NULL")
	List<Long> getNotCompletedCrawlersIds();
	
	Page<Crawler> findAllByTrainingCenterNameContaining(String centerName, Pageable pageable);

	long countByTrainingCenterNameContaining(String centerName); 
}
