package com.wizaord.money.service.dto;

import com.wizaord.money.domain.DetailMontant;

public class DetailMontantDTO {

    private long id;
    private String categorieName;
    private long categorieId;
    private double montant;
    private boolean isVirementInterne;

    public DetailMontantDTO() {
        super();
    }

    public DetailMontantDTO(long id, String categorieName, long categorieId, double montant, boolean isVirementInterne) {
        this.id = id;
        this.categorieName = categorieName;
        this.categorieId = categorieId;
        this.montant = montant;
        this.isVirementInterne = isVirementInterne;
    }

    public DetailMontantDTO(DetailMontant detailMontant) {
        this.id = detailMontant.getId();
        if (detailMontant.getCategorie() != null) {
            this.categorieName = detailMontant.getCategorie().getLibelle();
            this.categorieId = detailMontant.getCategorie().getId();
        }
        this.montant = detailMontant.getMontant();
        this.isVirementInterne = (detailMontant.getVirementInterneCompteId() != null) ? true : false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

}
