package com.example.auctionarena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AuctionarenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionarenaApplication.class, args);
	}

}
