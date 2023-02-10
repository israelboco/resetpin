package com.he.resetpin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.he.resetpin.model.Partenaire;

public interface PartenaireRepository extends JpaRepository<Partenaire, Integer>{
    
    Partenaire findByEmail(String email);
    Partenaire findByTelephone(String email);
    Partenaire findById(int id);
    Page<Partenaire> findAll(Pageable pageable);
    // Page<Cour> findAll(Pageable pageable);
}
