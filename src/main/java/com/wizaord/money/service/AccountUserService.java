package com.wizaord.money.service;

import com.wizaord.money.domain.DebitCredit;
import com.wizaord.money.repository.CompteBancaireRepository;
import com.wizaord.money.repository.DebitCreditRepository;
import com.wizaord.money.service.dto.AccountDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountUserService {

    private static final Logger log = LoggerFactory.getLogger(AccountUserService.class);

    @Autowired
    private CompteBancaireRepository compteBancaireRepository;
    @Autowired
    private DebitCreditRepository debitCreditRepository;

    /**
     * This function returns the list of accounts for a user
     *
     * @return
     */
    public List<AccountDetailDTO> getUserAccounts(final Long userId) {
        log.info("User with id {} is requesting his accounts", userId);

        return compteBancaireRepository.getAllByProprietaire(userId).stream()
            .map((compte) -> {
                DebitCredit lastDB = debitCreditRepository.getFirstByComptebancaireByCompteRattacheOrderByDateEnregistrement(compte);
                AccountDetailDTO addto = new AccountDetailDTO(compte);
                addto .setLastTransaction(lastDB.getDateEnregistrement());
                return addto ;
            }).collect(Collectors.toList());
    }
}
