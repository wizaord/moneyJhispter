package com.wizaord.money.web.rest.util;

import com.wizaord.money.domain.Categorie;
import com.wizaord.money.domain.CompteBancaire;
import com.wizaord.money.domain.DebitCredit;
import com.wizaord.money.domain.DetailMontant;
import com.wizaord.money.service.dto.DebitCreditDTO;
import com.wizaord.money.service.dto.DetailMontantDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DebitCreditTool {

    public static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    public static final Instant DEFAULT_DATE_ENREGISTREMENT = Instant.ofEpochMilli(0L);
    public static final Boolean DEFAULT_IS_POINTE = false;
    public static final Instant DEFAULT_DATE_POINTAGE = Instant.ofEpochMilli(0L);
    public static final Float DEFAULT_MONTANT_TOTAL = 1F;
    public static final String DEFAULT_LIBELLE_BANQUE = "BANQUELIBELLE";

    public static final Double DEFAULT_MONTANT = 1D;


    /**
     * Create an entity for this test.
     */
    public static DebitCredit createDebitCredit(final CompteBancaire compteBancaire) {
        DebitCredit debitCredit = new DebitCredit()
            .libelle(DEFAULT_LIBELLE)
            .dateEnregistrement(DEFAULT_DATE_ENREGISTREMENT)
            .isPointe(DEFAULT_IS_POINTE)
            .datePointage(DEFAULT_DATE_POINTAGE)
            .montantTotal(DEFAULT_MONTANT_TOTAL)
            .libelleBanque(DEFAULT_LIBELLE_BANQUE)
            .compterattache(compteBancaire);
        return debitCredit;
    }

    /**
     * Create a DebitCredit. Can specify the month
     *
     * @param compteBancaire
     * @param month
     * @return
     */
    public static DebitCredit createDebitCreditWithMonth(final CompteBancaire compteBancaire, final int year, final int month) {
        DebitCredit debitCredit = createDebitCredit(compteBancaire);
        LocalDateTime ldt = LocalDateTime.ofInstant(debitCredit.getDateEnregistrement(), ZoneOffset.UTC);
        //change the month
        LocalDateTime newLocalDateTime = LocalDateTime.of(year, month, 10, ldt.getHour(), ldt.getMinute());
        debitCredit.setDateEnregistrement(newLocalDateTime.toInstant(ZoneOffset.UTC));
        return debitCredit;
    }


    /**
     * Create a DebitCredit. Can specify the libelle
     */
    public static DebitCredit createDebitCreditWithLibelle(final CompteBancaire compteBancaire, final String libelle) {
        DebitCredit debitCredit = createDebitCredit(compteBancaire);
        debitCredit.setLibelle(libelle);
        return debitCredit;
    }

    public static DetailMontant createDetailMontantWithNotCategorie() {
        DetailMontant detailMontant = new DetailMontant()
            .montant(DEFAULT_MONTANT)
            .virementInterneCompteId(null);
        return detailMontant;
    }

    public static DetailMontant createDetailMontantWithCategorie(final Categorie c) {
        DetailMontant detailMontant = new DetailMontant()
            .montant(DEFAULT_MONTANT)
            .categorie(c)
            .virementInterneCompteId(null);
        return detailMontant;
    }

    public static DebitCreditDTO createDebitCreditDTO(final CompteBancaire cb) {
        DebitCreditDTO debitCreditDTO = new DebitCreditDTO();
        debitCreditDTO.setDatePointage(null);
        debitCreditDTO.setDateTransaction(Instant.now());
        debitCreditDTO.setCompteId(cb.getId());
        debitCreditDTO.setLibelleBanque(DEFAULT_LIBELLE_BANQUE);
        debitCreditDTO.setLibellePerso(DEFAULT_LIBELLE);
        debitCreditDTO.setMontantTotal(DEFAULT_MONTANT_TOTAL);
        debitCreditDTO.setPointe(false);
        debitCreditDTO.setId(null);
        return debitCreditDTO;
    }

    public static DetailMontantDTO createDetailMontantDTOWithoutCategorie() {
        DetailMontantDTO dm = new DetailMontantDTO();
        dm.setId(null);
        dm.setMontant(DEFAULT_MONTANT_TOTAL);
        dm.setVirementInterne(false);
        dm.setVirementInterneCompteId(null);
        return dm;
    }


}
