package com.wizaord.money.service.dto;

import com.wizaord.money.domain.CompteBancaire;

import java.time.Instant;

/**
 * A DTO representing an account with details
 */
public class AccountDetailDTO {

    private Long id;
    private String libelle;
    private String numeroCompte;
    private Float montantSolde;
    private Instant dateOuverture;
    private Instant dateFermeture;
    private Boolean isClos;
    private Boolean isDeleted;
    private Integer proprietaire;
    private Instant lastTransaction;


    public AccountDetailDTO() {
        // Empty constructor needed for Jackson.
    }

    public AccountDetailDTO(Long id, String libelle, String numeroCompte, Float montantSolde, Instant dateOuverture, Instant dateFermeture, Boolean isClos, Boolean isDeleted, Integer proprietaire, Instant lastTransaction) {
        this.id = id;
        this.libelle = libelle;
        this.numeroCompte = numeroCompte;
        this.montantSolde = montantSolde;
        this.dateOuverture = dateOuverture;
        this.dateFermeture = dateFermeture;
        this.isClos = isClos;
        this.isDeleted = isDeleted;
        this.proprietaire = proprietaire;
        this.lastTransaction = lastTransaction;
    }

    public AccountDetailDTO(CompteBancaire cb) {
        this(cb.getId(), cb.getLibelle(), cb.getNumeroCompte(), cb.getMontantSolde(), cb.getDateOuverture(), cb.getDateFermeture(), cb.isIsClos(), cb.isIsDeleted(), cb.getProprietaire(), null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public Float getMontantSolde() {
        return montantSolde;
    }

    public void setMontantSolde(Float montantSolde) {
        this.montantSolde = montantSolde;
    }

    public Instant getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(Instant dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public Instant getDateFermeture() {
        return dateFermeture;
    }

    public void setDateFermeture(Instant dateFermeture) {
        this.dateFermeture = dateFermeture;
    }

    public Boolean getClos() {
        return isClos;
    }

    public void setClos(Boolean clos) {
        isClos = clos;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Integer proprietaire) {
        this.proprietaire = proprietaire;
    }

    public Instant getLastTransaction() {
        return lastTransaction;
    }

    public void setLastTransaction(Instant lastTransaction) {
        this.lastTransaction = lastTransaction;
    }
}
