package ru.kulbaka.bushunter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BushunterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BushunterApplication.class, args);
	}

}
