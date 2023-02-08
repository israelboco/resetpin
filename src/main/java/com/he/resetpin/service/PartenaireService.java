package com.he.resetpin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.resetpin.model.Partenaire;
import com.he.resetpin.repository.PartenaireRepository;

@Service
public class PartenaireService {
    
    @Autowired
    private PartenaireRepository partenaireRepository;

    public Partenaire getPartenaireByEmail(String email){
        return partenaireRepository.findByEmail(email);
    }

    public Partenaire savePartenaire(Partenaire p){
        return partenaireRepository.save(p);
    }

    public Partenaire updatePartenaire(Partenaire p){
        return partenaireRepository.save(p);
    }

}
