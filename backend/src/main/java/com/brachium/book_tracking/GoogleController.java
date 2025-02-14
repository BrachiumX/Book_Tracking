package com.brachium.book_tracking;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.LinkedList;

@Controller
@RequestMapping(path="/google")
public class GoogleController {

    final int maxResults = 40;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public @ResponseBody Iterable<Book> getGoogleApiSearch(@RequestParam(defaultValue = "") String titleParameter,
                                                        @RequestParam(defaultValue = "") String authorParameter,
                                                        @RequestParam(defaultValue = "") String isbn,
                                                        @RequestParam(defaultValue = "") String oclc,
                                                        @RequestParam(defaultValue = "") String lccn) throws IOException {
        return searchGoogleApi(titleParameter, authorParameter, isbn, oclc, lccn);
    }

    @PostMapping
    public @ResponseBody String selectBookFromGoogle(@RequestParam String googleId) throws IOException {
        Book selectedBook = searchByGoogleId(googleId);
        if (selectedBook == null) {
            return "Error";
        }
        bookRepository.save(selectedBook);
        return "Saved";
    }

    public Book searchByGoogleId(String googleId) throws IOException {
        String uri = "https://www.googleapis.com/books/v1/volumes/" + googleId;
        ObjectMapper objectMapper = new ObjectMapper();
        RestClient client = RestClient.create();
        byte[] data;

        data = client.get()
                .uri(uri)
                .retrieve()
                .body(byte[].class);

        JsonNode node = objectMapper.readTree(data);

        if (!node.path("kind").asText().equals("books#volume")) {
            return null;
        }

        return parseBookFromJSON(node);
    }

    public Iterable<Book> searchGoogleApi(String titleParameter, String authorParameter, String isbn, String oclc, String lccn) throws IOException {
        if (isbn.isEmpty() && titleParameter.isEmpty() && authorParameter.isEmpty() && oclc.isEmpty() && lccn.isEmpty()) {
            return null;
        }

        String uri = "";

        if(!isbn.isEmpty()) {
            uri += "isbn:" + isbn + "+";
        }
        if (!titleParameter.isEmpty()) {
            uri += "intitle:" + titleParameter + "+";
        }
        if (!authorParameter.isEmpty()) {
            uri += "inauthor:" + authorParameter + "+";
        }
        if(!oclc.isEmpty()) {
            uri += "oclc:" + oclc + "+";
        }
        if(!lccn.isEmpty()) {
            uri += "lccn:" + lccn + "+";
        }

        if(uri.isEmpty()) {
            return null;
        }

        uri = "https://www.googleapis.com/books/v1/volumes?q=" + uri;

        ObjectMapper objectMapper = new ObjectMapper();
        RestClient client = RestClient.create();
        byte[] data;
        int currIndex = 0;
        int itemCount = 1;
        LinkedList<Book> bookList = new LinkedList<>();

        while (currIndex < itemCount) {
            data = client.get()
                    .uri(uri + "&maxResults=" + maxResults + "&startIndex=" + currIndex)
                    .retrieve()
                    .body(byte[].class);

            JsonNode rootNode = objectMapper.readTree(data);
            JsonNode items = rootNode.path("items");
            itemCount = rootNode.path("totalItems").asInt();

            for (int i = 0; i < maxResults && i < itemCount - currIndex; i++) {
                Book n = parseBookFromJSON(items.path(i));
                if (n != null) {
                    bookList.add(n);
                }
            }
            currIndex += maxResults;
        }

        return bookList;
    }

    public Book parseBookFromJSON(JsonNode rootNode) {
        String googleId = rootNode.path("id").asText();
        JsonNode volumeInfo = rootNode.path("volumeInfo");
        String title = volumeInfo.path("title").asText();
        int year;
        try {
            year = Integer.parseInt(volumeInfo.path("publishedDate").asText().split("-")[0]);
        } catch (Exception ex) {
            year = 0;
        }
        int pageCount = volumeInfo.path("pageCount").asInt();
        String description = volumeInfo.path("description").asText();
        String author = volumeInfo.path("authors").path(0).asText();

        JsonNode industryIdents = volumeInfo.path("industryIdentifiers");
        String isbn10 = "";
        String isbn13 = "";
        String oclc = "";
        String lccn = "";

        for (int j = 0; j < 3; j++) {
            JsonNode curr = industryIdents.path(j);

            if (curr.path("type").asText().equals("ISBN_10")) {
                isbn10 = curr.path("identifier").asText();
            } else if (curr.path("type").asText().equals("ISBN_13")) {
                isbn13 = curr.path("identifier").asText();
            } else if (curr.path("type").asText().equals("OTHER") ) {
                String[] identifier = curr.path("identifier").asText().split(":");
                if (identifier[0].equals("OCLC")) {
                    oclc = identifier[1];
                }
                else if (identifier[0].equals("LCCN")) {
                    lccn = identifier[1];
                }
            }
        }

        String language = volumeInfo.path("language").asText();
        return new Book(title, isbn10, isbn13, oclc, lccn, googleId, pageCount, language, author, description, year);
    }
}