package com.wizaord.money.repository;

import com.wizaord.money.domain.CompteBancaire;
import com.wizaord.money.domain.DebitCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the DebitCredit entity.
 */
@Repository
public interface DebitCreditRepository extends JpaRepository<DebitCredit,Long> {

    DebitCredit getFirstByComptebancaireByCompteRattacheOrderByDateEnregistrement(CompteBancaire cb);

    List<DebitCredit> findAllByComptebancaireByCompteRattacheOrderByDateEnregistrement(List<CompteBancaire> cbs);
}
