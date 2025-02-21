package com.brachium.book_tracking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path="/google")
public class GoogleController {

    final int maxResults = 40;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public @ResponseBody String selectBookFromGoogle(@RequestParam String googleId) throws IOException {
        Book selectedBook = searchByGoogleId(googleId);
        try {
            bookRepository.save(selectedBook);
        } catch (Exception e) {
            return "Error";
        }
        return "Saved " + selectedBook.getId();
    }

    public Book searchByGoogleId(String googleId) throws IOException {
        String uri = "https://www.googleapis.com/books/v1/volumes/" + googleId;
        RestTemplate restTemplate = new RestTemplate();
        return Objects.requireNonNull(restTemplate.getForObject(uri, GoogleBookList.GoogleBook.class)).convertToBook();
    }

    @GetMapping
    public Iterable<Book> searchGoogleApi(@RequestParam(defaultValue = "") String query,
                                          @RequestParam(defaultValue = "") String titleParameter,
                                          @RequestParam(defaultValue = "") String authorParameter,
                                          @RequestParam(defaultValue = "") String isbn) throws IOException {

        if (query.isEmpty() && isbn.isEmpty() && titleParameter.isEmpty() && authorParameter.isEmpty()) {
            return null;
        }

        String url = "https://www.googleapis.com/books/v1/volumes?q=";
        String uri = query;

        if(!isbn.isEmpty()) {
            uri += "isbn:" + isbn + "+";
        }
        if (!titleParameter.isEmpty()) {
            uri += "intitle:" + titleParameter + "+";
        }
        if (!authorParameter.isEmpty()) {
            uri += "inauthor:" + authorParameter + "+";
        }

        RestTemplate restTemplate = new RestTemplate();
        int currIndex = 0;
        GoogleBookList googleBookList = restTemplate.getForObject(url + uri + "&maxResults=" + maxResults + "&startIndex=" + currIndex, GoogleBookList.class);
        List<Book> result = new LinkedList<>();

        if (googleBookList == null) {
            return null;
        }
        int itemCount = googleBookList.getTotalItems();

        while (true) {
            if (googleBookList == null) {
                return null;
            }
            result.addAll(googleBookList.convertList());
            currIndex += maxResults;

            if (currIndex >= itemCount) {
                break;
            }
            googleBookList = restTemplate.getForObject(url + uri + "&maxResults=" + maxResults + "&startIndex=" + currIndex, GoogleBookList.class);
        }
        return result;
    }
}