package com.brachium.book_tracking;

import jakarta.persistence.*;

import java.util.LinkedList;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String googleId;
    private String title;
    private String author;
    private int publishedYear;
    private String publisher;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    @Column(unique = true)
    private String isbn13;
    @Column(unique = true)
    private String isbn10;
    private int pageCount;
    private String category;
    private String imageLink;
    private String language;

    public Book() {}

    public Book(String googleId, String title, String author, int publishedYear, String publisher, String description, String isbn13, String isbn10, int pageCount, String category, String imageLink, String language) {
        this.googleId = googleId;
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.publisher = publisher;
        this.description = description;
        this.isbn13 = isbn13;
        this.isbn10 = isbn10;
        this.pageCount = pageCount;
        this.category = category;
        this.imageLink = imageLink;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
