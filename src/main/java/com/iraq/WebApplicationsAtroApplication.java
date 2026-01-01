package com.iraq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
		"Componte",
		"Confgrution",
		"Service",
		"Controllre",
		"JwtsManager"
})
@EnableJpaRepositories("Reposteryes")
@EntityScan("model")
// صارلي سبوع شتقل عليه
// نسيت ربطهم بي بكج وحده ونلاصت
public class WebApplicationsAtroApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplicationsAtroApplication.class, args);
	}

}
