package com.he.resetpin.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.he.resetpin.model.CodeOTP;
import com.he.resetpin.model.Partenaire;
import com.he.resetpin.model.verificateCodeOTP;
import com.he.resetpin.repository.CodeOTPRepository;

@Service
public class CodeOTPService {
    
    @Autowired
    private CodeOTPRepository codeOTPRepository;

    public Boolean verificateCodeOTP(verificateCodeOTP recherchecode){
        Boolean verification = false;
        CodeOTP code = new CodeOTP();
        code = codeOTPRepository.findByCodeOTPAndByPartenaireEmail(recherchecode.getCode(), recherchecode.getEmail());
        // Verification de la date de validation
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        if(cal.getTime().after(code.getDateExpiration()));
            verification = true;
        
        return verification;
    }

    public CodeOTP createCode(Partenaire p){
        CodeOTP code = new CodeOTP();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        code.setCode(stringRandom());
        code.setDateCreate(cal.getTime());
        cal.add(Calendar.MINUTE, 2);
        code.setDateExpiration(cal.getTime());
        code.setPartenaireID(p.getId());
        
        return codeOTPRepository.save(code);

    }

    private String stringRandom(){
        String str = "";
        for(int i = 0; i<10; i++){
            Random rand = new Random();
            char c = (char)(rand.nextInt(26) + 97);
            str += c;
        }
        return str;
    }
    
}
