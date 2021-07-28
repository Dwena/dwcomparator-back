package fr.dawan.dwcomparator.interceptors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.dawan.dwcomparator.entities.AuditLogEntry;
import fr.dawan.dwcomparator.repositories.AuditLogEntryRepository;

/**
 * Intercepteur pour enregistrer les modifications effectuées dans la table
 * AuditLogEntry sur les entités qui implémentent Auditable
 * 
 * @author Mohamed
 *
 */
@SuppressWarnings("serial")
@Component
public class AuditInterceptor extends EmptyInterceptor  {

	@Autowired
	private AuditLogEntryRepository auditLogEntryRepository;


	public AuditInterceptor() {

	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		return onFlushDirty(entity, id, state, null, propertyNames, types);
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		if (entity instanceof Auditable) {
			List<AuditLogEntry> logEntries = new ArrayList<AuditLogEntry>();
			for (int i = 0; i < propertyNames.length; i++) {
				if (currentState != null && currentState[i] != null && !(propertyNames[i].contentEquals("version"))) {
					if (previousState == null || currentState[i] != previousState[i]) {
						AuditLogEntry logEntry = new AuditLogEntry();
						logEntry.setCreationDate(LocalDateTime.now());

						if (id != null) {
							logEntry.setEntityIdentifier(id.toString());
						}
						//TODO gérer un id nouveau après sav

						logEntry.setAuditEntryType(types[i].toString());
						logEntry.setEntityName(entity.getClass().getName());
						logEntry.setFieldName(propertyNames[i]);
						
						if (previousState != null && previousState[i]!=null) {
							logEntry.setOldValue(previousState[i].toString());
						}
						logEntry.setNewValue(currentState[i].toString());

						// TODO trouver un moyen de récupérer le userId
						// (exploiter le token + réfléchir au passage de ce paramètre)

						logEntries.add(logEntry);
					}
				}
			}
			try {
				auditLogEntryRepository.saveAll(logEntries);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	

}
