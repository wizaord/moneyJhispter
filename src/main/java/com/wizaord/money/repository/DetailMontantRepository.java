package com.wizaord.money.repository;

import com.wizaord.money.domain.DetailMontant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DetailMontant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailMontantRepository extends JpaRepository<DetailMontant,Long> {
    
}
