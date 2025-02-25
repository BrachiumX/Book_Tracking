package com.brachium.book_tracking.library;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LibraryRelationRepository extends CrudRepository<LibraryRelation, Integer> {
    Iterable<LibraryRelation> findByUserId(int id);
    Iterable<LibraryRelation> findByBookId(int id);

    @Query("select u.bookId from LibraryRelation u where u.userId = :userId")
    public Iterable<Integer> getUserLibrary(@Param("userId") int userId);
}
