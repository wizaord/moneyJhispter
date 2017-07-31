package com.wizaord.money.web.rest;

import com.wizaord.money.MoneyJhipsterApp;
import com.wizaord.money.domain.DebitCredit;
import com.wizaord.money.repository.DebitCreditRepository;
import com.wizaord.money.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DebitCreditResource REST controller.
 *
 * @see DebitCreditResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoneyJhipsterApp.class)
public class DebitCreditResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_ENREGISTREMENT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ENREGISTREMENT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_POINTE = false;
    private static final Boolean UPDATED_IS_POINTE = true;

    private static final Instant DEFAULT_DATE_POINTAGE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_POINTAGE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_MONTANT_TOTAL = 1F;
    private static final Float UPDATED_MONTANT_TOTAL = 2F;

    private static final String DEFAULT_LIBELLE_BANQUE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_BANQUE = "BBBBBBBBBB";

    @Autowired
    private DebitCreditRepository debitCreditRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDebitCreditMockMvc;

    private DebitCredit debitCredit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DebitCreditResource debitCreditResource = new DebitCreditResource(debitCreditRepository);
        this.restDebitCreditMockMvc = MockMvcBuilders.standaloneSetup(debitCreditResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DebitCredit createEntity(EntityManager em) {
        DebitCredit debitCredit = new DebitCredit()
            .libelle(DEFAULT_LIBELLE)
            .dateEnregistrement(DEFAULT_DATE_ENREGISTREMENT)
            .isPointe(DEFAULT_IS_POINTE)
            .datePointage(DEFAULT_DATE_POINTAGE)
            .montantTotal(DEFAULT_MONTANT_TOTAL)
            .libelleBanque(DEFAULT_LIBELLE_BANQUE);
        return debitCredit;
    }

    @Before
    public void initTest() {
        debitCredit = createEntity(em);
    }

    @Test
    @Transactional
    public void createDebitCredit() throws Exception {
        int databaseSizeBeforeCreate = debitCreditRepository.findAll().size();

        // Create the DebitCredit
        restDebitCreditMockMvc.perform(post("/api/debit-credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCredit)))
            .andExpect(status().isCreated());

        // Validate the DebitCredit in the database
        List<DebitCredit> debitCreditList = debitCreditRepository.findAll();
        assertThat(debitCreditList).hasSize(databaseSizeBeforeCreate + 1);
        DebitCredit testDebitCredit = debitCreditList.get(debitCreditList.size() - 1);
        assertThat(testDebitCredit.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testDebitCredit.getDateEnregistrement()).isEqualTo(DEFAULT_DATE_ENREGISTREMENT);
        assertThat(testDebitCredit.isIsPointe()).isEqualTo(DEFAULT_IS_POINTE);
        assertThat(testDebitCredit.getDatePointage()).isEqualTo(DEFAULT_DATE_POINTAGE);
        assertThat(testDebitCredit.getMontantTotal()).isEqualTo(DEFAULT_MONTANT_TOTAL);
        assertThat(testDebitCredit.getLibelleBanque()).isEqualTo(DEFAULT_LIBELLE_BANQUE);
    }

    @Test
    @Transactional
    public void createDebitCreditWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = debitCreditRepository.findAll().size();

        // Create the DebitCredit with an existing ID
        debitCredit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDebitCreditMockMvc.perform(post("/api/debit-credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCredit)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DebitCredit> debitCreditList = debitCreditRepository.findAll();
        assertThat(debitCreditList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDebitCredits() throws Exception {
        // Initialize the database
        debitCreditRepository.saveAndFlush(debitCredit);

        // Get all the debitCreditList
        restDebitCreditMockMvc.perform(get("/api/debit-credits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(debitCredit.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].dateEnregistrement").value(hasItem(DEFAULT_DATE_ENREGISTREMENT.toString())))
            .andExpect(jsonPath("$.[*].isPointe").value(hasItem(DEFAULT_IS_POINTE.booleanValue())))
            .andExpect(jsonPath("$.[*].datePointage").value(hasItem(DEFAULT_DATE_POINTAGE.toString())))
            .andExpect(jsonPath("$.[*].montantTotal").value(hasItem(DEFAULT_MONTANT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].libelleBanque").value(hasItem(DEFAULT_LIBELLE_BANQUE.toString())));
    }

    @Test
    @Transactional
    public void getDebitCredit() throws Exception {
        // Initialize the database
        debitCreditRepository.saveAndFlush(debitCredit);

        // Get the debitCredit
        restDebitCreditMockMvc.perform(get("/api/debit-credits/{id}", debitCredit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(debitCredit.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.dateEnregistrement").value(DEFAULT_DATE_ENREGISTREMENT.toString()))
            .andExpect(jsonPath("$.isPointe").value(DEFAULT_IS_POINTE.booleanValue()))
            .andExpect(jsonPath("$.datePointage").value(DEFAULT_DATE_POINTAGE.toString()))
            .andExpect(jsonPath("$.montantTotal").value(DEFAULT_MONTANT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.libelleBanque").value(DEFAULT_LIBELLE_BANQUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDebitCredit() throws Exception {
        // Get the debitCredit
        restDebitCreditMockMvc.perform(get("/api/debit-credits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDebitCredit() throws Exception {
        // Initialize the database
        debitCreditRepository.saveAndFlush(debitCredit);
        int databaseSizeBeforeUpdate = debitCreditRepository.findAll().size();

        // Update the debitCredit
        DebitCredit updatedDebitCredit = debitCreditRepository.findOne(debitCredit.getId());
        updatedDebitCredit
            .libelle(UPDATED_LIBELLE)
            .dateEnregistrement(UPDATED_DATE_ENREGISTREMENT)
            .isPointe(UPDATED_IS_POINTE)
            .datePointage(UPDATED_DATE_POINTAGE)
            .montantTotal(UPDATED_MONTANT_TOTAL)
            .libelleBanque(UPDATED_LIBELLE_BANQUE);

        restDebitCreditMockMvc.perform(put("/api/debit-credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDebitCredit)))
            .andExpect(status().isOk());

        // Validate the DebitCredit in the database
        List<DebitCredit> debitCreditList = debitCreditRepository.findAll();
        assertThat(debitCreditList).hasSize(databaseSizeBeforeUpdate);
        DebitCredit testDebitCredit = debitCreditList.get(debitCreditList.size() - 1);
        assertThat(testDebitCredit.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testDebitCredit.getDateEnregistrement()).isEqualTo(UPDATED_DATE_ENREGISTREMENT);
        assertThat(testDebitCredit.isIsPointe()).isEqualTo(UPDATED_IS_POINTE);
        assertThat(testDebitCredit.getDatePointage()).isEqualTo(UPDATED_DATE_POINTAGE);
        assertThat(testDebitCredit.getMontantTotal()).isEqualTo(UPDATED_MONTANT_TOTAL);
        assertThat(testDebitCredit.getLibelleBanque()).isEqualTo(UPDATED_LIBELLE_BANQUE);
    }

    @Test
    @Transactional
    public void updateNonExistingDebitCredit() throws Exception {
        int databaseSizeBeforeUpdate = debitCreditRepository.findAll().size();

        // Create the DebitCredit

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDebitCreditMockMvc.perform(put("/api/debit-credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debitCredit)))
            .andExpect(status().isCreated());

        // Validate the DebitCredit in the database
        List<DebitCredit> debitCreditList = debitCreditRepository.findAll();
        assertThat(debitCreditList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDebitCredit() throws Exception {
        // Initialize the database
        debitCreditRepository.saveAndFlush(debitCredit);
        int databaseSizeBeforeDelete = debitCreditRepository.findAll().size();

        // Get the debitCredit
        restDebitCreditMockMvc.perform(delete("/api/debit-credits/{id}", debitCredit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DebitCredit> debitCreditList = debitCreditRepository.findAll();
        assertThat(debitCreditList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DebitCredit.class);
        DebitCredit debitCredit1 = new DebitCredit();
        debitCredit1.setId(1L);
        DebitCredit debitCredit2 = new DebitCredit();
        debitCredit2.setId(debitCredit1.getId());
        assertThat(debitCredit1).isEqualTo(debitCredit2);
        debitCredit2.setId(2L);
        assertThat(debitCredit1).isNotEqualTo(debitCredit2);
        debitCredit1.setId(null);
        assertThat(debitCredit1).isNotEqualTo(debitCredit2);
    }
}
