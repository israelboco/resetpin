package com.he.resetpin.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.resetpin.model.CodeOTP;
import com.he.resetpin.model.Partenaire;
import com.he.resetpin.model.Validation;
import com.he.resetpin.model.VerificationCodeOTP;
import com.he.resetpin.repository.CodeOTPRepository;
import com.he.resetpin.repository.PartenaireRepository;
import com.he.resetpin.repository.ValidationRepository;

@Service
public class CodeOTPService {
    
    @Autowired
    private CodeOTPRepository codeOTPRepository;

    @Autowired
    private ValidationRepository validationRepository;

    @Autowired
    private PartenaireRepository partenaireRepository;

    public Boolean verificateCodeOTP(VerificationCodeOTP recherchecode){
        Boolean verification = false;
        Partenaire p = partenaireRepository.findByEmail(recherchecode.getEmail());
        CodeOTP code = codeOTPRepository.findByCode(recherchecode.getCode());
        // Verification de la date de validation
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        if(code.getDateExpiration().after(cal.getTime()) && code.getPartenaire() == p){
            Validation v = new Validation();
            v.setPartenaire(code.getPartenaire());
            v.setTypeObjet("RĂ©initialisation de pin");
            v.setDateCreate(new Date());
            v.setStatus(false);
            v = validationRepository.save(v);
            verification = true;
        }

        return verification;
    }

    public CodeOTP createCode(Partenaire p){
        CodeOTP code = new CodeOTP();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        code.setCode(codeGenerique());
        code.setDateCreate(cal.getTime());
        cal.add(Calendar.MINUTE, 2);
        code.setDateExpiration(cal.getTime());
        code.setPartenaire(p);
        
        return codeOTPRepository.save(code);

    }

    private String codeGenerique(){
        String str = "";
        for(int i = 0; i<10; i++){
            Random rand = new Random();
            char c = (char)(rand.nextInt(26) + 97);
            str += c;
        }
        return str;
    }
    
}
