package com.wizaord.money.web.rest;

import com.wizaord.money.repository.UserRepository;
import com.wizaord.money.security.SecurityUtils;
import com.wizaord.money.service.AccountUserService;
import com.wizaord.money.service.dto.AccountDetailDTO;
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
    private AccountUserService accountUserService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Returns the user compteBancaire
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<List<AccountDetailDTO>> getUserCompteBancaire() {
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).map(user -> {
            List<AccountDetailDTO> lstDTO = accountUserService.getUserAccounts(user.getId());
            return new ResponseEntity<List<AccountDetailDTO>>(lstDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED));
    }

}
