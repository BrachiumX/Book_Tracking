package com.brachium.book_tracking.user;

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

    @PostMapping
    public @ResponseBody String addUser(@RequestParam String name) {
        userRepository.save(new User(0, 0, "", name, 0));
        return "Saved";
    }

    @PostMapping(path="/book")
    public @ResponseBody String addBook(@RequestParam int userId, @RequestParam int bookId) {
        libraryRelationRepository.save(new LibraryRelation(0, bookId, userId, LibraryStatus.FINISHED, 0, 0));
        return "Saved";
    }

    @GetMapping(path="/book")
    public @ResponseBody Iterable<Integer> getBookListOfUser(@RequestParam int userId) {
        return libraryRelationRepository.getUserLibrary(userId);
    }
}
