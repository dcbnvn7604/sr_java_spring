package com.sr.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sr.spring.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
    public List<Job> findByStatus(int status);
}
