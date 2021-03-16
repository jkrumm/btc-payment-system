package com.jkrumm.btcpay.btc;

import com.jkrumm.btcpay.btc.dto.*;
import com.jkrumm.btcpay.domain.*;
import com.jkrumm.btcpay.domain.enumeration.ConfidenceType;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private BtcRepositoryContainer.Container repos;

    @Autowired
    private TxService txService;

    @Autowired
    private WalletService WalletService;

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

    List<Transaction> getMerchantTransactions(Principal principal) {
        log.info("Called getMerchantTransactions() in ProfileService");
        Optional<User> user = repos.user.findOneByLogin(principal.getName());
        log.info(user.toString());
        if (user.isPresent()) {
            Merchant merchant = repos.merchantUser.findMerchantUserByUser(user).getMerchant();
            List<MerchantUser> merchantUsers = repos.merchantUser.findByMerchant(merchant);
            List<User> users = new ArrayList<>();
            for (MerchantUser mu : merchantUsers) {
                users.add(mu.getUser());
            }
            log.info("getMerchantTransactions(): " + repos.transaction.findByUserIsIn(users).toString());
            return repos.transaction.findByUserIsIn(users);
        }
        return null;
    }

    MerchantWallet getWallet(Principal principal) throws IOException {
        log.info("Called getWallet() in ProfileService");
        List<Transaction> transactions = getMerchantTransactions(principal);
        MerchantWallet merchantWallet = new MerchantWallet();
        for (Transaction tx : transactions) {
            if (tx.getActualAmount() != null) {
                List<Confidence> confidences = repos.confidence.findByTransactionOrderByChangeAtDesc(tx);
                if (confidences.size() > 0 && confidences.get(0).getConfirmations() > 0) {
                    merchantWallet.setEstimated(merchantWallet.getEstimated() + tx.getActualAmount() - tx.getServiceFee());
                    merchantWallet.setSpendable(merchantWallet.getSpendable() + tx.getActualAmount() - tx.getServiceFee());
                    merchantWallet.setServiceFee(merchantWallet.getServiceFee() + tx.getServiceFee());
                } else if (confidences.size() > 0 && confidences.get(0).getConfidenceType() == ConfidenceType.BUILDING) {
                    merchantWallet.setEstimated(merchantWallet.getEstimated() + tx.getActualAmount() - tx.getServiceFee());
                    merchantWallet.setServiceFee(merchantWallet.getServiceFee() + tx.getServiceFee() - tx.getServiceFee());
                }
            }
        }
        Double btcUsd = txService.getBtcEuroPrice();
        merchantWallet.setEstimatedUsd(Math.round(((merchantWallet.getEstimated().doubleValue() / 100000000) * btcUsd) * 100) / 100.0);
        merchantWallet.setSpendableUsd(Math.round(((merchantWallet.getSpendable().doubleValue() / 100000000) * btcUsd) * 100) / 100.0);
        merchantWallet.setServiceFeeUsd(Math.round(((merchantWallet.getServiceFee().doubleValue() / 100000000) * btcUsd) * 100) / 100.0);
        log.info("getWallet(): " + merchantWallet.toString());
        return merchantWallet;
    }

    List<TransactionHistory> getTransactions(Principal principal) {
        log.info("Called getTransactions() in ProfileService");
        List<Transaction> transactions = getMerchantTransactions(principal);
        List<TransactionHistory> transactionHistories = new ArrayList<>();
        for (Transaction tx : transactions) {
            if (tx.getActualAmount() != null) {
                List<Confidence> confidences = repos.confidence.findByTransactionOrderByChangeAtDesc(tx);
                List<TransactionHistoryConfidence> transactionHistoryConfidences = new ArrayList<>();
                ConfidenceType confidenceType = ConfidenceType.UNKNOWN;
                Integer confirmations = 0;
                if (confidences.size() > 0) {
                    confidenceType = confidences.get(0).getConfidenceType();
                    confirmations = confidences.get(0).getConfirmations();
                    for (Confidence conf : confidences) {
                        transactionHistoryConfidences.add(
                            new TransactionHistoryConfidence(
                                conf.getId(),
                                conf.getChangeAt(),
                                conf.getConfidenceType(),
                                conf.getConfirmations()
                            )
                        );
                    }
                }
                String timeAgoString = TimeAgo.toRelative(Date.from(tx.getInitiatedAt()), new Date());
                transactionHistories.add(
                    new TransactionHistory(
                        tx.getId(),
                        tx.getUser().getFirstName() + " " + tx.getUser().getLastName(),
                        tx.getAddress(),
                        tx.getInitiatedAt(),
                        tx.getTransactionType(),
                        tx.getTxHash(),
                        tx.getExpectedAmount(),
                        tx.getActualAmount(),
                        tx.getTransactionFee(),
                        tx.getServiceFee(),
                        tx.getBtcUsd(),
                        tx.getAmount(),
                        transactionHistoryConfidences,
                        timeAgoString,
                        confidenceType,
                        confirmations
                    )
                );
            }
        }
        Collections.reverse(transactionHistories);
        log.info("getTransactions(): " + transactionHistories.size());
        return transactionHistories;
    }

    Forward send(Principal principal) throws IOException {
        Merchant merchant = getMerchant(principal);
        String spendable = getWallet(principal).getSpendable().toString();
        log.info(
            "Merchant: " +
            merchant.getName() +
            " | User: " +
            principal.getName() +
            " | Forward " +
            spendable +
            " to " +
            merchant.getForward()
        );
        Wallet.SendResult sendResult = WalletService.send(spendable, merchant.getForward());
        Forward forward = new Forward(
            Long.valueOf(spendable),
            merchant.getForward(),
            sendResult.tx.getTxId().toString(),
            sendResult.tx.getFee().getValue()
        );
        log.info(forward.toString());
        return forward;
    }
}
