package com.wizaord.money.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wizaord.money.domain.Categorie;
import com.wizaord.money.domain.DetailMontant;

public class DetailMontantDTO {

    private Long id;
    private String categorieName;
    private long categorieId;
    private double montant;
    private boolean isVirementInterne;
    private Integer virementInterneCompteId;

    public DetailMontantDTO() {
        super();
    }

    public DetailMontantDTO(long id, String categorieName, long categorieId, double montant, boolean isVirementInterne, Integer virementInterneCompteId) {
        this.id = id;
        this.categorieName = categorieName;
        this.categorieId = categorieId;
        this.montant = montant;
        this.isVirementInterne = isVirementInterne;
        this.virementInterneCompteId = virementInterneCompteId;
    }

    public DetailMontantDTO(DetailMontant detailMontant) {
        this.id = detailMontant.getId();
        if (detailMontant.getCategorie() != null) {
            this.categorieName = detailMontant.getCategorie().getLibelle();
            this.categorieId = detailMontant.getCategorie().getId();
        }
        this.montant = detailMontant.getMontant();
        this.isVirementInterne = (detailMontant.getVirementInterneCompteId() != null) ? true : false;
        this.virementInterneCompteId = detailMontant.getVirementInterneCompteId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategorieName() {
        return categorieName;
    }

    public void setCategorieName(String categorieName) {
        this.categorieName = categorieName;
    }

    public long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(long categorieId) {
        this.categorieId = categorieId;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public boolean isVirementInterne() {
        return isVirementInterne;
    }

    public void setVirementInterne(boolean virementInterne) {
        isVirementInterne = virementInterne;
    }

    public Integer getVirementInterneCompteId() {
        return virementInterneCompteId;
    }

    public void setVirementInterneCompteId(Integer virementInterneCompteId) {
        this.virementInterneCompteId = virementInterneCompteId;
    }

    /**
     * Convert a DetailMontantDTO in DetailMontant
     * @return
     */
    @JsonIgnore
    public DetailMontant getDetailMontant() {
        DetailMontant dm = new DetailMontant();
        dm.setId(this.id);
        dm.setMontant(this.montant);
        dm.setVirementInterneCompteId(this.virementInterneCompteId);
        Categorie c = new Categorie();
        c.setId(this.categorieId);
        dm.setCategorie(c);

        return dm;
    }
}
