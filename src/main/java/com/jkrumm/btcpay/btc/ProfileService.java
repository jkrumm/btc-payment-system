package com.jkrumm.btcpay.btc;

import com.jkrumm.btcpay.btc.dto.*;
import com.jkrumm.btcpay.domain.*;
import com.jkrumm.btcpay.domain.enumeration.ConfidenceType;
import com.jkrumm.btcpay.domain.enumeration.TransactionType;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
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

    User getUser(Principal principal) {
        log.info("Called getUser() in ProfileService");
        Optional<User> user = repos.user.findOneByLogin(principal.getName());
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NullPointerException();
        }
    }

    Merchant getMerchant(Principal principal) {
        log.info("Called getMerchant() in ProfileService");
        return repos.merchantUser.findMerchantUserByUser(getUser(principal)).getMerchant();
    }

    List<Transaction> getMerchantTransactions(Principal principal) {
        log.info("Called getMerchantTransactions() in ProfileService");
        Merchant merchant = repos.merchantUser.findMerchantUserByUser(getUser(principal)).getMerchant();
        List<MerchantUser> merchantUsers = repos.merchantUser.findByMerchant(merchant);
        List<User> users = new ArrayList<>();
        for (MerchantUser mu : merchantUsers) {
            users.add(mu.getUser());
        }
        log.info("getMerchantTransactions(): " + repos.transaction.findByUserIsIn(users).toString());
        return repos.transaction.findByUserIsIn(users);
    }

    MerchantWallet getWallet(Principal principal) throws IOException {
        log.info("Called getWallet() in ProfileService");
        List<Transaction> transactions = getMerchantTransactions(principal);
        MerchantWallet merchantWallet = new MerchantWallet();
        for (Transaction tx : transactions) {
            if (tx.getActualAmount() != null) {
                List<Confidence> confidences = repos.confidence.findByTransactionOrderByChangeAtDesc(tx);
                if (tx.getTransactionType().equals(TransactionType.FORWARD_MERCHANT)) {
                    merchantWallet.setForward(merchantWallet.getForward() + tx.getActualAmount());
                } else if (confidences.size() > 0 && confidences.get(0).getConfirmations() > 0) {
                    merchantWallet.setEstimated(merchantWallet.getEstimated() + tx.getActualAmount() - tx.getServiceFee());
                    merchantWallet.setSpendable(merchantWallet.getSpendable() + tx.getActualAmount() - tx.getServiceFee());
                    merchantWallet.setServiceFee(merchantWallet.getServiceFee() + tx.getServiceFee());
                } else if (confidences.size() > 0 && confidences.get(0).getConfidenceType() == ConfidenceType.BUILDING) {
                    merchantWallet.setEstimated(merchantWallet.getEstimated() + tx.getActualAmount() - tx.getServiceFee());
                    merchantWallet.setServiceFee(merchantWallet.getServiceFee() + tx.getServiceFee() - tx.getServiceFee());
                }
            }
        }
        merchantWallet.setTotal(merchantWallet.getEstimated());
        merchantWallet.setEstimated(merchantWallet.getEstimated() - merchantWallet.getForward());
        merchantWallet.setSpendable(merchantWallet.getSpendable() - merchantWallet.getForward());
        merchantWallet.setTotalUsd(txService.getBtcToEuro(merchantWallet.getTotal()));
        merchantWallet.setForwardUsd(txService.getBtcToEuro(merchantWallet.getForward()));
        merchantWallet.setEstimatedUsd(txService.getBtcToEuro(merchantWallet.getEstimated()));
        merchantWallet.setSpendableUsd(txService.getBtcToEuro(merchantWallet.getSpendable()));
        merchantWallet.setServiceFeeUsd(txService.getBtcToEuro(merchantWallet.getServiceFee()));
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
                        tx.getBtcEuro(),
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
        Wallet.SendResult sendResult = WalletService.sendMerchant(getUser(principal), spendable, merchant.getForward());
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
