package com.he.resetpin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.he.resetpin.model.Validation;
import com.he.resetpin.repository.ValidationRepository;

@Service
public class ValidationService {
    
    @Autowired
    private ValidationRepository validationRepository;

    public Validation saveValidation(Validation v){
        return validationRepository.save(v);
    }

    public Page<Validation> getAll(Pageable p){
        return validationRepository.findByStatus(false, p);
    }

    public Boolean traitementValidation(Validation v){
        Boolean traitement = false;
        v.setStatus(true);
        v = validationRepository.save(v); 
        if(v.getStatus()){
            traitement = true;
        }
        return traitement;
    }
}
