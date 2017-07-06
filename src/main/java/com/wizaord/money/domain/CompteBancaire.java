package com.wizaord.money.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CompteBancaire.
 */
@Entity
@Table(name = "compte_bancaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompteBancaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "numero_compte")
    private String numeroCompte;

    @Column(name = "montant_solde")
    private Float montantSolde;

    @Column(name = "date_ouverture")
    private Instant dateOuverture;

    @Column(name = "date_fermeture")
    private Instant dateFermeture;

    @Column(name = "is_clos")
    private Boolean isClos;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "proprietaire")
    private Integer proprietaire;

    @OneToMany(mappedBy = "compteRattache")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DebitCredit> debitscredits = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public CompteBancaire libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public CompteBancaire numeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
        return this;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public Float getMontantSolde() {
        return montantSolde;
    }

    public CompteBancaire montantSolde(Float montantSolde) {
        this.montantSolde = montantSolde;
        return this;
    }

    public void setMontantSolde(Float montantSolde) {
        this.montantSolde = montantSolde;
    }

    public Instant getDateOuverture() {
        return dateOuverture;
    }

    public CompteBancaire dateOuverture(Instant dateOuverture) {
        this.dateOuverture = dateOuverture;
        return this;
    }

    public void setDateOuverture(Instant dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public Instant getDateFermeture() {
        return dateFermeture;
    }

    public CompteBancaire dateFermeture(Instant dateFermeture) {
        this.dateFermeture = dateFermeture;
        return this;
    }

    public void setDateFermeture(Instant dateFermeture) {
        this.dateFermeture = dateFermeture;
    }

    public Boolean isIsClos() {
        return isClos;
    }

    public CompteBancaire isClos(Boolean isClos) {
        this.isClos = isClos;
        return this;
    }

    public void setIsClos(Boolean isClos) {
        this.isClos = isClos;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public CompteBancaire isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getProprietaire() {
        return proprietaire;
    }

    public CompteBancaire proprietaire(Integer proprietaire) {
        this.proprietaire = proprietaire;
        return this;
    }

    public void setProprietaire(Integer proprietaire) {
        this.proprietaire = proprietaire;
    }

    public Set<DebitCredit> getDebitscredits() {
        return debitscredits;
    }

    public CompteBancaire debitscredits(Set<DebitCredit> debitCredits) {
        this.debitscredits = debitCredits;
        return this;
    }

    public CompteBancaire addDebitscredits(DebitCredit debitCredit) {
        this.debitscredits.add(debitCredit);
        debitCredit.setCompteRattache(this);
        return this;
    }

    public CompteBancaire removeDebitscredits(DebitCredit debitCredit) {
        this.debitscredits.remove(debitCredit);
        debitCredit.setCompteRattache(null);
        return this;
    }

    public void setDebitscredits(Set<DebitCredit> debitCredits) {
        this.debitscredits = debitCredits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompteBancaire compteBancaire = (CompteBancaire) o;
        if (compteBancaire.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compteBancaire.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompteBancaire{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", numeroCompte='" + getNumeroCompte() + "'" +
            ", montantSolde='" + getMontantSolde() + "'" +
            ", dateOuverture='" + getDateOuverture() + "'" +
            ", dateFermeture='" + getDateFermeture() + "'" +
            ", isClos='" + isIsClos() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            ", proprietaire='" + getProprietaire() + "'" +
            "}";
    }
}
