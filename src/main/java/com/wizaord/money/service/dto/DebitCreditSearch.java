package com.wizaord.money.service.dto;

import com.wizaord.money.domain.CompteBancaire;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DebitCreditSearch {
    @NotNull
    @NotEmpty
    private List<Long> compteIds = new ArrayList<>();

    private LocalDate beginDate;
    private LocalDate endDate;
    private String libelleMatch;
    private String categorieName;

    public DebitCreditSearch() {
        super();
    }

    public List<Long> getCompteIds() {
        return compteIds;
    }

    public List<CompteBancaire> getCompteBancaires() {
        List<CompteBancaire> compteBancaires = new ArrayList<>();
        this.compteIds.forEach((elt) -> {
            CompteBancaire cb = new CompteBancaire();
            cb.setId(elt.longValue());
            compteBancaires.add(cb);
        });
        return compteBancaires;
    }

    public void setCompteIds(List<Long> compteIds) {
        this.compteIds = compteIds;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getLibelleMatch() {
        return libelleMatch;
    }

    public void setLibelleMatch(String libelleMatch) {
        this.libelleMatch = libelleMatch;
    }

    public String getCategorieName() {
        return categorieName;
    }

    public void setCategorieName(String categorieName) {
        this.categorieName = categorieName;
    }

    public DebitCreditSearch addCompteId(long id) {
        this.compteIds.add(id);
        return this;
    }

}
