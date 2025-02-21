package com.brachium.book_tracking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/google")
public class GoogleController {

    final int sizeLimit = 100;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public @ResponseBody String selectBookFromGoogle(@RequestParam String googleId) {
        Book selectedBook = GoogleInteraction.searchByGoogleId(googleId);
        try {
            bookRepository.save(selectedBook);
        } catch (DataIntegrityViolationException e) {
            return "Error: A book with this id or isbn already exists.";
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
                                                        @RequestParam(defaultValue = "20") int pageSize,
                                                        @RequestParam(defaultValue = "0") int currentPage) throws InterruptedException {
        return GoogleInteraction.makeGoogleApiRequest(query, titleParameter, authorParameter, isbn, Math.min(pageSize, sizeLimit) , currentPage);
    }


}