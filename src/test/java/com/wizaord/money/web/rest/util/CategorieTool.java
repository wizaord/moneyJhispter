package com.wizaord.money.web.rest.util;

import com.wizaord.money.domain.Categorie;

public class CategorieTool {

    public static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    public static final Boolean DEFAULT_IS_CREDIT = false;
    public static final Boolean DEFAULT_ABONNEMENT_REGULIER = false;
    public static final Integer DEFAULT_CATEGORIE_PERE = 1;

    public static Categorie createCategorie() {
        Categorie categorie = new Categorie()
            .libelle(DEFAULT_LIBELLE)
            .isCredit(DEFAULT_IS_CREDIT)
            .abonnementRegulier(DEFAULT_ABONNEMENT_REGULIER)
            .categoriePere(DEFAULT_CATEGORIE_PERE);
        return categorie;
    }

    public static Categorie createCategorieWithName(final String categorieName) {
        final Categorie categorie = createCategorie();
        categorie.setLibelle(categorieName);
        return categorie;
    }
}
