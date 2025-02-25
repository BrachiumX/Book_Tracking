package com.brachium.book_tracking.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;

    private int googleId;
    private String email;
    private String name;
    private int goal;

    public User() {}

}
