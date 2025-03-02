package com.brachium.book_tracking.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Entity
@Table(name="user_table")
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
