package com.wizaord.money.web.rest.util;

import com.wizaord.money.domain.CompteBancaire;
import com.wizaord.money.domain.DebitCredit;

import java.time.Instant;

public class DebitCreditTool {

    public static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    public static final Instant DEFAULT_DATE_ENREGISTREMENT = Instant.ofEpochMilli(0L);
    public static final Boolean DEFAULT_IS_POINTE = false;
    public static final Instant DEFAULT_DATE_POINTAGE = Instant.ofEpochMilli(0L);
    public static final Float DEFAULT_MONTANT_TOTAL = 1F;
    public static final String DEFAULT_LIBELLE_BANQUE = "AAAAAAAAAA";


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
}
