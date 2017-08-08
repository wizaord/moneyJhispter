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

        return compteBancaireRepository.getAllByProprietaireAndIsDeletedFalse(userId.intValue()).stream()
            .map((compte) -> {
                DebitCredit lastDB = debitCreditRepository.getFirstByComptebancaireByCompteRattacheOrderByDateEnregistrement(compte);
                AccountDetailDTO addto = new AccountDetailDTO(compte);
                if (lastDB != null) {
                    addto.setLastTransaction(lastDB.getDateEnregistrement());
                }
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
    public Optional<CompteBancaire> isAccountOwner(final Long userId, final Long accountId) throws NotAllowedException {
        //if accountId is null, return null
        log.debug("Checking that user {} is the owner of account {}", userId, accountId);
        if (accountId == null) {
            return Optional.ofNullable(null);
        }
        Optional<CompteBancaire> cb = Optional.ofNullable(compteBancaireRepository.findOne(accountId));
        if (cb.isPresent()) {
            if (cb.get().getProprietaire().intValue() != userId) {
                log.warn(" user {} is not the owner of account {}", userId, accountId);
                throw new NotAllowedException("You are not allowed to delete this account");
            } else {
                log.debug("User {} is the owner of the account {}", userId, accountId);
            }
        } else {
            log.debug("Account with id {} has not been found ! ", accountId);
        }
        return cb;
    }

    /**
     * Check that the userid is the owner of all accountIds passed in parameter
     * @param userId
     * @param accountIds
     * @return
     */
    @Transactional(readOnly = true)
    public boolean isAccountsOwner(final Long userId, final List<Long> accountIds) {
        if (accountIds != null) {
            for(Long accountId : accountIds) {
                try {
                    Optional<CompteBancaire> accountOwner = isAccountOwner(userId, accountId);
                    if(!accountOwner.isPresent()) {
                        return false;
                    }
                } catch (NotAllowedException e) {
                   return false;
                }

            }
        }
        return true;
    }

    /**
     * Delete the account from the user
     * If the account does not exist or if the user is not the account owner, an exception is raised
     */
    public void deleteAccount(final Long userId, final Long accountId) throws NotAllowedException {
        isAccountOwner(userId, accountId).ifPresent((account) -> {
            log.info("Mark accountId {} has deleted", accountId);
            account.setIsDeleted(true);
            compteBancaireRepository.save(account);
        });
    }

    /**
     * Change the isClos status of an account. Check that the user is the owner of the account
     * @param userId : the userId
     * @param accountId : the accountId
     * @param isClos : true or false to open or close the account
     * @throws NotAllowedException
     */
    public void changeAccountStatus(final Long userId, final Long accountId, final boolean isClos) throws NotAllowedException {
        isAccountOwner(userId, accountId).ifPresent((account) -> {
            log.info("Mark accountId {} with status isClos {}", accountId, isClos);
            account.setIsClos(isClos);
            compteBancaireRepository.save(account);
        });
    }
}
