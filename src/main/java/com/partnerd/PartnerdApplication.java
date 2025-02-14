package com.partnerd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableJpaAuditing
@EnableMongoRepositories
@EnableKafka
public class PartnerdApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartnerdApplication.class, args);
	}

}
