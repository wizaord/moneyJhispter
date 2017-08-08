package com.wizaord.money.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wizaord.money.domain.User;
import com.wizaord.money.repository.UserRepository;
import com.wizaord.money.security.AuthoritiesConstants;
import com.wizaord.money.security.SecurityUtils;
import com.wizaord.money.service.AccountUserService;
import com.wizaord.money.service.DebitCreditUserService;
import com.wizaord.money.service.dto.DebitCreditDTO;
import com.wizaord.money.service.dto.DebitCreditSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api/users/debitcredit")
public class UserDebitCreditResource {

    /**
     * The default Logger
     */
    private final Logger log = LoggerFactory.getLogger(UserDebitCreditResource.class);

    private UserRepository userRepository;
    private AccountUserService accountUserService;
    private DebitCreditUserService debitCreditUserService;


    /**
     * Default constructor
     * @param userRepository
     * @param accountUserService
     * @param debitCreditUserService
     */
    public UserDebitCreditResource(UserRepository userRepository, AccountUserService accountUserService, DebitCreditUserService debitCreditUserService) {
        this.userRepository = userRepository;
        this.accountUserService = accountUserService;
        this.debitCreditUserService = debitCreditUserService;
    }


    @GetMapping("/")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<DebitCreditDTO>> getDebitsCredits(@RequestBody DebitCreditSearch debitCreditSearch) {
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        //if the user is not authenticated
        if (! user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
        //if no account has been set in the post request
        if (debitCreditSearch == null || debitCreditSearch.getCompteIds().size() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 1 - check that the user is the owner of all accounts
        if (!accountUserService.isAccountsOwner(user.get().getId(), debitCreditSearch.getCompteIds())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // 2 - get all debitCredits
        final List<DebitCreditDTO> debitsCredits = debitCreditUserService.getDebitCredit(debitCreditSearch);
        return new ResponseEntity<>(debitsCredits, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Void> deleteDebitCredit(@PathVariable Long id) {
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if (! user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
        // 1 - check that the user is the owner of the debitCredit

        // 2 - delete the debit credit

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}")
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Void> updateDebitCredit(@PathVariable Long id, @RequestBody DebitCreditDTO debitCreditDTO) {
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if (! user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
        // 1 - check that the user is the owner of the debitCredit

        // 2 - update debitCredit

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Void> createDebitCredit(@RequestBody DebitCreditDTO debitCreditDTO) {
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if (! user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
        // 1 - check that the user is the owner of the account where debitCredit will be create

        // 2 - update debitCredit

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
