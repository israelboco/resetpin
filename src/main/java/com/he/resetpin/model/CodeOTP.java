package com.he.resetpin.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.Getter;


@Getter
@Setter
@Entity
@Table(name="code_otp")
public class CodeOTP {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="paetenaire_id")
    private int partenaireID;

    private String code;

    private Date dateCreate;

    private Date dateExpiration;
}
