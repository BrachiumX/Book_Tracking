package com.brachium.book_tracking.library;

import com.brachium.book_tracking.book.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LibraryRelationRepository extends CrudRepository<LibraryRelation, Integer> {
    Iterable<LibraryRelation> findByUserId(int id);
    Iterable<LibraryRelation> findByBookId(int id);

    @Query("select book from Book book where book.id " +
            "in (select library.bookId from LibraryRelation library where library.userId = :userId)")
    Iterable<Book> getUserLibrary(@Param("userId") int userId);

    LibraryRelation getByUserIdAndBookId(int userId, int bookId);
}
