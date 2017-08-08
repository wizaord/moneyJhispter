package com.wizaord.money.service.dto;

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
    private int compteId;
    private long montantTotal;
    private List<DetailMontantDTO> detailMontantDTOS = new ArrayList<>();

    public DebitCreditDTO(long id, Instant dateTransaction, Instant datePointage, boolean isPointe, String libellePerso, String libelleBanque, int compteId, long montantTotal) {
        super();
        this.id = id;
        this.dateTransaction = dateTransaction;
        this.datePointage = datePointage;
        this.isPointe = isPointe;
        this.libellePerso = libellePerso;
        this.libelleBanque = libelleBanque;
        this.compteId = compteId;
        this.montantTotal = montantTotal;
    }

    public DebitCreditDTO() {
        super();
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

    public int getCompteId() {
        return compteId;
    }

    public void setCompteId(int compteId) {
        this.compteId = compteId;
    }

    public long getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(long montantTotal) {
        this.montantTotal = montantTotal;
    }

}
