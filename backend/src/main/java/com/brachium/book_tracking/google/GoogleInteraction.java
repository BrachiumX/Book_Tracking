package com.brachium.book_tracking.google;

import com.brachium.book_tracking.book.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@AllArgsConstructor
@Component
public class GoogleInteraction {

    RestClient restClient;

    public Book searchByGoogleId(String googleId) {
        String url = "https://www.googleapis.com/books/v1/volumes/" + googleId;
        GoogleBookList.GoogleBook googleBook;
        try {
            googleBook = restClient.get().uri(url).retrieve().body(GoogleBookList.GoogleBook.class);
        } catch (Exception e) {
            return null;
        }
        if (googleBook == null) {
            return null;
        }
        return googleBook.convertToBook();
    }

    public List<Book> makeGoogleApiRequest(String query, String titleParameter, String authorParameter, String isbn, int pageSize, int currentPage) {
        if (query.isEmpty() && isbn.isEmpty() && titleParameter.isEmpty() && authorParameter.isEmpty()) {
            return null;
        }

        String uri = query;

        if (!isbn.isEmpty()) {
            uri += "isbn:" + isbn + "+";
        }
        if (!titleParameter.isEmpty()) {
            uri += "intitle:" + titleParameter + "+";
        }
        if (!authorParameter.isEmpty()) {
            uri += "inauthor:" + authorParameter + "+";
        }

        int currIndex = currentPage * pageSize;
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + uri + "&maxResults=" + pageSize + "&startIndex=" + currIndex;
        GoogleBookList googleBookList;
        try {
            googleBookList = restClient.get().uri(url).retrieve().body(GoogleBookList.class);
        } catch (Exception e) {
            return null;
        }
        if (googleBookList == null) {
            return null;
        }
        return googleBookList.convertList();
    }
}
