package com.he.resetpin.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    private String code;

    private Date dateCreate;

    private Date dateExpiration;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partenaire_id")
    private Partenaire partenaire;

    public CodeOTP() {};

}
