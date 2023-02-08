package com.he.resetpin.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(name="partenaire_id")
    private int partenaireID;

    private String code;

    private Date dateCreate;

    private Date dateExpiration;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Partenaire partenaire;

    public CodeOTP() {};

}
