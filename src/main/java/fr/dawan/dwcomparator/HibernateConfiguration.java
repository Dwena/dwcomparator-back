package fr.dawan.dwcomparator;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import fr.dawan.dwcomparator.interceptors.AuditInterceptor;

@Configuration
public class HibernateConfiguration implements HibernatePropertiesCustomizer {

    @Autowired
    private AuditInterceptor auditInterceptor;

	@Override
	public void customize(Map<String, Object> hibernateProperties) {
		hibernateProperties.put("hibernate.ejb.interceptor",auditInterceptor);
	}
}