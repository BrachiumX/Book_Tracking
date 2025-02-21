package com.brachium.book_tracking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
        String url = "https://www.googleapis.com/books/v1/volumes/" + googleId;
        RestTemplate restTemplate = new RestTemplate();
        GoogleBookList.GoogleBook googleBook = restTemplate.getForObject(url, GoogleBookList.GoogleBook.class);
        if (googleBook == null) {
            return null;
        }
        return googleBook.convertToBook();
    }

    @GetMapping
    public @ResponseBody Iterable<Book> searchGoogleApi(@RequestParam(defaultValue = "") String query,
                                                        @RequestParam(defaultValue = "") String titleParameter,
                                                        @RequestParam(defaultValue = "") String authorParameter,
                                                        @RequestParam(defaultValue = "") String isbn) throws IOException, InterruptedException {

        if (query.isEmpty() && isbn.isEmpty() && titleParameter.isEmpty() && authorParameter.isEmpty()) {
            return null;
        }

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

        String url = "https://www.googleapis.com/books/v1/volumes?q=" + uri + "&maxResults=" + maxResults + "&startIndex=";

        RestTemplate restTemplate = new RestTemplate();
        int currIndex = 0;
        List<Book> result = new LinkedList<>();
        GoogleBookList googleBookList = restTemplate.getForObject(url + currIndex, GoogleBookList.class);

        if (googleBookList == null) {
            return null;
        }
        result.addAll(googleBookList.convertList());
        int itemCount = googleBookList.getTotalItems();

        List<Thread> threadList = new LinkedList<>();

        while (currIndex < itemCount) {
            MakeGoogleBookListQuery task = new MakeGoogleBookListQuery(url, result, currIndex);
            Thread thread = new Thread(task);
            thread.start();
            threadList.add(new Thread(task));
            currIndex += maxResults;
        }

        for (Thread a : threadList) {
            a.join();
        }

        return result;
    }

    private static class MakeGoogleBookListQuery implements Runnable {

        final private String url;
        final private List<Book> list;
        final private int currIndex;

        MakeGoogleBookListQuery(String url, List<Book> list, int currIndex) {
           this.url = url;
           this.list = list;
           this.currIndex = currIndex;
        }

        @Override
        public void run() {
            RestTemplate restTemplate = new RestTemplate();
            GoogleBookList googleList = restTemplate.getForObject(url + currIndex, GoogleBookList.class);
            if (googleList == null) {
                return;
            }
            synchronized (list) {
                list.addAll(googleList.convertList());
            }
        }
    }
}