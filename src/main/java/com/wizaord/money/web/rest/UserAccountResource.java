package com.wizaord.money.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wizaord.money.domain.User;
import com.wizaord.money.repository.UserRepository;
import com.wizaord.money.security.AuthoritiesConstants;
import com.wizaord.money.security.SecurityUtils;
import com.wizaord.money.service.AccountUserService;
import com.wizaord.money.service.Exception.NotAllowedException;
import com.wizaord.money.service.dto.AccountDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/users/accounts")
public class UserAccountResource {

    /**
     * The default Logger
     */
    private final Logger log = LoggerFactory.getLogger(UserAccountResource.class);

    @Autowired
    private AccountUserService accountUserService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Returns the user compteBancaire
     *
     * @return
     */
    @GetMapping("/")
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<AccountDetailDTO>> getUserCompteBancaire() {
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).map(user -> {
            List<AccountDetailDTO> lstDTO = accountUserService.getUserAccounts(user.getId());
            return new ResponseEntity<List<AccountDetailDTO>>(lstDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED));
    }

    /**
     * DELETE /api/users/accounts/:id : delete the "account" User.
     *
     * @param id : The account id
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/:id")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        log.info("REST request to delete Account: {}", accountId);
        String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userRepository.findOneByLogin(userLogin);

        if (user.isPresent()) {
            try {
                this.accountUserService.deleteAccount(user.get().getId(), accountId);
                return ResponseEntity.ok().build();
            } catch (NotAllowedException e) {
            }
            return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
        } else {
            return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

}
