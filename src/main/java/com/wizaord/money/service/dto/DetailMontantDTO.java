package com.wizaord.money.service.dto;

import com.wizaord.money.domain.DetailMontant;

public class DetailMontantDTO {

    private long id;
    private String categorieName;
    private long categorieId;
    private double montant;
    private boolean isVirementInterne;

    /**
     * Default constructor
     * @param id
     * @param categorieName
     * @param categorieId
     * @param montant
     * @param isVirementInterne
     */
    public DetailMontantDTO(long id, String categorieName, long categorieId, double montant, boolean isVirementInterne) {
        this.id = id;
        this.categorieName = categorieName;
        this.categorieId = categorieId;
        this.montant = montant;
        this.isVirementInterne = isVirementInterne;
    }

    /**
     * Constructor based on a DetailMontant
     * @param detailMontant
     */
    public DetailMontantDTO(DetailMontant detailMontant) {
        this.id = detailMontant.getId();
        this.categorieName = detailMontant.getCategorie().getLibelle();
        this.categorieId = detailMontant.getCategorie().getId();
        this.montant = detailMontant.getMontant();
        this.isVirementInterne = (detailMontant.getVirementInterneCompteId() != null) ? true : false;
    }
}
