package com.wizaord.money.web.rest;

import com.wizaord.money.MoneyJhipsterApp;

import com.wizaord.money.domain.DetailMontant;
import com.wizaord.money.repository.DetailMontantRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DetailMontantResource REST controller.
 *
 * @see DetailMontantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoneyJhipsterApp.class)
public class DetailMontantResourceIntTest {

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final Integer DEFAULT_VIREMENT_INTERNE_COMPTE_ID = 1;
    private static final Integer UPDATED_VIREMENT_INTERNE_COMPTE_ID = 2;

    @Autowired
    private DetailMontantRepository detailMontantRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDetailMontantMockMvc;

    private DetailMontant detailMontant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DetailMontantResource detailMontantResource = new DetailMontantResource(detailMontantRepository);
        this.restDetailMontantMockMvc = MockMvcBuilders.standaloneSetup(detailMontantResource)
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
    public static DetailMontant createEntity(EntityManager em) {
        DetailMontant detailMontant = new DetailMontant()
            .montant(DEFAULT_MONTANT)
            .virementInterneCompteId(DEFAULT_VIREMENT_INTERNE_COMPTE_ID);
        return detailMontant;
    }

    @Before
    public void initTest() {
        detailMontant = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetailMontant() throws Exception {
        int databaseSizeBeforeCreate = detailMontantRepository.findAll().size();

        // Create the DetailMontant
        restDetailMontantMockMvc.perform(post("/api/detail-montants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailMontant)))
            .andExpect(status().isCreated());

        // Validate the DetailMontant in the database
        List<DetailMontant> detailMontantList = detailMontantRepository.findAll();
        assertThat(detailMontantList).hasSize(databaseSizeBeforeCreate + 1);
        DetailMontant testDetailMontant = detailMontantList.get(detailMontantList.size() - 1);
        assertThat(testDetailMontant.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testDetailMontant.getVirementInterneCompteId()).isEqualTo(DEFAULT_VIREMENT_INTERNE_COMPTE_ID);
    }

    @Test
    @Transactional
    public void createDetailMontantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detailMontantRepository.findAll().size();

        // Create the DetailMontant with an existing ID
        detailMontant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailMontantMockMvc.perform(post("/api/detail-montants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailMontant)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DetailMontant> detailMontantList = detailMontantRepository.findAll();
        assertThat(detailMontantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDetailMontants() throws Exception {
        // Initialize the database
        detailMontantRepository.saveAndFlush(detailMontant);

        // Get all the detailMontantList
        restDetailMontantMockMvc.perform(get("/api/detail-montants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailMontant.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].virementInterneCompteId").value(hasItem(DEFAULT_VIREMENT_INTERNE_COMPTE_ID)));
    }

    @Test
    @Transactional
    public void getDetailMontant() throws Exception {
        // Initialize the database
        detailMontantRepository.saveAndFlush(detailMontant);

        // Get the detailMontant
        restDetailMontantMockMvc.perform(get("/api/detail-montants/{id}", detailMontant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detailMontant.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.virementInterneCompteId").value(DEFAULT_VIREMENT_INTERNE_COMPTE_ID));
    }

    @Test
    @Transactional
    public void getNonExistingDetailMontant() throws Exception {
        // Get the detailMontant
        restDetailMontantMockMvc.perform(get("/api/detail-montants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetailMontant() throws Exception {
        // Initialize the database
        detailMontantRepository.saveAndFlush(detailMontant);
        int databaseSizeBeforeUpdate = detailMontantRepository.findAll().size();

        // Update the detailMontant
        DetailMontant updatedDetailMontant = detailMontantRepository.findOne(detailMontant.getId());
        updatedDetailMontant
            .montant(UPDATED_MONTANT)
            .virementInterneCompteId(UPDATED_VIREMENT_INTERNE_COMPTE_ID);

        restDetailMontantMockMvc.perform(put("/api/detail-montants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDetailMontant)))
            .andExpect(status().isOk());

        // Validate the DetailMontant in the database
        List<DetailMontant> detailMontantList = detailMontantRepository.findAll();
        assertThat(detailMontantList).hasSize(databaseSizeBeforeUpdate);
        DetailMontant testDetailMontant = detailMontantList.get(detailMontantList.size() - 1);
        assertThat(testDetailMontant.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testDetailMontant.getVirementInterneCompteId()).isEqualTo(UPDATED_VIREMENT_INTERNE_COMPTE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingDetailMontant() throws Exception {
        int databaseSizeBeforeUpdate = detailMontantRepository.findAll().size();

        // Create the DetailMontant

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDetailMontantMockMvc.perform(put("/api/detail-montants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailMontant)))
            .andExpect(status().isCreated());

        // Validate the DetailMontant in the database
        List<DetailMontant> detailMontantList = detailMontantRepository.findAll();
        assertThat(detailMontantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDetailMontant() throws Exception {
        // Initialize the database
        detailMontantRepository.saveAndFlush(detailMontant);
        int databaseSizeBeforeDelete = detailMontantRepository.findAll().size();

        // Get the detailMontant
        restDetailMontantMockMvc.perform(delete("/api/detail-montants/{id}", detailMontant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DetailMontant> detailMontantList = detailMontantRepository.findAll();
        assertThat(detailMontantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailMontant.class);
        DetailMontant detailMontant1 = new DetailMontant();
        detailMontant1.setId(1L);
        DetailMontant detailMontant2 = new DetailMontant();
        detailMontant2.setId(detailMontant1.getId());
        assertThat(detailMontant1).isEqualTo(detailMontant2);
        detailMontant2.setId(2L);
        assertThat(detailMontant1).isNotEqualTo(detailMontant2);
        detailMontant1.setId(null);
        assertThat(detailMontant1).isNotEqualTo(detailMontant2);
    }
}
