package com.brachium.book_tracking.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = )
public class BookTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookTrackingApplication.class, args);
	}

}
