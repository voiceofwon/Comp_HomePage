package com.CompD.A.Compage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CompageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompageApplication.class, args);
	}

}
