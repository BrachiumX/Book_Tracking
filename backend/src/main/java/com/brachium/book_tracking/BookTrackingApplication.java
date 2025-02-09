package com.brachium.book_tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class BookTrackingApplication {

	@Autowired
	private BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookTrackingApplication.class, args);
	}

}
