package com.brachium.book_tracking.google.book;

import com.brachium.book_tracking.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBookList {

    private int totalItems;
    private String kind;
    private LinkedList<GoogleBook> items;

    @Data
    public static class GoogleBook {
        private String kind;
        private String id;
        private VolumeInfo volumeInfo;

        @Data
        public static class VolumeInfo {
            private String title;
            private LinkedList<String> authors;
            private String publishedDate;
            private String publisher;
            private String description;
            private LinkedList<IndustryIdentifier> industryIdentifiers;
            private int pageCount;
            private LinkedList<String> categories;
            private ImageLinks imageLinks;
            private String language;

            @Data
            public static class IndustryIdentifier {
                private String type;
                private String identifier;
            }

            @Data
            public static class ImageLinks {
                private String smallThumbnail;
                private String thumbnail;
            }
        }

        public Book convertToBook() {
            if (this.volumeInfo == null) {
                return null;
            }

            String isbn13 = "";
            String isbn10 = "";
            String author = "";
            String categories = "";
            String imageLink = "";

            if (this.volumeInfo.authors != null) {
                author = this.volumeInfo.authors.getFirst();
            }

            if (this.volumeInfo.categories != null) {
                categories = this.volumeInfo.categories.getFirst();
            }

            if (this.volumeInfo.imageLinks != null) {
                imageLink = this.volumeInfo.imageLinks.thumbnail;
            }

            for (int i = 0; this.volumeInfo.industryIdentifiers != null && i < this.volumeInfo.industryIdentifiers.size(); i++) {
                if(this.volumeInfo.industryIdentifiers.get(i).type.equals("ISBN_13")) {
                    isbn13 = this.volumeInfo.industryIdentifiers.get(i).identifier;
                }
                else if(this.volumeInfo.industryIdentifiers.get(i).type.equals("ISBN_10")) {
                    isbn10 = this.volumeInfo.industryIdentifiers.get(i).identifier;
                }
            }

            int publishedYear = 0;

            if (this.volumeInfo.publishedDate != null) {
                publishedYear = Integer.parseInt(this.volumeInfo.publishedDate.split("-")[0]);
            }

            return new Book(0,
                    this.id,
                    this.volumeInfo.title,
                    author,
                    publishedYear,
                    this.volumeInfo.publisher,
                    this.volumeInfo.description,
                    isbn13,
                    isbn10,
                    this.volumeInfo.pageCount,
                    categories,
                    imageLink,
                    this.volumeInfo.language);
        }
    }

    public List<Book> convertList() {
        if (this.items == null) {
            return new LinkedList<>();
        }
        List<Book> result = new LinkedList<>();
        for (GoogleBook a : this.items) {
            result.add(a.convertToBook());
        }
        return result;
    }
}
