package com.wizaord.money.service.dto;

import com.wizaord.money.domain.CompteBancaire;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DebitCreditSearch {
    @NotNull
    @NotEmpty
    private List<Long> compteIds = new ArrayList<>();

    private Instant beginMonth;
    private Instant endMonth;
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

    public Instant getBeginMonth() {
        return beginMonth;
    }

    public void setBeginMonth(Instant beginMonth) {
        this.beginMonth = beginMonth;
    }

    public Instant getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Instant endMonth) {
        this.endMonth = endMonth;
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
