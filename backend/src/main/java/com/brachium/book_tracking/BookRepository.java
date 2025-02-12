package com.brachium.book_tracking;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {

    Iterable<Book> findByLanguage(String language);
    Iterable<Book> findByName(String name);
    Iterable<Book> findByIsbn(String isbn);
    Iterable<Book> findByAuthor(String author);
    Iterable<Book> findByPageCountBetween(int upperLimit, int lowerLimit);
    Iterable<Book> findByYearBetween(int upperLimit, int lowerLimit);
    void deleteById(int id);
}