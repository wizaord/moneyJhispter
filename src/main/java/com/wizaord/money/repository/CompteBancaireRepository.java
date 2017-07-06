package com.wizaord.money.repository;

import com.wizaord.money.domain.CompteBancaire;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompteBancaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteBancaireRepository extends JpaRepository<CompteBancaire,Long> {
    
}
