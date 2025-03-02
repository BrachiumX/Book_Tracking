package com.brachium.book_tracking.user;

import com.brachium.book_tracking.book.Book;
import com.brachium.book_tracking.book.BookRepository;
import com.brachium.book_tracking.library.LibraryRelation;
import com.brachium.book_tracking.library.LibraryRelationRepository;
import com.brachium.book_tracking.library.LibraryStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class UserLibrary {
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private LibraryRelationRepository libraryRelationRepository;

    public String addBookToUserLibrary(int userId, int bookId) {
        User user = userRepository.findById(userId);
        Book book = bookRepository.findById(bookId);
        if(user == null || book == null) {
            return "Error";
        }
        libraryRelationRepository.save(new LibraryRelation(0, bookId, userId, LibraryStatus.UNKNOWN, 0, 0));
        return "Saved";
    }

    public String updateStatus(int userId, int bookId, LibraryStatus status) {
        LibraryRelation libraryRelation = libraryRelationRepository.getByUserIdAndBookId(userId, bookId);
        if(libraryRelation == null) {
            return "Error";
        }
        libraryRelation.setStatus(status);
        libraryRelationRepository.save(libraryRelation);
        return "Saved";
    }

    public String updateInitialRating(int userId, int bookId, int rating) {
        LibraryRelation libraryRelation = libraryRelationRepository.getByUserIdAndBookId(userId, bookId);
        if(libraryRelation == null) {
            return "Error";
        }
        libraryRelation.setInitialRating(rating);
        libraryRelationRepository.save(libraryRelation);
        return "Saved";
    }

    public String updateEndRating(int userId, int bookId, int rating) {
        LibraryRelation libraryRelation = libraryRelationRepository.getByUserIdAndBookId(userId, bookId);
        if(libraryRelation == null) {
            return "Error";
        }
        libraryRelation.setEndRating(rating);
        libraryRelationRepository.save(libraryRelation);
        return "Saved";
    }
}
