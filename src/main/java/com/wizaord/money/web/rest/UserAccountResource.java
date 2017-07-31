package com.wizaord.money.web.rest;

import com.wizaord.money.domain.CompteBancaire;
import com.wizaord.money.repository.CompteBancaireRepository;
import com.wizaord.money.repository.UserRepository;
import com.wizaord.money.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api/users/accounts")
public class UserAccountResource {

    /**
     * The default Logger
     */
    private final Logger log = LoggerFactory.getLogger(UserAccountResource.class);

    @Autowired
    private CompteBancaireRepository compteBancaireRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Returns the user compteBancaire
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<List<CompteBancaire>> getUserCompteBancaire() {
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).map(user -> {
            log.info("User with id {} is requesting his accounts", user.getId());
            List<CompteBancaire> userAccounts = compteBancaireRepository.getAllByProprietaire(user.getId());
            return new ResponseEntity<List<CompteBancaire>>(userAccounts, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED));
    }

}
