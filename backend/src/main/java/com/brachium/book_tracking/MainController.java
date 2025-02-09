package com.brachium.book_tracking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping(path="/api")
public class MainController {
  @Autowired
  private BookRepository bookRepository;

  @PostMapping(path="/add")
  public @ResponseBody String addNewBook (@RequestParam String name
      , @RequestParam String isbn, @RequestParam int pageCount, @RequestParam String language
      , @RequestParam String author, @RequestParam String description, @RequestParam int year) {

    isbn = isbn.replaceAll("-","");

    if(getByIsbn(isbn).iterator().hasNext()) {
      return "A book with this ISBN already exists";
    }

    if(!language.equals("eng") && !language.equals("tr") && !language.equals("ger")) {
      return "This language is not supported";
    }

    if(pageCount < 0) {
      return "Page Count must be non-negative";
    }
    
    Book book = new Book(name, isbn, pageCount, language, author, description, year);

    bookRepository.save(book);

    return "Saved";
  }

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
    return bookRepository.findByName(name);
  }

  @GetMapping(path="/isbn/{isbn}")
  public @ResponseBody Iterable<Book> getByIsbn(@PathVariable String isbn) {
    return bookRepository.findByIsbn(isbn);
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
    return bookRepository.findByYearBetween(lowerLimit, upperLimit);
  }

  @GetMapping(path="/year/gre")
  public @ResponseBody Iterable<Book> getYearGreaterThan(@RequestParam int lowerLimit) {
    return bookRepository.findByYearBetween(lowerLimit, Integer.MAX_VALUE);
  }

  @GetMapping(path="/year/les")
  public @ResponseBody Iterable<Book> getYearLessThan(@RequestParam int upperLimit) {
    return bookRepository.findByYearBetween(0, upperLimit);
  }

  @GetMapping(path="/year/exact")
  public @ResponseBody Iterable<Book> getYearExact(@RequestParam int year) {
    return bookRepository.findByYearBetween(year, year);
  }

  @GetMapping(path="/author")
  public @ResponseBody Iterable<Book> getByAuthor(@RequestParam String author) {
    return bookRepository.findByAuthor(author);
  }
}