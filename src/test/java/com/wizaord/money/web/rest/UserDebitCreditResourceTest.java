package com.wizaord.money.web.rest;

import com.wizaord.money.MoneyJhipsterApp;
import com.wizaord.money.domain.CompteBancaire;
import com.wizaord.money.domain.User;
import com.wizaord.money.repository.CompteBancaireRepository;
import com.wizaord.money.repository.DebitCreditRepository;
import com.wizaord.money.repository.UserRepository;
import com.wizaord.money.service.AccountUserService;
import com.wizaord.money.service.DebitCreditUserService;
import com.wizaord.money.service.dto.DebitCreditSearch;
import com.wizaord.money.web.rest.util.CompteBancaireTool;
import com.wizaord.money.web.rest.util.DebitCreditTool;
import com.wizaord.money.web.rest.util.UserTool;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static com.wizaord.money.web.rest.util.DebitCreditTool.DEFAULT_LIBELLE;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoneyJhipsterApp.class)
public class UserDebitCreditResourceTest {

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    @Autowired
    private CompteBancaireRepository compteBancaireRepository;
    @Autowired
    private DebitCreditRepository debitCreditRepository;
    @Autowired
    private AccountUserService accountUserService;
    @Autowired
    private DebitCreditUserService debitCreditUserService;

    private MockMvc restUserDebitCredit;
    private User user;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Initialize the user in the database
        user = UserTool.createUser();
        //mock the user repository
        when(userRepository.findOneByLogin(anyString())).thenReturn(Optional.of(user));

        //create useraccontRessource MVC Mock
        UserDebitCreditResource userDebitCreditResource = new UserDebitCreditResource(userRepository, accountUserService, debitCreditUserService);
        this.restUserDebitCredit = MockMvcBuilders
            .standaloneSetup(userDebitCreditResource)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @Test
    @Transactional
    public void getDebitsCreditsAll() throws Exception {
        //create account
        CompteBancaire cb = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(user.getId().intValue()));
        //create 100 debit credit
        for (int i = 0; i < 100; i++) {
            debitCreditRepository.saveAndFlush(DebitCreditTool.createDebitCredit(cb));
        }

        //set account 1 as criteria
        DebitCreditSearch debitCreditSearch = new DebitCreditSearch();
        debitCreditSearch.addCompteId(1);

        restUserDebitCredit.perform(get("/api/users/debitcredit/")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCreditSearch)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(100)))
            .andExpect(jsonPath("$.[?(@.id == 1)].libellePerso").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    public void getDebitsCreditsByMonthNoValue() throws Exception {
        //create account
        CompteBancaire cb = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(user.getId().intValue()));
        //create 100 debit credit
        for (int i = 0; i < 100; i++) {
            debitCreditRepository.saveAndFlush(DebitCreditTool.createDebitCreditWithMonth(cb, 2016, 1));
        }

        DebitCreditSearch debitCreditSearch = new DebitCreditSearch();
        debitCreditSearch.addCompteId(cb.getId());
        debitCreditSearch.setBeginDate(LocalDate.of(2016, 5, 1));
        debitCreditSearch.setEndDate(LocalDate.of(2016, 6, 1));

        restUserDebitCredit.perform(get("/api/users/debitcredit/")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCreditSearch)))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
    }

    @Test
    public void getDebitsCreditsByMonth() throws Exception {
        //create account
        CompteBancaire cb = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(user.getId().intValue()));
        //create 100 debit credit
        for (int i = 0; i < 100; i++) {
            debitCreditRepository.saveAndFlush(DebitCreditTool.createDebitCreditWithMonth(cb, 2016, (i % 2) + 1));
        }

        DebitCreditSearch debitCreditSearch = new DebitCreditSearch();
        debitCreditSearch.addCompteId(cb.getId());
        debitCreditSearch.setBeginDate(LocalDate.of(2016, 2, 1));
        debitCreditSearch.setEndDate(LocalDate.of(2016, 3, 1));

        restUserDebitCredit.perform(get("/api/users/debitcredit/")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCreditSearch)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(50)));
    }

    @Test
    public void getDebitsCreditsTwoAccount() throws Exception {
        //create account
        CompteBancaire cb = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(user.getId().intValue()));
        CompteBancaire cb2 = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(user.getId().intValue()));
        //create 100 debit credit
        for (int i = 0; i < 50; i++) {
            debitCreditRepository.saveAndFlush(DebitCreditTool.createDebitCreditWithMonth(cb, 2016, 1));
            debitCreditRepository.saveAndFlush(DebitCreditTool.createDebitCreditWithMonth(cb2, 2016, 1));
        }

        DebitCreditSearch debitCreditSearch = new DebitCreditSearch();
        debitCreditSearch.addCompteId(cb.getId())
            .addCompteId(cb2.getId());

        restUserDebitCredit.perform(get("/api/users/debitcredit/")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCreditSearch)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(100)));
    }


    @Test
    public void getDebitsCreditsNoAccount() throws Exception {
        DebitCreditSearch debitCreditSearch = new DebitCreditSearch();
        debitCreditSearch.setLibelleMatch("plop");

        restUserDebitCredit.perform(get("/api/users/debitcredit/")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCreditSearch)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getDebitsCreditsTwoAccount_notAllowed() throws Exception {
        //create two account (first to the user and the second to another person)
        CompteBancaire cb = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(user.getId().intValue()));
        CompteBancaire cb2 = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(2));

        DebitCreditSearch debitCreditSearch = new DebitCreditSearch();
        debitCreditSearch.addCompteId(cb.getId())
            .addCompteId(cb2.getId());

        restUserDebitCredit.perform(get("/api/users/debitcredit/")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCreditSearch)))
            .andExpect(status().isForbidden());
    }

    @Ignore
    @Test
    public void getDebitsCreditsNotConnected() throws Exception {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void getDebitsCreditsCriteriaOnName() throws Exception {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void getDebitsCreditsCriteriaOnCategorie() throws Exception {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void deleteDebitCredit() throws Exception {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void deleteDebitCreditNotExist() throws Exception {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void deleteDebitCreditNotAllow() throws Exception {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void updateDebitCredit() throws Exception {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void updateDebitCreditNotExist() throws Exception {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void updateDebitCreditNotAllowed() throws Exception {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void createDebitCredit() throws Exception {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void createDebitCreditNotConnected() throws Exception {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void createDebitCreditNotAccountDestinationOwner() throws Exception {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void createDebitCreditMissingMandatoryParam() throws Exception {
        fail("Not yet implemented");
    }

}
