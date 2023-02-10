package com.he.resetpin.service;


import java.util.Date;

// import javax.crypto.SecretKeyFactory;
// import javax.crypto.spec.PBEKeySpec;


// import java.nio.charset.StandardCharsets;
// import java.security.NoSuchAlgorithmException;
// import java.security.SecureRandom;
// import java.security.spec.InvalidKeySpecException;
// import java.security.spec.KeySpec;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.exception.InvalidHashException;
import com.he.resetpin.model.Partenaire;
import com.he.resetpin.repository.PartenaireRepository;

@Service
public class PartenaireService {
    
    @Autowired
    private PartenaireRepository partenaireRepository;

    public Page<Partenaire> getAll(Pageable p){
        return partenaireRepository.findAll(p);
    }

    public Partenaire getPartenaireByEmail(String email){
        return partenaireRepository.findByEmail(email);
    }

    public Partenaire getPartenaireByTelephone(String telephone){
        return partenaireRepository.findByEmail(telephone);
    }

    public Partenaire savePartenaire(Partenaire p){
        p.setReinitialisable(false);
        p.setPin(null);
        return partenaireRepository.save(p);
    }

    public Partenaire updatePartenaire(Partenaire p){
        return partenaireRepository.save(p);
    }

    // public String encodePin(String pin, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException{
    //     KeySpec spec = new PBEKeySpec(pin.toCharArray(), salt, 65536, 128);
    //     SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    //     byte[] hash = factory.generateSecret(spec).getEncoded();
    //     return hash.toString();
    // }

    // public byte[] salt(){
    //     SecureRandom random = new SecureRandom();
    //     byte[] salts = new byte[16];
    //     random.nextBytes(salts); 
    //     return salts;
    // }


    public Partenaire createPin(Partenaire p, String pin) {
        p.setPin(Hash.password(pin.toCharArray()).create());
        return partenaireRepository.save(p);
    }

    public Boolean verificatePin(Partenaire p, String pin2) throws InvalidHashException {
        Partenaire partenaire = partenaireRepository.findByEmail(p.getEmail());
        String pin = partenaire.getPin(); 
        Boolean verification = Hash.password(pin2.toCharArray()).verify(pin);
        
        return verification;
    }

    public void reinitialisablePin(Partenaire p){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 5);
        p.setReinitialisable(true);
        p.setDateReinitialisation(cal.getTime());
        p = partenaireRepository.save(p);
    }



}
