package com.wizaord.money.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wizaord.money.domain.CompteBancaire;

import com.wizaord.money.repository.CompteBancaireRepository;
import com.wizaord.money.web.rest.util.HeaderUtil;
import com.wizaord.money.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CompteBancaire.
 */
@RestController
@RequestMapping("/api")
public class CompteBancaireResource {

    private final Logger log = LoggerFactory.getLogger(CompteBancaireResource.class);

    private static final String ENTITY_NAME = "compteBancaire";

    private final CompteBancaireRepository compteBancaireRepository;

    public CompteBancaireResource(CompteBancaireRepository compteBancaireRepository) {
        this.compteBancaireRepository = compteBancaireRepository;
    }

    /**
     * POST  /compte-bancaires : Create a new compteBancaire.
     *
     * @param compteBancaire the compteBancaire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compteBancaire, or with status 400 (Bad Request) if the compteBancaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compte-bancaires")
    @Timed
    public ResponseEntity<CompteBancaire> createCompteBancaire(@RequestBody CompteBancaire compteBancaire) throws URISyntaxException {
        log.debug("REST request to save CompteBancaire : {}", compteBancaire);
        if (compteBancaire.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new compteBancaire cannot already have an ID")).body(null);
        }
        CompteBancaire result = compteBancaireRepository.save(compteBancaire);
        return ResponseEntity.created(new URI("/api/compte-bancaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /compte-bancaires : Updates an existing compteBancaire.
     *
     * @param compteBancaire the compteBancaire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compteBancaire,
     * or with status 400 (Bad Request) if the compteBancaire is not valid,
     * or with status 500 (Internal Server Error) if the compteBancaire couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compte-bancaires")
    @Timed
    public ResponseEntity<CompteBancaire> updateCompteBancaire(@RequestBody CompteBancaire compteBancaire) throws URISyntaxException {
        log.debug("REST request to update CompteBancaire : {}", compteBancaire);
        if (compteBancaire.getId() == null) {
            return createCompteBancaire(compteBancaire);
        }
        CompteBancaire result = compteBancaireRepository.save(compteBancaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compteBancaire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /compte-bancaires : get all the compteBancaires.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of compteBancaires in body
     */
    @GetMapping("/compte-bancaires")
    @Timed
    public ResponseEntity<List<CompteBancaire>> getAllCompteBancaires(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CompteBancaires");
        Page<CompteBancaire> page = compteBancaireRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/compte-bancaires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /compte-bancaires/:id : get the "id" compteBancaire.
     *
     * @param id the id of the compteBancaire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compteBancaire, or with status 404 (Not Found)
     */
    @GetMapping("/compte-bancaires/{id}")
    @Timed
    public ResponseEntity<CompteBancaire> getCompteBancaire(@PathVariable Long id) {
        log.debug("REST request to get CompteBancaire : {}", id);
        CompteBancaire compteBancaire = compteBancaireRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(compteBancaire));
    }

    /**
     * DELETE  /compte-bancaires/:id : delete the "id" compteBancaire.
     *
     * @param id the id of the compteBancaire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compte-bancaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompteBancaire(@PathVariable Long id) {
        log.debug("REST request to delete CompteBancaire : {}", id);
        compteBancaireRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
