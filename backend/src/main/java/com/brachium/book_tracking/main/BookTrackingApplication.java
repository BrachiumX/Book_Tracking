package com.brachium.book_tracking.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookTrackingApplication {

	//@Autowired
	//private BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookTrackingApplication.class, args);
	}

}
