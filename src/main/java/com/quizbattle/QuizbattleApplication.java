package com.quizbattle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class QuizbattleApplication {

	public static void main(String[] args) {

		SpringApplication.run(QuizbattleApplication.class, args);
	}

}
