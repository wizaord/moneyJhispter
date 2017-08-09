package com.wizaord.money.service.dto;

import com.wizaord.money.domain.DebitCredit;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DebitCreditDTO {

    private long id;
    private Instant dateTransaction;
    private Instant datePointage;
    private boolean isPointe;
    private String libellePerso;
    private String libelleBanque;
    private long compteId;
    private float montantTotal;


    private List<DetailMontantDTO> detailMontantDTOS = new ArrayList<>();

    public DebitCreditDTO() {
        super();
    }

    public DebitCreditDTO(DebitCredit debitCredit) {
        super();
        this.id = debitCredit.getId();
        this.dateTransaction = debitCredit.getDateEnregistrement();
        this.datePointage = debitCredit.getDatePointage();
        this.isPointe = debitCredit.isIsPointe();
        this.libellePerso = debitCredit.getLibelle();
        this.libelleBanque = debitCredit.getLibelleBanque();
        this.compteId = debitCredit.getCompterattache().getId();
        this.montantTotal = debitCredit.getMontantTotal();

        debitCredit.getDetails().forEach((elt) -> {
            DetailMontantDTO detailMontantDTO = new DetailMontantDTO(elt);
            this.addDetailMontant(detailMontantDTO);
        });

    }

    public void addDetailMontant(DetailMontantDTO detail) {
        this.detailMontantDTOS.add(detail);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Instant dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public Instant getDatePointage() {
        return datePointage;
    }

    public void setDatePointage(Instant datePointage) {
        this.datePointage = datePointage;
    }

    public boolean isPointe() {
        return isPointe;
    }

    public void setPointe(boolean pointe) {
        isPointe = pointe;
    }

    public String getLibellePerso() {
        return libellePerso;
    }

    public void setLibellePerso(String libellePerso) {
        this.libellePerso = libellePerso;
    }

    public String getLibelleBanque() {
        return libelleBanque;
    }

    public void setLibelleBanque(String libelleBanque) {
        this.libelleBanque = libelleBanque;
    }

    public long getCompteId() {
        return compteId;
    }

    public void setCompteId(int compteId) {
        this.compteId = compteId;
    }

    public float getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(long montantTotal) {
        this.montantTotal = montantTotal;
    }

    public List<DetailMontantDTO> getDetailMontantDTOS() {
        return detailMontantDTOS;
    }
}
