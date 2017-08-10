package com.wizaord.money.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DetailMontant.
 */
@Entity
@Table(name = "detailmontant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DetailMontant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "virementInterneCompteId")
    private Integer virementInterneCompteId;

    @ManyToOne
    @JoinColumn(name = "categorie", referencedColumnName = "id")
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "debitCreditAssocie", referencedColumnName = "id", nullable = false)
    private DebitCredit debitCreditAssocie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public DetailMontant montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Integer getVirementInterneCompteId() {
        return virementInterneCompteId;
    }

    public DetailMontant virementInterneCompteId(Integer virementInterneCompteId) {
        this.virementInterneCompteId = virementInterneCompteId;
        return this;
    }

    public void setVirementInterneCompteId(Integer virementInterneCompteId) {
        this.virementInterneCompteId = virementInterneCompteId;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public DetailMontant categorie(Categorie categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public DebitCredit getDebitCreditAssocie() {
        return debitCreditAssocie;
    }

    public DetailMontant debitCreditAssocie(DebitCredit debitCredit) {
        this.debitCreditAssocie = debitCredit;
        return this;
    }

    public void setDebitCreditAssocie(DebitCredit debitCredit) {
        this.debitCreditAssocie = debitCredit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DetailMontant detailMontant = (DetailMontant) o;
        if (detailMontant.getId() == null || getId() == null) {
            return false;
        }
        return (getId() == detailMontant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetailMontant{" +
            "id=" + getId() +
            ", montant='" + getMontant() + "'" +
            ", virementInterneCompteId='" + getVirementInterneCompteId() + "'" +
            "}";
    }
}
