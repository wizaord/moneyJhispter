package com.wizaord.money.service;

import com.codahale.metrics.annotation.Timed;
import com.wizaord.money.domain.Categorie;
import com.wizaord.money.domain.CompteBancaire;
import com.wizaord.money.domain.DebitCredit;
import com.wizaord.money.domain.DetailMontant;
import com.wizaord.money.repository.CategorieRepository;
import com.wizaord.money.repository.CompteBancaireRepository;
import com.wizaord.money.repository.DebitCreditRepository;
import com.wizaord.money.repository.DetailMontantRepository;
import com.wizaord.money.service.dto.DebitCreditDTO;
import com.wizaord.money.service.dto.DebitCreditSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    private DetailMontantRepository detailMontantRepository;
    @Autowired
    private EntityManager em;

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
     *
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


    /**
     * Return the accountId for a debitCredit.
     * If the debitCredit does not exist, this function will return an optional null value.
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<Long> getAccountIdFromDebitCredit(final long debitCreditId) {
        final DebitCredit debitCredit = debitCreditRepository.findOne(debitCreditId);
        if (debitCredit != null) {
            return Optional.of(debitCredit.getCompterattache().getId());
        }
        return Optional.empty();
    }

    /**
     * This function remove the debitCredit
     *
     * @param id
     */
    public void delete(Long id) {
        debitCreditRepository.delete(id);
    }

    /**
     * Check that the DTO is valid with the database object instance.
     * Some parameter must not change
     *
     * @param debitCreditDTO
     * @return
     */
    @Transactional(readOnly = true)
    public boolean isDebitCreditValid(DebitCreditDTO debitCreditDTO) {
        if (debitCreditDTO == null || debitCreditDTO.getId() == null) {
            return false;
        }

        final DebitCredit one = debitCreditRepository.findOne(debitCreditDTO.getId());
        if (one == null) {
            return false;
        }

        //check the same account
        if (one.getCompterattache().getId() != debitCreditDTO.getCompteId()) {
            return false;
        }
        return true;
    }

    /**
     * Update a specific debitCredit with some new value
     *
     * @param debitCreditDTO
     * @return
     */
    public DebitCreditDTO update(DebitCreditDTO debitCreditDTO) {
        //set the account
        final CompteBancaire compteRattache = compteBancaireRepository.getOne(debitCreditDTO.getCompteId());

        //refresh with the persistence manager
        DebitCredit debitCreditFromDB = debitCreditRepository.getOne(debitCreditDTO.getId());

        //update only following parameter
        debitCreditFromDB.setMontantTotal(debitCreditDTO.getMontantTotal());
        debitCreditFromDB.setIsPointe(debitCreditDTO.isPointe());
        debitCreditFromDB.setDatePointage(debitCreditDTO.getDatePointage());
        debitCreditFromDB.setDateEnregistrement(debitCreditDTO.getDateTransaction());
        debitCreditFromDB.setLibelleBanque(debitCreditDTO.getLibelleBanque());
        debitCreditFromDB.setLibelle(debitCreditDTO.getLibellePerso());

        //update current details
        debitCreditDTO.getDetails().parallelStream()
            .filter(detailMontantDTO -> detailMontantDTO.getId() != null)
            .forEach(detailMontantDTO -> {
                for(DetailMontant detailMontant : debitCreditFromDB.getDetails()) {
                    if (detailMontant.getId() == detailMontantDTO.getId()) {
                        // update detailMontant value
                        detailMontant.setVirementInterneCompteId(detailMontantDTO.getVirementInterneCompteId());
                        detailMontant.setMontant(detailMontantDTO.getMontant());
                        //check if categorie need to be updated
                        if (detailMontant.getCategorie().getId() != detailMontantDTO.getCategorieId()) {
                            Categorie c = categorieRepository.getOne(detailMontantDTO.getCategorieId());
                            detailMontant.setCategorie(c);
                        }
                    }
                }
            });
        //remove unused details
        debitCreditFromDB.getDetails().stream()
            .forEach(detailMontant -> {
                if (!debitCreditDTO.detailMontantDTOContainId(detailMontant.getId())) {
                    // il faut supprimer ce detailMontant
                    DebitCredit db = debitCreditFromDB.removeDetails(detailMontant);
                    log.debug("Remove from debitCredit the detailMontant with ID {}", db.getId());
                    //delete the detail montant from database
                    detailMontantRepository.delete(detailMontant);
                }
            });

        //add new details
        debitCreditDTO.getDetails().parallelStream()
            .filter(detailMontantDTO -> detailMontantDTO.getId() == null)
            .forEach(detailMontantDTO -> {
                Categorie c = categorieRepository.getOne(detailMontantDTO.getCategorieId());
                final DetailMontant detailMontant = detailMontantDTO.getDetailMontant();
                detailMontant.setCategorie(c);
                debitCreditFromDB.addDetails(detailMontant);
            });

        //commit modification
        return new DebitCreditDTO(debitCreditRepository.saveAndFlush(debitCreditFromDB));
    }
}
