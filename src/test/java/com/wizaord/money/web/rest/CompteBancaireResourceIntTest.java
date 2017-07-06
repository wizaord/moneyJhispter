package com.wizaord.money.web.rest;

import com.wizaord.money.MoneyJhipsterApp;

import com.wizaord.money.domain.CompteBancaire;
import com.wizaord.money.repository.CompteBancaireRepository;
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
 * Test class for the CompteBancaireResource REST controller.
 *
 * @see CompteBancaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoneyJhipsterApp.class)
public class CompteBancaireResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_COMPTE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_COMPTE = "BBBBBBBBBB";

    private static final Float DEFAULT_MONTANT_SOLDE = 1F;
    private static final Float UPDATED_MONTANT_SOLDE = 2F;

    private static final Instant DEFAULT_DATE_OUVERTURE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OUVERTURE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_FERMETURE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FERMETURE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_CLOS = false;
    private static final Boolean UPDATED_IS_CLOS = true;

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Integer DEFAULT_PROPRIETAIRE = 1;
    private static final Integer UPDATED_PROPRIETAIRE = 2;

    @Autowired
    private CompteBancaireRepository compteBancaireRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompteBancaireMockMvc;

    private CompteBancaire compteBancaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompteBancaireResource compteBancaireResource = new CompteBancaireResource(compteBancaireRepository);
        this.restCompteBancaireMockMvc = MockMvcBuilders.standaloneSetup(compteBancaireResource)
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
    public static CompteBancaire createEntity(EntityManager em) {
        CompteBancaire compteBancaire = new CompteBancaire()
            .libelle(DEFAULT_LIBELLE)
            .numeroCompte(DEFAULT_NUMERO_COMPTE)
            .montantSolde(DEFAULT_MONTANT_SOLDE)
            .dateOuverture(DEFAULT_DATE_OUVERTURE)
            .dateFermeture(DEFAULT_DATE_FERMETURE)
            .isClos(DEFAULT_IS_CLOS)
            .isDeleted(DEFAULT_IS_DELETED)
            .proprietaire(DEFAULT_PROPRIETAIRE);
        return compteBancaire;
    }

    @Before
    public void initTest() {
        compteBancaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompteBancaire() throws Exception {
        int databaseSizeBeforeCreate = compteBancaireRepository.findAll().size();

        // Create the CompteBancaire
        restCompteBancaireMockMvc.perform(post("/api/compte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteBancaire)))
            .andExpect(status().isCreated());

        // Validate the CompteBancaire in the database
        List<CompteBancaire> compteBancaireList = compteBancaireRepository.findAll();
        assertThat(compteBancaireList).hasSize(databaseSizeBeforeCreate + 1);
        CompteBancaire testCompteBancaire = compteBancaireList.get(compteBancaireList.size() - 1);
        assertThat(testCompteBancaire.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testCompteBancaire.getNumeroCompte()).isEqualTo(DEFAULT_NUMERO_COMPTE);
        assertThat(testCompteBancaire.getMontantSolde()).isEqualTo(DEFAULT_MONTANT_SOLDE);
        assertThat(testCompteBancaire.getDateOuverture()).isEqualTo(DEFAULT_DATE_OUVERTURE);
        assertThat(testCompteBancaire.getDateFermeture()).isEqualTo(DEFAULT_DATE_FERMETURE);
        assertThat(testCompteBancaire.isIsClos()).isEqualTo(DEFAULT_IS_CLOS);
        assertThat(testCompteBancaire.isIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testCompteBancaire.getProprietaire()).isEqualTo(DEFAULT_PROPRIETAIRE);
    }

    @Test
    @Transactional
    public void createCompteBancaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compteBancaireRepository.findAll().size();

        // Create the CompteBancaire with an existing ID
        compteBancaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompteBancaireMockMvc.perform(post("/api/compte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteBancaire)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CompteBancaire> compteBancaireList = compteBancaireRepository.findAll();
        assertThat(compteBancaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompteBancaires() throws Exception {
        // Initialize the database
        compteBancaireRepository.saveAndFlush(compteBancaire);

        // Get all the compteBancaireList
        restCompteBancaireMockMvc.perform(get("/api/compte-bancaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compteBancaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].numeroCompte").value(hasItem(DEFAULT_NUMERO_COMPTE.toString())))
            .andExpect(jsonPath("$.[*].montantSolde").value(hasItem(DEFAULT_MONTANT_SOLDE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateOuverture").value(hasItem(DEFAULT_DATE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].dateFermeture").value(hasItem(DEFAULT_DATE_FERMETURE.toString())))
            .andExpect(jsonPath("$.[*].isClos").value(hasItem(DEFAULT_IS_CLOS.booleanValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].proprietaire").value(hasItem(DEFAULT_PROPRIETAIRE)));
    }

    @Test
    @Transactional
    public void getCompteBancaire() throws Exception {
        // Initialize the database
        compteBancaireRepository.saveAndFlush(compteBancaire);

        // Get the compteBancaire
        restCompteBancaireMockMvc.perform(get("/api/compte-bancaires/{id}", compteBancaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compteBancaire.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.numeroCompte").value(DEFAULT_NUMERO_COMPTE.toString()))
            .andExpect(jsonPath("$.montantSolde").value(DEFAULT_MONTANT_SOLDE.doubleValue()))
            .andExpect(jsonPath("$.dateOuverture").value(DEFAULT_DATE_OUVERTURE.toString()))
            .andExpect(jsonPath("$.dateFermeture").value(DEFAULT_DATE_FERMETURE.toString()))
            .andExpect(jsonPath("$.isClos").value(DEFAULT_IS_CLOS.booleanValue()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.proprietaire").value(DEFAULT_PROPRIETAIRE));
    }

    @Test
    @Transactional
    public void getNonExistingCompteBancaire() throws Exception {
        // Get the compteBancaire
        restCompteBancaireMockMvc.perform(get("/api/compte-bancaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompteBancaire() throws Exception {
        // Initialize the database
        compteBancaireRepository.saveAndFlush(compteBancaire);
        int databaseSizeBeforeUpdate = compteBancaireRepository.findAll().size();

        // Update the compteBancaire
        CompteBancaire updatedCompteBancaire = compteBancaireRepository.findOne(compteBancaire.getId());
        updatedCompteBancaire
            .libelle(UPDATED_LIBELLE)
            .numeroCompte(UPDATED_NUMERO_COMPTE)
            .montantSolde(UPDATED_MONTANT_SOLDE)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE)
            .isClos(UPDATED_IS_CLOS)
            .isDeleted(UPDATED_IS_DELETED)
            .proprietaire(UPDATED_PROPRIETAIRE);

        restCompteBancaireMockMvc.perform(put("/api/compte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompteBancaire)))
            .andExpect(status().isOk());

        // Validate the CompteBancaire in the database
        List<CompteBancaire> compteBancaireList = compteBancaireRepository.findAll();
        assertThat(compteBancaireList).hasSize(databaseSizeBeforeUpdate);
        CompteBancaire testCompteBancaire = compteBancaireList.get(compteBancaireList.size() - 1);
        assertThat(testCompteBancaire.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCompteBancaire.getNumeroCompte()).isEqualTo(UPDATED_NUMERO_COMPTE);
        assertThat(testCompteBancaire.getMontantSolde()).isEqualTo(UPDATED_MONTANT_SOLDE);
        assertThat(testCompteBancaire.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testCompteBancaire.getDateFermeture()).isEqualTo(UPDATED_DATE_FERMETURE);
        assertThat(testCompteBancaire.isIsClos()).isEqualTo(UPDATED_IS_CLOS);
        assertThat(testCompteBancaire.isIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testCompteBancaire.getProprietaire()).isEqualTo(UPDATED_PROPRIETAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompteBancaire() throws Exception {
        int databaseSizeBeforeUpdate = compteBancaireRepository.findAll().size();

        // Create the CompteBancaire

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompteBancaireMockMvc.perform(put("/api/compte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteBancaire)))
            .andExpect(status().isCreated());

        // Validate the CompteBancaire in the database
        List<CompteBancaire> compteBancaireList = compteBancaireRepository.findAll();
        assertThat(compteBancaireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompteBancaire() throws Exception {
        // Initialize the database
        compteBancaireRepository.saveAndFlush(compteBancaire);
        int databaseSizeBeforeDelete = compteBancaireRepository.findAll().size();

        // Get the compteBancaire
        restCompteBancaireMockMvc.perform(delete("/api/compte-bancaires/{id}", compteBancaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompteBancaire> compteBancaireList = compteBancaireRepository.findAll();
        assertThat(compteBancaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompteBancaire.class);
        CompteBancaire compteBancaire1 = new CompteBancaire();
        compteBancaire1.setId(1L);
        CompteBancaire compteBancaire2 = new CompteBancaire();
        compteBancaire2.setId(compteBancaire1.getId());
        assertThat(compteBancaire1).isEqualTo(compteBancaire2);
        compteBancaire2.setId(2L);
        assertThat(compteBancaire1).isNotEqualTo(compteBancaire2);
        compteBancaire1.setId(null);
        assertThat(compteBancaire1).isNotEqualTo(compteBancaire2);
    }
}
