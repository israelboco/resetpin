package com.he.resetpin.service;

import java.util.Random;

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
        p.setReinitialisable(false);
        p.setPin(null);
        p.setSalt(null);
        return partenaireRepository.save(p);
    }

    public Partenaire updatePartenaire(Partenaire p){
        return partenaireRepository.save(p);
    }

    public Partenaire createPin(Partenaire p){
        Random random = new Random();
        // String encodePin;
        int nb;
        nb = 10000 + random.nextInt(89999);
        // encodePin = hashCode();
        p.setPin(Integer.toString(nb));
        return partenaireRepository.save(p);
    }

}
