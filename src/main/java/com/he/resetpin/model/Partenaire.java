package com.he.resetpin.model;

import java.util.Date;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;



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
    
    private String salt;
    
    @Column(name="email")
    private String email;
    
    private String telephone;
    
    private Boolean reinitialisable;
    
    private Date dateReinitialisation = new Date();
    
}
