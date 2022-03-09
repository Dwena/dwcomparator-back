package fr.dawan.dwcomparator.repositories;

import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.dwcomparator.entities.UrlToCrawl;

@Repository
public interface UrlToCrawlRepository extends JpaRepository<UrlToCrawl, Long> {

	@Query(value = "SELECT u.id FROM UrlToCrawl u WHERE u.trainingCenter.id = :trainingCenterId")
	List<Long> findIdsByTrainingCenterId(@Param("trainingCenterId") long trainingCenterId);

	@Query(value = "FROM UrlToCrawl u JOIN FETCH u.searchExpressionByLevel WHERE u.id = :id")
	Optional<UrlToCrawl> findById(long id);

	@Query(value = "SELECT u FROM UrlToCrawl u WHERE u.trainingCenter.id = :trainingCenterId")
	List<UrlToCrawl> findByTrainingCenterId(@Param("trainingCenterId") long trainingCenterId);
	
	long countByUrlContaining(String url);

	Page<UrlToCrawl> findAllByUrlContaining(String url, Pageable pageable);
	

}
