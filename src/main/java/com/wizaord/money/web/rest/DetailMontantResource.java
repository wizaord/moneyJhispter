package com.wizaord.money.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wizaord.money.domain.DetailMontant;

import com.wizaord.money.repository.DetailMontantRepository;
import com.wizaord.money.web.rest.util.HeaderUtil;
import com.wizaord.money.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
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
 * REST controller for managing DetailMontant.
 */
@RestController
@RequestMapping("/api")
public class DetailMontantResource {

    private final Logger log = LoggerFactory.getLogger(DetailMontantResource.class);

    private static final String ENTITY_NAME = "detailMontant";

    private final DetailMontantRepository detailMontantRepository;

    public DetailMontantResource(DetailMontantRepository detailMontantRepository) {
        this.detailMontantRepository = detailMontantRepository;
    }

    /**
     * POST  /detail-montants : Create a new detailMontant.
     *
     * @param detailMontant the detailMontant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new detailMontant, or with status 400 (Bad Request) if the detailMontant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/detail-montants")
    @Timed
    public ResponseEntity<DetailMontant> createDetailMontant(@RequestBody DetailMontant detailMontant) throws URISyntaxException {
        log.debug("REST request to save DetailMontant : {}", detailMontant);
        if (detailMontant.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new detailMontant cannot already have an ID")).body(null);
        }
        DetailMontant result = detailMontantRepository.save(detailMontant);
        return ResponseEntity.created(new URI("/api/detail-montants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /detail-montants : Updates an existing detailMontant.
     *
     * @param detailMontant the detailMontant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated detailMontant,
     * or with status 400 (Bad Request) if the detailMontant is not valid,
     * or with status 500 (Internal Server Error) if the detailMontant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/detail-montants")
    @Timed
    public ResponseEntity<DetailMontant> updateDetailMontant(@RequestBody DetailMontant detailMontant) throws URISyntaxException {
        log.debug("REST request to update DetailMontant : {}", detailMontant);
        if (detailMontant.getId() == null) {
            return createDetailMontant(detailMontant);
        }
        DetailMontant result = detailMontantRepository.save(detailMontant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detailMontant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /detail-montants : get all the detailMontants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of detailMontants in body
     */
    @GetMapping("/detail-montants")
    @Timed
    public ResponseEntity<List<DetailMontant>> getAllDetailMontants(@ApiParam Pageable pageable) {
        log.debug("REST request to get all DetailMontants");
        Page<DetailMontant> page = detailMontantRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/detail-montants");
        return new ResponseEntity<List<DetailMontant>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /detail-montants/:id : get the "id" detailMontant.
     *
     * @param id the id of the detailMontant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detailMontant, or with status 404 (Not Found)
     */
    @GetMapping("/detail-montants/{id}")
    @Timed
    public ResponseEntity<DetailMontant> getDetailMontant(@PathVariable Long id) {
        log.debug("REST request to get DetailMontant : {}", id);
        DetailMontant detailMontant = detailMontantRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(detailMontant));
    }

    /**
     * DELETE  /detail-montants/:id : delete the "id" detailMontant.
     *
     * @param id the id of the detailMontant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/detail-montants/{id}")
    @Timed
    public ResponseEntity<Void> deleteDetailMontant(@PathVariable Long id) {
        log.debug("REST request to delete DetailMontant : {}", id);
        detailMontantRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
