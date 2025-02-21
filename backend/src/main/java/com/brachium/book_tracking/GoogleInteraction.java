package com.brachium.book_tracking;

import org.springframework.web.client.RestTemplate;

import java.util.List;


public class GoogleInteraction {

    public static Book searchByGoogleId(String googleId) {
        String url = "https://www.googleapis.com/books/v1/volumes/" + googleId;
        RestTemplate restTemplate = new RestTemplate();
        GoogleBookList.GoogleBook googleBook = restTemplate.getForObject(url, GoogleBookList.GoogleBook.class);
        if (googleBook == null) {
            return null;
        }
        return googleBook.convertToBook();
    }

    public static List<Book> makeGoogleApiRequest(String query, String titleParameter, String authorParameter, String isbn, int pageSize, int currentPage) {
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

        String url = "https://www.googleapis.com/books/v1/volumes?q=" + uri + "&maxResults=" + pageSize + "&startIndex=";

        int currIndex = currentPage * pageSize;
        RestTemplate restTemplate = new RestTemplate();
        GoogleBookList googleBookList = restTemplate.getForObject(url + currIndex, GoogleBookList.class);

        if (googleBookList == null) {
            return null;
        }

        return googleBookList.convertList();
    }
}
