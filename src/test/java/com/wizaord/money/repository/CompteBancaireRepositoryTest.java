package com.wizaord.money.repository;

import com.wizaord.money.MoneyJhipsterApp;
import com.wizaord.money.domain.CompteBancaire;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoneyJhipsterApp.class)
@Transactional
public class CompteBancaireRepositoryTest {

    @Autowired
    private CompteBancaireRepository compteBancaireRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    @Transactional
    public void getAllByProprietaire() throws Exception {

        compteBancaireRepository.saveAndFlush(createCompteBancaire("first", false, 1L));
        compteBancaireRepository.saveAndFlush(createCompteBancaire("second", false, 1L));
        compteBancaireRepository.saveAndFlush(createCompteBancaire("third", false, 1L));
        compteBancaireRepository.saveAndFlush(createCompteBancaire("four", true, 1L));
        compteBancaireRepository.saveAndFlush(createCompteBancaire("five", true, 2L));

        List<CompteBancaire> allByProprietaire = compteBancaireRepository.getAllByProprietaire(1L);
        Assertions.assertThat(allByProprietaire).isNotNull().isNotEmpty().hasSize(4);
    }


    private CompteBancaire createCompteBancaire(final String libelle, final boolean isClos, final Long proprio) {
        CompteBancaire cb = new CompteBancaire()
            .libelle(libelle)
            .isClos(isClos)
            .proprietaire(proprio)
            .isDeleted(false)
            .montantSolde(1000F)
            .numeroCompte("123456789");
        return cb;
    }

}
