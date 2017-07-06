package com.wizaord.money.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Categorie.
 */
@Entity
@Table(name = "categorie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "is_credit")
    private Boolean isCredit;

    @Column(name = "abonnement_regulier")
    private Boolean abonnementRegulier;

    @Column(name = "categorie_pere")
    private Integer categoriePere;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Categorie libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Boolean isIsCredit() {
        return isCredit;
    }

    public Categorie isCredit(Boolean isCredit) {
        this.isCredit = isCredit;
        return this;
    }

    public void setIsCredit(Boolean isCredit) {
        this.isCredit = isCredit;
    }

    public Boolean isAbonnementRegulier() {
        return abonnementRegulier;
    }

    public Categorie abonnementRegulier(Boolean abonnementRegulier) {
        this.abonnementRegulier = abonnementRegulier;
        return this;
    }

    public void setAbonnementRegulier(Boolean abonnementRegulier) {
        this.abonnementRegulier = abonnementRegulier;
    }

    public Integer getCategoriePere() {
        return categoriePere;
    }

    public Categorie categoriePere(Integer categoriePere) {
        this.categoriePere = categoriePere;
        return this;
    }

    public void setCategoriePere(Integer categoriePere) {
        this.categoriePere = categoriePere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Categorie categorie = (Categorie) o;
        if (categorie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categorie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Categorie{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", isCredit='" + isIsCredit() + "'" +
            ", abonnementRegulier='" + isAbonnementRegulier() + "'" +
            ", categoriePere='" + getCategoriePere() + "'" +
            "}";
    }
}
