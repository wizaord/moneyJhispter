package com.wizaord.money.service;

import com.codahale.metrics.annotation.Timed;
import com.wizaord.money.domain.DebitCredit;
import com.wizaord.money.repository.DebitCreditRepository;
import com.wizaord.money.service.dto.DebitCreditDTO;
import com.wizaord.money.service.dto.DebitCreditSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DebitCreditUserService {

    private static final Logger log = LoggerFactory.getLogger(DebitCreditUserService.class);

    @Autowired
    private DebitCreditRepository debitCreditRepository;

    /**
     * Based on the criteria, returns the list of the debitCredits
     * All criteria could be added
     * @param debitCreditSearch
     * @return
     */
    @Timed
    public List<DebitCreditDTO> getDebitCredit(final DebitCreditSearch debitCreditSearch) {
        //get from account
        log.debug("Get all debitCredit on accounts {}", debitCreditSearch.getCompteIds());
        List<DebitCredit> debitCredits = debitCreditRepository.findAllByComptebancaireByCompteRattacheOrderByDateEnregistrement(debitCreditSearch.getCompteBancaires());
        log.debug("Retrieve {} debitcredit", debitCredits.size());

        //apply criteroa

        return null;
    }
}