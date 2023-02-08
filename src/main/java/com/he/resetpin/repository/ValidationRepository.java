package com.he.resetpin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.he.resetpin.model.Validation;

public interface ValidationRepository extends JpaRepository<Validation, Integer>{
    
    Page<Validation> findByStatus(Boolean status ,Pageable pageable);
}
