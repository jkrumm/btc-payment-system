package com.jkrumm.btcpay.btc;

import com.jkrumm.btcpay.domain.Merchant;
import com.jkrumm.btcpay.domain.MerchantUser;
import com.jkrumm.btcpay.domain.Transaction;
import com.jkrumm.btcpay.domain.User;
import com.jkrumm.btcpay.service.dto.MerchantDTO;
import com.jkrumm.btcpay.service.dto.MerchantUserDTO;
import com.jkrumm.btcpay.service.mapper.MerchantMapper;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private BtcRepositoryContainer.Container repos;

    Merchant getMerchant(Principal principal) {
        log.info("Called getMerchant() in ProfileService");
        Optional<User> user = repos.user.findOneByLogin(principal.getName());
        log.info(user.toString());
        if (user.isPresent()) {
            Merchant merchant = repos.merchantUser.findMerchantUserByUser(user).getMerchant();
            log.info(merchant.toString());
            return merchant;
        } else {
            throw new NullPointerException();
        }
    }

    List<Transaction> getTransactions(Principal principal) {
        log.info("Called getTransactions() in ProfileService");
        Optional<User> user = repos.user.findOneByLogin(principal.getName());
        log.info(user.toString());
        if (user.isPresent()) {
            List<Transaction> transactions = repos.transaction.findByUserIsCurrentUserOrderByInitiatedAtDesc();
            log.info("getTransactions(): " + transactions.size());
            return transactions;
        } else {
            throw new NullPointerException();
        }
    }
}
