package com.wizaord.money.web.rest.util;

import com.wizaord.money.domain.CompteBancaire;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class CompteBancaireTool {


    public static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    public static final String DEFAULT_NUMERO_COMPTE = "AAAAAAAAAA";
    public static final Float DEFAULT_MONTANT_SOLDE = 1F;
    public static final Instant DEFAULT_DATE_OUVERTURE = Instant.now().truncatedTo(ChronoUnit.HOURS);
    public static final Instant DEFAULT_DATE_FERMETURE = Instant.now().truncatedTo(ChronoUnit.HOURS);
    public static final Boolean DEFAULT_IS_CLOS = false;
    public static final Boolean DEFAULT_IS_DELETED = false;



    /**
     * Create an entity for this test.
     *
     */
    public static CompteBancaire createEntity(final int userId) {
        CompteBancaire compteBancaire = new CompteBancaire()
            .libelle(DEFAULT_LIBELLE)
            .numeroCompte(DEFAULT_NUMERO_COMPTE)
            .montantSolde(DEFAULT_MONTANT_SOLDE)
            .dateOuverture(DEFAULT_DATE_OUVERTURE)
            .dateFermeture(DEFAULT_DATE_FERMETURE)
            .isClos(DEFAULT_IS_CLOS)
            .isDeleted(DEFAULT_IS_DELETED)
            .proprietaire(userId);
        return compteBancaire;
    }

    /**
     * Create an entity with a specific libelle
     * @param libelle
     * @param userId
     * @return
     */
    public static CompteBancaire createEntity(final String libelle, final int userId) {
        CompteBancaire cb = createEntity(userId);
        cb.setLibelle(libelle);
        return cb;
    }
}
