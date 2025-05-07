package com.sr.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sr.spring.model.User;

public interface UserRRepository extends JpaRepository<User, Long>{
    Page<User> findAll(Pageable pageable);
}
