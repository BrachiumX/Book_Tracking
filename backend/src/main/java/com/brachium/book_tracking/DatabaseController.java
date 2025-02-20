package com.brachium.book_tracking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/data")
public class DatabaseController {
  @Autowired
  protected BookRepository bookRepository;

  @GetMapping(path="/all")
  public @ResponseBody Iterable<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  @GetMapping(path="/lan/{language}")
  public @ResponseBody Iterable<Book> getByLanguage(@PathVariable String language) {
    return bookRepository.findByLanguage(language);
  }

  @GetMapping(path="/name/{name}")
  public @ResponseBody Iterable<Book> getByName(@PathVariable String name) {
    return bookRepository.findByTitle(name);
  }

  @GetMapping(path="/isbn10/{isbn10}")
  public @ResponseBody Iterable<Book> getByIsbn10(@PathVariable String isbn10) {
    return bookRepository.findByIsbn10(isbn10);
  }

  @GetMapping(path="/isbn13/{isbn13}")
  public @ResponseBody Iterable<Book> getByIsbn13(@PathVariable String isbn13) {
    return bookRepository.findByIsbn13(isbn13);
  }

  @GetMapping(path="/googleId/{googleId}")
  public @ResponseBody Iterable<Book> getByGoogleId(@PathVariable String googleId) {
    return bookRepository.findByGoogleId(googleId);
  }

  @GetMapping(path="/page/intv")
  public @ResponseBody Iterable<Book> getPageInterval(@RequestParam int lowerLimit, @RequestParam int upperLimit) {
    return bookRepository.findByPageCountBetween(lowerLimit, upperLimit);
  }

  @GetMapping(path="/page/gre")
  public @ResponseBody Iterable<Book> getPageGreaterThan(@RequestParam int lowerLimit) {
    return bookRepository.findByPageCountBetween(lowerLimit, Integer.MAX_VALUE);
  }

  @GetMapping(path="/page/les")
  public @ResponseBody Iterable<Book> getPageLessThan(@RequestParam int upperLimit) {
    return bookRepository.findByPageCountBetween(0, upperLimit);
  }

  @GetMapping(path="/page/exact")
  public @ResponseBody Iterable<Book> getPageExact(@RequestParam int page) {
    return bookRepository.findByPageCountBetween(page, page);
  }

  @GetMapping(path="/year/intv")
  public @ResponseBody Iterable<Book> getYearInterval(@RequestParam int lowerLimit, @RequestParam int upperLimit) {
    return bookRepository.findByPublishedYearBetween(lowerLimit, upperLimit);
  }

  @GetMapping(path="/year/gre")
  public @ResponseBody Iterable<Book> getYearGreaterThan(@RequestParam int lowerLimit) {
    return bookRepository.findByPublishedYearBetween(lowerLimit, Integer.MAX_VALUE);
  }

  @GetMapping(path="/year/les")
  public @ResponseBody Iterable<Book> getYearLessThan(@RequestParam int upperLimit) {
    return bookRepository.findByPublishedYearBetween(0, upperLimit);
  }

  @GetMapping(path="/year/exact")
  public @ResponseBody Iterable<Book> getYearExact(@RequestParam int year) {
    return bookRepository.findByPublishedYearBetween(year, year);
  }

  @GetMapping(path="/author")
  public @ResponseBody Iterable<Book> getByAuthor(@RequestParam String author) {
    return bookRepository.findByAuthor(author);
  }

  //Controller for testing purposes
  //This shouldn't be exposed to the outside
  @Controller
  @RequestMapping(path="/test")
  private class TestController {

    @DeleteMapping(path="/reset")
    private @ResponseBody String resetDatabase() {
      bookRepository.deleteAll();
      return "Database reset";
    }

    @DeleteMapping(path="/delete")
    private @ResponseBody String deleteById(int id) {
      bookRepository.deleteById(id);
      return "Entry deleted";
    }
  }
}