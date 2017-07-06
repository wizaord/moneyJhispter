package com.wizaord.money.repository;

import com.wizaord.money.domain.DebitCredit;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DebitCredit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DebitCreditRepository extends JpaRepository<DebitCredit,Long> {
    
}
