package com.he.resetpin.service;


import java.util.Date;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
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

    public String encodePin(String pin, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException{
        KeySpec spec = new PBEKeySpec(pin.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return hash.toString();
    }

    public byte[] salt(){
        SecureRandom random = new SecureRandom();
        byte[] salts = new byte[16];
        random.nextBytes(salts); 
        return salts;
    }

    public Partenaire createPin(Partenaire p, String pin) throws NoSuchAlgorithmException, InvalidKeySpecException{
        byte[] salts = new byte[16];
        salts = salt();
        p.setSalt(salts.toString());
        p.setPin(encodePin(pin, salts));
        return partenaireRepository.save(p);
    }

    public Boolean verificatePin(Partenaire p, String pin2) throws NoSuchAlgorithmException, InvalidKeySpecException{
        Boolean verification = false;
        String pin;
        String salt;
        String encodePinVerificate;
        pin = partenaireRepository.findByEmail(p.getEmail()).getPin(); 
        salt = partenaireRepository.findByEmail(p.getEmail()).getSalt(); 
        encodePinVerificate = encodePin(pin2, salt.getBytes(StandardCharsets.UTF_8));
        if(pin == encodePinVerificate){
            verification = true;
        }
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
