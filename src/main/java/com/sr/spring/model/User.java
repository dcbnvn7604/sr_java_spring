package com.sr.spring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @Column(unique = true)
    private String username;
    @NonNull
    @Getter
    private String password;
}
