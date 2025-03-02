package com.brachium.book_tracking.user;

import com.brachium.book_tracking.book.Book;
import com.brachium.book_tracking.book.BookRepository;
import com.brachium.book_tracking.library.LibraryStatus;
import com.brachium.book_tracking.library.LibraryRelation;
import com.brachium.book_tracking.library.LibraryRelationRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path="/user")
public class UserController {

    private LibraryRelationRepository libraryRelationRepository;
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private UserLibrary userLibrary;

    @PostMapping
    public @ResponseBody String addUser(@RequestParam String name) {
        userRepository.save(new User(0, 0, "", name, 0));
        return "Saved";
    }

    @GetMapping(path="/book")
    public @ResponseBody Iterable<Book> getBookListOfUser(@RequestParam int userId) {
        return libraryRelationRepository.getUserLibrary(userId);
    }

    @GetMapping(path="/test")
    public @ResponseBody String test(@RequestParam int userId, @RequestParam int bookId,
                                     @RequestParam LibraryStatus status, @RequestParam int initialRating,
                                     @RequestParam int endRating) {
        String result = "";
        result += userLibrary.addBookToUserLibrary(userId, bookId);
        result += userLibrary.updateStatus(userId, bookId, status);
        result += userLibrary.updateInitialRating(userId, bookId, initialRating);
        result += userLibrary.updateEndRating(userId, bookId, endRating);
        return result;
    }
}
