package com.wizaord.money.web.rest;

import com.wizaord.money.MoneyJhipsterApp;
import com.wizaord.money.domain.Categorie;
import com.wizaord.money.domain.CompteBancaire;
import com.wizaord.money.domain.DebitCredit;
import com.wizaord.money.domain.User;
import com.wizaord.money.repository.CategorieRepository;
import com.wizaord.money.repository.CompteBancaireRepository;
import com.wizaord.money.repository.DebitCreditRepository;
import com.wizaord.money.repository.UserRepository;
import com.wizaord.money.service.AccountUserService;
import com.wizaord.money.service.DebitCreditUserService;
import com.wizaord.money.service.dto.DebitCreditDTO;
import com.wizaord.money.service.dto.DebitCreditSearch;
import com.wizaord.money.service.dto.DetailMontantDTO;
import com.wizaord.money.web.rest.util.CategorieTool;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private CategorieRepository categorieRepository;
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
        debitCreditSearch.addCompteId(cb.getId());

        restUserDebitCredit.perform(get("/api/users/debitcredit/")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCreditSearch)))
//            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(100)));
//            .andExpect(jsonPath("$.[?(@.id == 1)].libellePerso").value(hasItem(DEFAULT_LIBELLE.toString())));
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

    @Test
    public void getDebitsCreditsNotConnected() throws Exception {
        when(userRepository.findOneByLogin(anyString())).thenReturn(Optional.empty());

        DebitCreditSearch debitCreditSearch = new DebitCreditSearch();
        debitCreditSearch.setLibelleMatch("plop");

        restUserDebitCredit.perform(get("/api/users/debitcredit/")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCreditSearch)))
            .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void getDebitsCreditsCriteriaOnName() throws Exception {
        //create account
        CompteBancaire cb = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(user.getId().intValue()));
        //create 100 debit credit
        for (int i = 0; i < 100; i++) {
            debitCreditRepository.saveAndFlush(DebitCreditTool.createDebitCreditWithLibelle(cb, "PLOP" + i));
        }


        DebitCreditSearch debitCreditSearch = new DebitCreditSearch();
        debitCreditSearch.addCompteId(cb.getId());
        debitCreditSearch.setLibelleMatch("P1");

        restUserDebitCredit.perform(get("/api/users/debitcredit/")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCreditSearch)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(11)));
    }

    @Test
    public void getDebitsCreditsCriteriaOnCategorie() throws Exception {
        //create account
        CompteBancaire cb = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(user.getId().intValue()));

        //create 3 categories
        final Categorie[] catArray = new Categorie[3];
        for (int i = 0 ; i < 3; i++) {
            catArray[i] = categorieRepository.saveAndFlush(CategorieTool.createCategorieWithName("CAT" + i));
        }

        //create 100 debit credit
        for (int i = 0; i < 100; i++) {
            //create with detail Montant
            final DebitCredit debitCredit = DebitCreditTool.createDebitCredit(cb);
            debitCredit.addDetails(DebitCreditTool.createDetailMontantWithCategorie(catArray[i % 3]));

            debitCreditRepository.saveAndFlush(debitCredit);
        }

        DebitCreditSearch debitCreditSearch = new DebitCreditSearch();
        debitCreditSearch.addCompteId(cb.getId());
        debitCreditSearch.setCategorieName(catArray[0].getLibelle());

        restUserDebitCredit.perform(get("/api/users/debitcredit/")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCreditSearch)))
//            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(34)));
    }

    @Test
    public void deleteDebitCredit() throws Exception {
        final CompteBancaire cb = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(user.getId().intValue()));
        final DebitCredit debitCredit = debitCreditRepository.saveAndFlush(DebitCreditTool.createDebitCredit(cb));

        restUserDebitCredit.perform(delete("/api/users/debitcredit/" + debitCredit.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        final DebitCredit debitCreditFromDatabase = debitCreditRepository.findOne(debitCredit.getId());
        assertThat(debitCreditFromDatabase).isNull();
    }

    @Test
    public void deleteDebitCreditNotExist() throws Exception {
        restUserDebitCredit.perform(delete("/api/users/debitcredit/1234567")
            .contentType(TestUtil.APPLICATION_JSON_UTF8))
//            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
    }

    @Test
    public void deleteDebitCreditNotAllow() throws Exception {
        final CompteBancaire cb = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(2));
        final DebitCredit debitCredit = debitCreditRepository.saveAndFlush(DebitCreditTool.createDebitCredit(cb));


        restUserDebitCredit.perform(delete("/api/users/debitcredit/" + debitCredit.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        final DebitCredit debitCreditFromDatabase = debitCreditRepository.getOne(debitCredit.getId());
        assertThat(debitCreditFromDatabase).isNotNull();
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

    @Test
    public void createDebitCredit() throws Exception {
        //create account
        CompteBancaire cb = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(user.getId().intValue()));

        final DebitCreditDTO debitCreditDTO = DebitCreditTool.createDebitCreditDTO(cb);
        debitCreditDTO.setCompteId(cb.getId());

        restUserDebitCredit.perform(post("/api/users/debitcredit/")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCreditDTO)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.dateTransaction").isNotEmpty())
            .andExpect(jsonPath("$.libellePerso").value(DebitCreditTool.DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.libelleBanque").value(DebitCreditTool.DEFAULT_LIBELLE_BANQUE))
            .andExpect(jsonPath("$.compteId").value(cb.getId()))
            .andExpect(jsonPath("$.montantTotal").value(DebitCreditTool.DEFAULT_MONTANT_TOTAL))
            .andExpect(jsonPath("$.pointe").value(false))
            .andExpect(jsonPath("$.datePointage").isEmpty());
    }

    @Test
    public void createDebitCreditWithDetailMontant() throws Exception {
        //create account
        CompteBancaire cb = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(user.getId().intValue()));
        final Categorie categorie = categorieRepository.saveAndFlush(CategorieTool.createCategorieWithName("CATCREATE"));

        final DetailMontantDTO detailMontantDTOWithoutCategorie = DebitCreditTool.createDetailMontantDTOWithoutCategorie();
        detailMontantDTOWithoutCategorie.setCategorieId(categorie.getId());

        final DebitCreditDTO debitCreditDTO = DebitCreditTool.createDebitCreditDTO(cb);
        debitCreditDTO.setCompteId(cb.getId());
        debitCreditDTO.addDetailMontant(detailMontantDTOWithoutCategorie);

        restUserDebitCredit.perform(post("/api/users/debitcredit/")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCreditDTO)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.dateTransaction").isNotEmpty())
            .andExpect(jsonPath("$.libellePerso").value(DebitCreditTool.DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.libelleBanque").value(DebitCreditTool.DEFAULT_LIBELLE_BANQUE))
            .andExpect(jsonPath("$.compteId").value(cb.getId()))
            .andExpect(jsonPath("$.montantTotal").value(DebitCreditTool.DEFAULT_MONTANT_TOTAL))
            .andExpect(jsonPath("$.pointe").value(false))
            .andExpect(jsonPath("$.datePointage").isEmpty());
    }

    @Test
    public void createDebitCreditNotAllowed() throws Exception {
        //create account
        CompteBancaire cb = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(user.getId().intValue()));

        final DebitCreditDTO debitCreditDTO = DebitCreditTool.createDebitCreditDTO(cb);
        debitCreditDTO.setCompteId(100L);

        restUserDebitCredit.perform(post("/api/users/debitcredit/")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCreditDTO)))
//            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isForbidden());
    }

    @Test
    public void createDebitCreditMissingMandatoryParam() throws Exception {
        //create account
        CompteBancaire cb = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(user.getId().intValue()));

        final DebitCreditDTO debitCreditDTO = DebitCreditTool.createDebitCreditDTO(cb);
        debitCreditDTO.setLibelleBanque(null);
        debitCreditDTO.setCompteId(cb.getId());

        restUserDebitCredit.perform(post("/api/users/debitcredit/")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCreditDTO)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isExpectationFailed());
    }


    @Test
    public void createDebitCreditCategoryDoesNotExist() throws Exception {
        //create account
        CompteBancaire cb = compteBancaireRepository.saveAndFlush(CompteBancaireTool.createCompteBancaire(user.getId().intValue()));

        final DetailMontantDTO detailMontantDTOWithoutCategorie = DebitCreditTool.createDetailMontantDTOWithoutCategorie();
        detailMontantDTOWithoutCategorie.setCategorieId(123456);

        final DebitCreditDTO debitCreditDTO = DebitCreditTool.createDebitCreditDTO(cb);
        debitCreditDTO.setCompteId(cb.getId());
        debitCreditDTO.addDetailMontant(detailMontantDTOWithoutCategorie);

        restUserDebitCredit.perform(post("/api/users/debitcredit/")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCreditDTO)))
//            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isExpectationFailed());
    }

}
