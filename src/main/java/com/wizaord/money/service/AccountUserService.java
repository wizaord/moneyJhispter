package com.wizaord.money.service;

import com.wizaord.money.domain.CompteBancaire;
import com.wizaord.money.domain.DebitCredit;
import com.wizaord.money.repository.CompteBancaireRepository;
import com.wizaord.money.repository.DebitCreditRepository;
import com.wizaord.money.service.Exception.NotAllowedException;
import com.wizaord.money.service.dto.AccountDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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

    @Transactional(readOnly = true)
    public List<AccountDetailDTO> getUserAccounts(final Long userId) {
        log.info("User with id {} is requesting his accounts", userId);

        return compteBancaireRepository.getAllByProprietaire(userId.intValue()).stream()
            .map((compte) -> {
                DebitCredit lastDB = debitCreditRepository.getFirstByComptebancaireByCompteRattacheOrderByDateEnregistrement(compte);
                AccountDetailDTO addto = new AccountDetailDTO(compte);
                addto .setLastTransaction(lastDB.getDateEnregistrement());
                return addto ;
            }).collect(Collectors.toList());
    }


    /**
     * Test if the user is the owner of the account. If true, the account is returned.
     *
     * @param userId : the userId
     * @param accountId : the accountId
     * @return Optional<CompteBancaire>
     * @throws NotAllowedException : if the user is not the account owner
     */
    @Transactional(readOnly = true)
    public Optional<CompteBancaire> isAccountOwner(final Long userId, final Long accountId) throws NotAllowedException {
        //if accountId is null, return null
        if (accountId == null) {
            return Optional.ofNullable(null);
        }
        Optional<CompteBancaire> cb = Optional.ofNullable(compteBancaireRepository.findOne(accountId));
        if (cb.isPresent()) {
            if (cb.get().getProprietaire().intValue() != userId) {
                throw new NotAllowedException("You are not allowed to delete this account");
            }
        }
        return cb;
    }

    /**
     * Delete the account from the user
     * If the account does not exist or if the user is not the account owner, an exception is raised
     */
    @Transactional
    public void deleteAccount(final Long userId, final Long accountId) throws NotAllowedException {
        isAccountOwner(userId, accountId).ifPresent((account) -> {
            account.setIsDeleted(true);
            compteBancaireRepository.save(account);
        });
    }
}
