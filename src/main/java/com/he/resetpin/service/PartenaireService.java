package com.he.resetpin.service;


import java.util.Date;
import java.util.Calendar;

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
        
        // p.setPin(Integer.toString(nb));
        return partenaireRepository.save(p);
    }

    public Boolean verificatePin(Partenaire p){
        partenaireRepository.findByEmail(p.getEmail()).getPin(); 
        return true;
    }

    public Boolean reinitialisablePin(Partenaire p){
        Boolean reinitiable = false;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 5);
        p.setReinitialisable(true);
        p.setDateReinitialisation(cal.getTime());
        p = partenaireRepository.save(p);
        if(p.getReinitialisable()){
            reinitiable = true;
        }
        return reinitiable;
    }



}
