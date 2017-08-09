package com.wizaord.money.service;

import com.codahale.metrics.annotation.Timed;
import com.wizaord.money.domain.Categorie;
import com.wizaord.money.domain.DebitCredit;
import com.wizaord.money.repository.CategorieRepository;
import com.wizaord.money.repository.CompteBancaireRepository;
import com.wizaord.money.repository.DebitCreditRepository;
import com.wizaord.money.service.dto.DebitCreditDTO;
import com.wizaord.money.service.dto.DebitCreditSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DebitCreditUserService {

    private static final Logger log = LoggerFactory.getLogger(DebitCreditUserService.class);

    @Autowired
    private DebitCreditRepository debitCreditRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private CompteBancaireRepository compteBancaireRepository;

    /**
     * Based on the criteria, returns the list of the debitCredits
     * All criteria could be added
     *
     * @param debitCreditSearch
     * @return
     */
    @Timed
    @Transactional(readOnly = true)
    public List<DebitCreditDTO> getDebitCredit(final DebitCreditSearch debitCreditSearch) {
        //get from account
        log.debug("Get all debitCredit on accounts {}", debitCreditSearch.getCompteIds());
        List<DebitCredit> debitCredits = debitCreditRepository.findAllByComptebancaireByCompteRattacheInOrderByDateEnregistrement(debitCreditSearch.getCompteBancaires());
        log.debug("Retrieve {} debitcredit", debitCredits.size());

        //remove before begin date
        if (debitCreditSearch.getBeginDate() != null) {
            debitCredits = debitCredits.parallelStream()
                .filter((debitCredit -> debitCredit.getDateEnregistrement().isAfter(debitCreditSearch.getBeginDate().atStartOfDay().toInstant(ZoneOffset.UTC))))
                .collect(Collectors.toList());
        }

        //remove after end date
        if (debitCreditSearch.getEndDate() != null) {
            debitCredits = debitCredits.parallelStream()
                .filter((debitCredit -> debitCredit.getDateEnregistrement().isBefore(debitCreditSearch.getEndDate().atStartOfDay().toInstant(ZoneOffset.UTC))))
                .collect(Collectors.toList());
        }

        //remove on libelle match
        if (debitCreditSearch.getLibelleMatch() != null && debitCreditSearch.getLibelleMatch().length() != 0) {
            debitCredits = debitCredits.parallelStream()
                .filter(debitCredit -> debitCredit.getLibelle().matches(".*" + debitCreditSearch.getLibelleMatch() + ".*"))
                .collect(Collectors.toList());
        }

        //remove by categorie
        if (debitCreditSearch.getCategorieName() != null) {
            debitCredits = debitCredits.parallelStream()
                .filter(debitCredit -> {
                    long nbMatchElt = debitCredit.getDetails().parallelStream()
                        .filter(detailMontant -> detailMontant.getCategorie() != null)
                        .filter(detailMontant -> detailMontant.getCategorie().getLibelle().matches(".*" + debitCreditSearch.getCategorieName() + ".*"))
                        .count();
                    return nbMatchElt != 0;
                })
                .collect(Collectors.toList());
        }

        //apply criteroa
        List<DebitCreditDTO> dtoList = null;
        if (!debitCredits.isEmpty()) {
            dtoList = debitCredits.stream()
                .map(DebitCreditDTO::new)
                .collect(Collectors.toList());
        }
        return dtoList;
    }

    /**
     * Create a debitCredit
     * @param debitCreditDTO
     */
    public DebitCreditDTO createDebitCredit(DebitCreditDTO debitCreditDTO) {
        //convert DTO in debitCredit
        DebitCredit debitCredit = debitCreditDTO.getDebitCredit();
        debitCredit.setCompteRattache(compteBancaireRepository.getOne(debitCreditDTO.getCompteId()));
        //Init Id
        debitCredit.setId(null);
        debitCredit.getDetails().forEach(detailMontant -> {
            //set Id to null
            detailMontant.setId(null);
            //set detail montant on debitCredit
            detailMontant.setDebitCreditAssocie(debitCredit);
            //looking for categorie
            Categorie c = categorieRepository.getOne(detailMontant.getCategorie().getId());
            detailMontant.setCategorie(c);
        });

        //persist the object
        final DebitCredit debitCreditUpdated = debitCreditRepository.saveAndFlush(debitCredit);
        return new DebitCreditDTO(debitCreditUpdated);
    }
}
