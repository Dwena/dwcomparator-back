package fr.dawan.dwcomparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import fr.dawan.dwcomparator.interceptors.TokenInterceptor;

@SpringBootApplication
@ComponentScan(basePackages = {"fr.dawan.dwcomparator"})
@EnableScheduling // pour pouvoir utiliser les tâches planifiées
public class DaComparatorApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaComparatorApiApplication.class, args);
	}

	@Autowired
	private TokenInterceptor tokenInterceptor;
	
	@Bean
	public WebMvcConfigurer myMvcConfigurer() {

		return new WebMvcConfigurer() {

			// CROSS ORIGIN
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
					.allowedOrigins("*");
			}

			// Intercepteurs
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(tokenInterceptor);
			}

		};
	}

}
