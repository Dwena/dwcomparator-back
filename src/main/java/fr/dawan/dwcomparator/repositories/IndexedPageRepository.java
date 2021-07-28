package fr.dawan.dwcomparator.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.dwcomparator.entities.IndexedPage;

@Repository
public interface IndexedPageRepository extends JpaRepository<IndexedPage, Long> {

	@Query(value="SELECT COUNT(p.id) FROM IndexedPage p WHERE p.crawler.id = :crawlerId")
	int countByCrawlerId(@Param("crawlerId") long crawlerId);
	
	@Query(value="DELETE FROM IndexedPage p WHERE p.title IS NULL OR p.title ='' AND p.crawler.id = :crawlerId")
	void deleteByEmptyTitle(@Param("crawlerId") long crawlerId);

	@Query(value="SELECT p FROM IndexedPage p WHERE p.title like :keyword OR p.trainingProgram like :keyword OR p.objectives like :keyword")
	Page<IndexedPage> findAllByKeyword(@Param("keyword")String keyword, Pageable p);

	@Query(value="SELECT COUNT(p) FROM IndexedPage p WHERE p.title like :keyword OR p.trainingProgram like :keyword OR p.objectives like :keyword")
	long countByKeyword(@Param("keyword")String keyword);
	
	long countByReferenceContainingOrTitleContainingOrDurationContaining(String reference,String title, String duration);
	
}
