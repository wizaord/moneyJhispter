package com.wizaord.money.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DebitCredit.
 */
@Entity
@Table(name = "debitcredit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DebitCredit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "dateEnregistrement")
    private Instant dateEnregistrement;

    @Column(name = "isPointe")
    private Boolean isPointe;

    @Column(name = "datePointage")
    private Instant datePointage;

    @Column(name = "montantTotal")
    private Float montantTotal;

    @Column(name = "libelleBanque")
    private String libelleBanque;

    @OneToMany(mappedBy = "debitCreditAssocie", cascade = CascadeType.PERSIST)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetailMontant> details = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "compteRattache", referencedColumnName = "id", nullable = false)
    private CompteBancaire comptebancaireByCompteRattache;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public DebitCredit libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Instant getDateEnregistrement() {
        return dateEnregistrement;
    }

    public DebitCredit dateEnregistrement(Instant dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
        return this;
    }

    public void setDateEnregistrement(Instant dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    public Boolean isIsPointe() {
        return isPointe;
    }

    public DebitCredit isPointe(Boolean isPointe) {
        this.isPointe = isPointe;
        return this;
    }

    public void setIsPointe(Boolean isPointe) {
        this.isPointe = isPointe;
    }

    public Instant getDatePointage() {
        return datePointage;
    }

    public DebitCredit datePointage(Instant datePointage) {
        this.datePointage = datePointage;
        return this;
    }

    public void setDatePointage(Instant datePointage) {
        this.datePointage = datePointage;
    }

    public Float getMontantTotal() {
        return montantTotal;
    }

    public DebitCredit montantTotal(Float montantTotal) {
        this.montantTotal = montantTotal;
        return this;
    }

    public void setMontantTotal(Float montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getLibelleBanque() {
        return libelleBanque;
    }

    public DebitCredit libelleBanque(String libelleBanque) {
        this.libelleBanque = libelleBanque;
        return this;
    }

    public void setLibelleBanque(String libelleBanque) {
        this.libelleBanque = libelleBanque;
    }

    public Set<DetailMontant> getDetails() {
        return details;
    }

    public DebitCredit details(Set<DetailMontant> detailMontants) {
        this.details = detailMontants;
        return this;
    }

    public DebitCredit addDetails(DetailMontant detailMontant) {
        this.details.add(detailMontant);
        detailMontant.setDebitCreditAssocie(this);
        return this;
    }

    public DebitCredit removeDetails(DetailMontant detailMontant) {
        this.details.remove(detailMontant);
        detailMontant.setDebitCreditAssocie(null);
        return this;
    }

    public void setDetails(Set<DetailMontant> detailMontants) {
        this.details = detailMontants;
    }

    public CompteBancaire getCompterattache() {
        return comptebancaireByCompteRattache;
    }

    public DebitCredit compterattache(CompteBancaire compteBancaire) {
        this.comptebancaireByCompteRattache = compteBancaire;
        return this;
    }

    public void setCompteRattache(CompteBancaire compteBancaire) {
        this.comptebancaireByCompteRattache = compteBancaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DebitCredit debitCredit = (DebitCredit) o;
        if (debitCredit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), debitCredit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DebitCredit{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", dateEnregistrement='" + getDateEnregistrement() + "'" +
            ", isPointe='" + isIsPointe() + "'" +
            ", datePointage='" + getDatePointage() + "'" +
            ", montantTotal='" + getMontantTotal() + "'" +
            ", libelleBanque='" + getLibelleBanque() + "'" +
            "}";
    }
}
