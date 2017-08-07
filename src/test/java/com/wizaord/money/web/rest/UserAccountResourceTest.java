package com.wizaord.money.web.rest;

import com.wizaord.money.MoneyJhipsterApp;
import com.wizaord.money.domain.CompteBancaire;
import com.wizaord.money.domain.User;
import com.wizaord.money.repository.CompteBancaireRepository;
import com.wizaord.money.repository.UserRepository;
import com.wizaord.money.service.AccountUserService;
import com.wizaord.money.web.rest.util.UserTool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.wizaord.money.web.rest.util.CompteBancaireTool.*;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoneyJhipsterApp.class)
public class UserAccountResourceTest {

    @Autowired
    private CompteBancaireRepository compteBancaireRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private AccountUserService accountUserService;


    private MockMvc restUserMockMvc;

    private User user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        // Initialize the user in the database
        user = UserTool.createUser();
        //mock the user repository
        when(userRepository.findOneByLogin(anyString())).thenReturn(Optional.of(user));

        //create useraccontRessource MVC Mock
        UserAccountResource userAccountResource =  new UserAccountResource(accountUserService, userRepository);
        this.restUserMockMvc = MockMvcBuilders
            .standaloneSetup(userAccountResource)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }


    @Test
    @Transactional
    public void getUserCompteBancaireTest() throws Exception {
        // Initialize the database
        CompteBancaire entity = createEntity(user.getId().intValue());
        entity = compteBancaireRepository.saveAndFlush(entity);

        // Get all the compteBancaireList
        restUserMockMvc.perform(get("/api/users/accounts/"))
//            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entity.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].numeroCompte").value(hasItem(DEFAULT_NUMERO_COMPTE.toString())))
            .andExpect(jsonPath("$.[*].montantSolde").value(hasItem(DEFAULT_MONTANT_SOLDE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateOuverture").value(hasItem(DEFAULT_DATE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].dateFermeture").value(hasItem(DEFAULT_DATE_FERMETURE.toString())))
            .andExpect(jsonPath("$.[*].clos").value(hasItem(DEFAULT_IS_CLOS.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].proprietaire").value(hasItem(user.getId().intValue())));
    }

    @Test
    @Transactional
    public void getUserCompteBancairesTest() throws Exception {
        // Initialize the database
        CompteBancaire entity = createEntity("libelle1", user.getId().intValue());
        entity = compteBancaireRepository.saveAndFlush(entity);
        CompteBancaire entity2 = createEntity("libelle2", user.getId().intValue());
        entity2 = compteBancaireRepository.saveAndFlush(entity2);

        // Get all the compteBancaireList
        restUserMockMvc.perform(get("/api/users/accounts/"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*", hasSize(2)));
    }
}
