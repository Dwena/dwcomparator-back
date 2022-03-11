package fr.dawan.dwcomparator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.dawan.dwcomparator.entities.AuditLogEntry;

@Repository
public interface AuditLogEntryRepository extends JpaRepository<AuditLogEntry, Long> {

}
