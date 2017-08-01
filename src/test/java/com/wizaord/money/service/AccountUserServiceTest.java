package com.wizaord.money.service;

import com.wizaord.money.MoneyJhipsterApp;
import com.wizaord.money.domain.CompteBancaire;
import com.wizaord.money.repository.CompteBancaireRepository;
import com.wizaord.money.service.Exception.NotAllowedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoneyJhipsterApp.class)
@Transactional
public class AccountUserServiceTest {

    private static Long userId = 1L;
    private static Long wrongUserId = 2L;

    @Autowired
    private AccountUserService accountUserService;
    @Autowired
    private CompteBancaireRepository compteBancaireRepository;


    /**
     * This test does nothing. No exception is returned
     * @throws NotAllowedException
     */
    @Test
    public void deleteAccount_NullParamTest() throws NotAllowedException {
            accountUserService.deleteAccount(userId, null);
    }

    /**
     * This test is success. If account is not found. No error is returned
     */
    @Test
    public void deleteAccount_NotExist() throws NotAllowedException {
        accountUserService.deleteAccount(userId ,1L);
    }

    /**
     * If the user is not the owner of the test, an exception is raised
     */
    @Test(expected = NotAllowedException.class)
    public void deleteAccount_NotOwnerTest() throws NotAllowedException {
        CompteBancaire cb = createCompteBancaire("23456", false, userId.intValue());
        accountUserService.deleteAccount(wrongUserId, cb.getId());
    }

    @Test
    public void deleteAccount_Test() throws NotAllowedException {
        CompteBancaire cb = createCompteBancaire("23456", false, userId.intValue());
        accountUserService.deleteAccount(userId, cb.getId());

        //get the cb
        cb = compteBancaireRepository.getOne(cb.getId());
        assertThat(cb).isNotNull();
        assertThat(cb.isIsDeleted()).isTrue();
    }

    private CompteBancaire createCompteBancaire(final String libelle, final boolean isDeleted, final Integer proprio) {
        CompteBancaire cb = new CompteBancaire()
            .libelle(libelle)
            .isClos(false)
            .proprietaire(proprio)
            .isDeleted(isDeleted)
            .montantSolde(1000F)
            .numeroCompte("123456789");
        return compteBancaireRepository.saveAndFlush(cb);
    }
}
