package com.wizaord.money.repository;

import com.wizaord.money.domain.CompteBancaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the CompteBancaire entity.
 */
@Repository
public interface CompteBancaireRepository extends JpaRepository<CompteBancaire,Long> {

    List<CompteBancaire> getAllByProprietaire(final Long proprietaireId);
}
