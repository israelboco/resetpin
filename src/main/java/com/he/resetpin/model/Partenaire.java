package com.he.resetpin.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="partenaire")
public class Partenaire {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String nom;
    
    private String prenoms;
    
    private String pin;
    
    @Column(name="email")
    private String email;
    
    private String telephone;
    
    private Boolean reinitialisable;
    
    private Date dateReinitialisation;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partenaire")
    private List<CodeOTP> codeOTPs;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partenaire")
    private List<Validation> validations;
    
}
