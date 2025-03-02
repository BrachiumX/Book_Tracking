package com.brachium.book_tracking.google.book;

import com.brachium.book_tracking.book.Book;
import com.brachium.book_tracking.book.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path="/google")
public class GoogleController {

    final int sizeLimit = 40;

    private BookRepository bookRepository;
    private GoogleInteraction googleInteraction;

    @PostMapping
    public @ResponseBody String selectBookFromGoogle(@RequestParam String googleId) {
        Book selectedBook = googleInteraction.searchByGoogleId(googleId);
        try {
            bookRepository.save(selectedBook);
        }
        catch (Exception e) {
            return "Error " + e;
        }
        return "Saved " + selectedBook.getId();
    }

    @GetMapping
    public @ResponseBody Iterable<Book> searchGoogleApi(@RequestParam(defaultValue = "") String query,
                                                        @RequestParam(defaultValue = "") String titleParameter,
                                                        @RequestParam(defaultValue = "") String authorParameter,
                                                        @RequestParam(defaultValue = "") String isbn,
                                                        @RequestParam(defaultValue = "10") int pageSize,
                                                        @RequestParam(defaultValue = "0") int currentPage) {
        return googleInteraction.makeGoogleApiRequest(query, titleParameter, authorParameter, isbn, Math.min(pageSize, sizeLimit) , currentPage);
    }
}