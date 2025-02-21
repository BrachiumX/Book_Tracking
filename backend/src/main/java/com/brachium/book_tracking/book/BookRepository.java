package com.brachium.book_tracking.book;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {

    Iterable<Book> findByLanguage(String language);
    Iterable<Book> findByTitle(String name);
    Iterable<Book> findByIsbn10(String isbn10);
    Iterable<Book> findByIsbn13(String isbn13);
    Iterable<Book> findByGoogleId(String googleId);
    Iterable<Book> findByAuthor(String author);
    Iterable<Book> findByPageCountBetween(int upperLimit, int lowerLimit);
    Iterable<Book> findByPublishedYearBetween(int upperLimit, int lowerLimit);
    void deleteById(int id);
}