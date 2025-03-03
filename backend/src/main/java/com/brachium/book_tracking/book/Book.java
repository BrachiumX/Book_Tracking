package com.brachium.book_tracking.book;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Entity
public class Book {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String googleId;
    private String title;
    private String author;
    private int publishedYear;
    private String publisher;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(unique = true)
    private String isbn13;
    @Column(unique = true)
    private String isbn10;
    private int pageCount;
    private String category;
    @Column(columnDefinition = "TEXT")
    private String imageLink;
    private String language;

    public Book() {}
}
