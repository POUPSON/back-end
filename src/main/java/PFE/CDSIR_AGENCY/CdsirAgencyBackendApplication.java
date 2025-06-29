package PFE.CDSIR_AGENCY;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync // Pour les méthodes asynchrones
@EnableScheduling // Pour les tâches planifiées
@ComponentScan(basePackages = {
		"PFE.CDSIR_AGENCY",
		"PFE.CDSIR_AGENCY.config",
		"PFE.CDSIR_AGENCY.controller",
		"PFE.CDSIR_AGENCY.service",
		"PFE.CDSIR_AGENCY.repository",
		"PFE.CDSIR_AGENCY.entity",
		"PFE.CDSIR_AGENCY.dto",
		"PFE.CDSIR_AGENCY.exception"
})
public class CdsirAgencyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CdsirAgencyBackendApplication.class, args);
	}
}