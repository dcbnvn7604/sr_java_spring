package com.sr.spring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor
@Table(name="\"user\"")
public class User {
    @Id
    private long id;
    @NonNull
    @Getter
    private String username;
    @NonNull
    @Getter
    private String password;
    @NonNull
    @Getter
    private String role;
}
