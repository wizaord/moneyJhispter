package com.wizaord.money.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wizaord.money.domain.DebitCredit;

import com.wizaord.money.repository.DebitCreditRepository;
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
 * REST controller for managing DebitCredit.
 */
@RestController
@RequestMapping("/api")
public class DebitCreditResource {

    private final Logger log = LoggerFactory.getLogger(DebitCreditResource.class);

    private static final String ENTITY_NAME = "debitCredit";

    private final DebitCreditRepository debitCreditRepository;

    public DebitCreditResource(DebitCreditRepository debitCreditRepository) {
        this.debitCreditRepository = debitCreditRepository;
    }

    /**
     * POST  /debit-credits : Create a new debitCredit.
     *
     * @param debitCredit the debitCredit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new debitCredit, or with status 400 (Bad Request) if the debitCredit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/debit-credits")
    @Timed
    public ResponseEntity<DebitCredit> createDebitCredit(@RequestBody DebitCredit debitCredit) throws URISyntaxException {
        log.debug("REST request to save DebitCredit : {}", debitCredit);
        if (debitCredit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new debitCredit cannot already have an ID")).body(null);
        }
        DebitCredit result = debitCreditRepository.save(debitCredit);
        return ResponseEntity.created(new URI("/api/debit-credits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /debit-credits : Updates an existing debitCredit.
     *
     * @param debitCredit the debitCredit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated debitCredit,
     * or with status 400 (Bad Request) if the debitCredit is not valid,
     * or with status 500 (Internal Server Error) if the debitCredit couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/debit-credits")
    @Timed
    public ResponseEntity<DebitCredit> updateDebitCredit(@RequestBody DebitCredit debitCredit) throws URISyntaxException {
        log.debug("REST request to update DebitCredit : {}", debitCredit);
        if (debitCredit.getId() == null) {
            return createDebitCredit(debitCredit);
        }
        DebitCredit result = debitCreditRepository.save(debitCredit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, debitCredit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /debit-credits : get all the debitCredits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of debitCredits in body
     */
    /*@GetMapping("/debit-credits")
    @Timed
    public List<DebitCredit> getAllDebitCredits() {
        log.debug("REST request to get all DebitCredits");
        return debitCreditRepository.findAll();
    }*/

    /**
     * GET  /debit-credits : get all the debitCredits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of debitCredits in body
     */
    @GetMapping("/debit-credits")
    @Timed
    public ResponseEntity<List<DebitCredit>> getAllDebitCredits(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CompteBancaires");
        Page<DebitCredit> page = debitCreditRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/debit-credits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /debit-credits/:id : get the "id" debitCredit.
     *
     * @param id the id of the debitCredit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the debitCredit, or with status 404 (Not Found)
     */
    @GetMapping("/debit-credits/{id}")
    @Timed
    public ResponseEntity<DebitCredit> getDebitCredit(@PathVariable Long id) {
        log.debug("REST request to get DebitCredit : {}", id);
        DebitCredit debitCredit = debitCreditRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(debitCredit));
    }

    /**
     * DELETE  /debit-credits/:id : delete the "id" debitCredit.
     *
     * @param id the id of the debitCredit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/debit-credits/{id}")
    @Timed
    public ResponseEntity<Void> deleteDebitCredit(@PathVariable Long id) {
        log.debug("REST request to delete DebitCredit : {}", id);
        debitCreditRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
