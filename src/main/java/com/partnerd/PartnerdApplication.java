package com.partnerd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.partnerd.repository")
@EnableR2dbcRepositories(basePackages = "com.partnerd.r2dbc")
@EnableMongoRepositories(basePackages = "com.partnerd.repository.chatRoomRepository.mongoRepository")
@EnableKafka
@EnableAsync
public class PartnerdApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PartnerdApplication.class);
		app.setAdditionalProfiles("default"); // 기본 프로파일 명시
		app.run(args);
	}

}
