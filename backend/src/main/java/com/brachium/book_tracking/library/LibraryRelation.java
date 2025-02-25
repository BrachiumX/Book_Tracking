package com.brachium.book_tracking.library;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Entity
public class LibraryRelation {
    @Id
    @GeneratedValue
    private int id;

    private int bookId;
    private int userId;
    private LibraryStatus status;
    private int initialRating;
    private int endRating;

    public LibraryRelation() {}

}
