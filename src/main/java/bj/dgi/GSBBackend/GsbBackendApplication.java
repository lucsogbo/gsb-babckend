package bj.dgi.GSBBackend;

import bj.dgi.GSBBackend.entities.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
@EnableJpaAuditing
@EntityScan(basePackageClasses = {
		GsbBackendApplication.class,
		Jsr310JpaConverters.class
})
@EnableTransactionManagement

public class GsbBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GsbBackendApplication.class, args);
	}

}
