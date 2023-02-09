package com.he.resetpin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.he.resetpin.model.Partenaire;

public interface PartenaireRepository extends JpaRepository<Partenaire, Integer>{
    
    Partenaire findByEmail(String email);
    Partenaire findById(int id);
    // Page<Cour> findAll(Pageable pageable);
}
