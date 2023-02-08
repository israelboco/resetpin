package com.he.resetpin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.he.resetpin.model.CodeOTP;

@Repository
public interface CodeOTPRepository extends JpaRepository<CodeOTP, Integer>{
    
    CodeOTP findByCodeOTPAndByPartenaireEmail(String code, String email);
}
