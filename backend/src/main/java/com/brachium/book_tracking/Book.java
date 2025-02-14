package com.brachium.book_tracking;

import jakarta.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Book {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  private String name;

  private String isbn10;
  private String isbn13;
  private String oclc;
  private String lccn;

  private String googleId;

  private int pageCount;
  private String language;
  private String author;

  @Column(name = "description", columnDefinition = "LONGTEXT")
  private String description;

  private int year;

  public Book() {}

  public Book(String name, String isbn10, String isbn13, String oclc, String lccn, String googleId, int pageCount, String language, String author, String description, int year) {
    this.name = name;
    this.isbn10 = isbn10;
    this.isbn13 = isbn13;
    this.oclc = oclc;
    this.lccn = lccn;
    this.googleId = googleId;
    this.pageCount = pageCount;
    this.language = language;
    this.author = author;
    this.description = description;
    this.year = year;
  }

  public String getGoogleId() {
    return googleId;
  }

  public void setGoogleId(String googleId) {
    this.googleId = googleId;
  }

  public String getOclc() {
    return oclc;
  }

  public void setOclc(String oclc) {
    this.oclc = oclc;
  }

  public String getLccn() {
    return lccn;
  }

  public void setLccn(String lccn) {
    this.lccn = lccn;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPageCount() {
    return pageCount;
  }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getIsbn10() {
    return isbn10;
  }

  public void setIsbn10(String isbn10) {
    this.isbn10 = isbn10;
  }

  public String getIsbn13() {
    return isbn13;
  }

  public void setIsbn13(String isbn13) {
    this.isbn13 = isbn13;
  }
}