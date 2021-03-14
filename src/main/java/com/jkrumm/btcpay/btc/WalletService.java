package com.jkrumm.btcpay.btc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import com.jkrumm.btcpay.btc.dto.StoredBlockDTO;
import com.jkrumm.btcpay.btc.websocket.TxWsService;
import com.jkrumm.btcpay.btc.websocket.WalletWsService;
import com.jkrumm.btcpay.btc.websocket.dto.ConfirmationDTO;
import com.jkrumm.btcpay.btc.websocket.dto.WalletDTO;
import com.jkrumm.btcpay.btc.websocket.dto.blockcypher.BlockCypherCompactDTO;
import com.jkrumm.btcpay.btc.websocket.dto.blockcypher.BlockCypherDTO;
import com.jkrumm.btcpay.domain.Confidence;
import com.jkrumm.btcpay.domain.Transaction;
import com.jkrumm.btcpay.domain.enumeration.ConfidenceType;
import com.jkrumm.btcpay.service.dto.ConfidenceDTO;
import com.jkrumm.btcpay.service.dto.TransactionDTO;
import com.jkrumm.btcpay.service.mapper.ConfidenceMapper;
import com.jkrumm.btcpay.service.mapper.TransactionMapper;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.script.ScriptException;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.h2.engine.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WalletService {

    private final Logger log = LoggerFactory.getLogger(WalletService.class);

    @Autowired
    private WalletAppKit walletAppKit;

    @Autowired
    private NetworkParameters networkParameters;

    @Autowired
    private BtcRepositoryContainer.Container repos;

    @Autowired
    private WalletWsService walletWsService;

    @Autowired
    private TxWsService txWsService;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private ConfidenceMapper confidenceMapper;

    @PostConstruct
    public void start() {
        walletAppKit.startAsync();
        walletAppKit.awaitRunning();

        walletAppKit
            .wallet()
            .addCoinsReceivedEventListener(
                (wallet, tx, prevBalance, newBalance) -> {
                    log.info("Triggered: addCoinsReceivedEventListener");
                    Coin value = tx.getValueSentToMe(wallet);
                    log.info("Received tx for " + value.toFriendlyString());
                    for (TransactionOutput output : tx.getOutputs()) {
                        if (output.isMine(wallet)) {
                            Script script = output.getScriptPubKey();
                            Address address = script.getToAddress(walletAppKit.wallet().getNetworkParameters(), true);
                            log.info("Received tx for address " + address.toString());
                            Transaction transaction = repos.transaction.findByAddress(address.toString()).get(0);
                            transaction.setTxHash(tx.getTxId().toString());
                            if (tx.getFee() != null) {
                                log.info("tx.getFee().getValue() " + tx.getFee().getValue());
                                transaction.setTransactionFee(tx.getFee().getValue());
                            }
                            log.error("Fee null");
                            log.info("tx.getValue(wallet).getValue() " + tx.getValue(wallet).getValue());
                            transaction.setActualAmount(tx.getValue(wallet).getValue());
                            log.info("Updated Tx: " + transaction.toString());
                            Confidence confidence = new Confidence();
                            confidence.setChangeAt(Instant.now());
                            confidence.setConfidenceType(ConfidenceType.BUILDING);
                            confidence.setConfirmations(0);
                            confidence.setTransaction(transaction);
                            confidence = repos.confidence.save(confidence);
                            log.info("Saved confidence: " + confidence.toString());
                            transaction.addConfidence(confidence);
                            transaction = repos.transaction.save(transaction);
                            log.info("Saved Tx: " + transaction.toString());
                            ConfirmationDTO confirmationDTO = new ConfirmationDTO(
                                address.toString(),
                                confidenceMapper.toDto(confidence),
                                transactionMapper.toDto(transaction),
                                null
                            );
                            log.info("confirmationDTO : " + confirmationDTO.toString());
                            txWsService.sendMessage(confirmationDTO);
                        } else {
                            log.info("Received tx NOT for this wallet!");
                        }
                    }
                    Futures.addCallback(
                        tx.getConfidence().getDepthFuture(1),
                        new FutureCallback<TransactionConfidence>() {
                            @Override
                            public void onSuccess(TransactionConfidence result) {
                                System.out.println("Received tx " + tx.getTxId() + " is confirmed.");
                                /*
                                for (TransactionOutput output : tx.getOutputs()) {
                                    if (output.isMine(wallet)) {
                                        Script script = output.getScriptPubKey();
                                        Address address = script.getToAddress(walletAppKit.wallet().getNetworkParameters(), true);
                                        log.info("Confirmed tx by blockchain for address " + address.toString());
                                        Transaction txDb = repos.transaction.findByAddress(address.toString()).get(0);
                                        Confidence confidence = new Confidence();
                                        confidence.setChangeAt(Instant.now());
                                        confidence.setConfidenceType(ConfidenceType.CONFIRMED);
                                        confidence.setConfirmations(1);
                                        txDb.addConfidence(confidence);
                                        log.info("Saved Tx: " + txDb.toString());
                                        txWsService.sendMessage(transactionMapper.toDto(txDb));
                                    } else {
                                        log.error("Received tx NOT for this wallet!");
                                    }
                                }*/
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                System.out.println("Received tx " + tx.getTxId() + " failed.");

                                // Persist updated Transaction and Confirmation to db
                                Transaction txDb = repos.transaction.findTopByTxHash(tx.getTxId().toString());
                                Confidence confidence = new Confidence();
                                confidence.setChangeAt(Instant.now());
                                confidence.setConfidenceType(ConfidenceType.DEAD);
                                confidence.setConfirmations(tx.getConfidence().getDepthInBlocks());
                                confidence.setTransaction(txDb);
                                confidence = repos.confidence.save(confidence);
                                log.info("Saved confidence: " + confidence.toString());
                                txDb.addConfidence(confidence);
                                log.info("Saved Tx: " + txDb.toString());

                                // Update Frontend using WebSocket
                                ConfirmationDTO confirmationDTO = new ConfirmationDTO(
                                    txDb.getAddress(),
                                    confidenceMapper.toDto(confidence),
                                    transactionMapper.toDto(txDb),
                                    null
                                );
                                log.info("confirmationDTO : " + confirmationDTO.toString());
                                txWsService.sendMessage(confirmationDTO);
                            }
                        },
                        MoreExecutors.directExecutor()
                    );
                }
            );

        walletAppKit
            .wallet()
            .addTransactionConfidenceEventListener(
                (
                    (wallet, tx) -> {
                        int depth = tx.getConfidence().getDepthInBlocks();
                        if (depth > 0 && depth <= 6) {
                            log.info("TransactionConfidenceEventListener : " + tx.getTxId() + " : " + depth);

                            // Persist updated Transaction and Confirmation to db
                            Transaction txDb = repos.transaction.findTopByTxHash(tx.getTxId().toString());
                            Confidence confidence = new Confidence();
                            confidence.setChangeAt(Instant.now());
                            confidence.setConfidenceType(ConfidenceType.CONFIRMED);
                            confidence.setConfirmations(depth);
                            confidence.setTransaction(txDb);
                            confidence = repos.confidence.save(confidence);
                            log.info("Saved confidence: " + confidence.toString());
                            txDb.addConfidence(confidence);
                            log.info("Saved Tx: " + txDb.toString());

                            // Update Frontend using WebSocket
                            ConfirmationDTO confirmationDTO = new ConfirmationDTO(
                                txDb.getAddress(),
                                confidenceMapper.toDto(confidence),
                                transactionMapper.toDto(txDb),
                                null
                            );
                            log.info("confirmationDTO : " + confirmationDTO.toString());
                            txWsService.sendMessage(confirmationDTO);
                        } else if (depth > 6) {
                            log.info("TransactionConfidenceEventListener 6 : " + tx.getTxId() + " : " + depth);
                            log.info("TransactionConfidenceEventListener 6 : " + tx.getConfidence().getConfidenceType());

                            // Persist updated Transaction and Confirmation to db
                            Transaction txDb = repos.transaction.findTopByTxHash(tx.getTxId().toString());
                            Confidence conf = repos.confidence.findFirstByTransactionOrderByChangeAt(txDb);
                            if (conf.getConfirmations() != 6) {
                                Confidence confidence = new Confidence();
                                confidence.setChangeAt(Instant.now());
                                confidence.setConfidenceType(ConfidenceType.CONFIRMED);
                                confidence.setConfirmations(6);
                                confidence.setTransaction(txDb);
                                confidence = repos.confidence.save(confidence);
                                log.info("Saved confidence: " + confidence.toString());
                                txDb.addConfidence(confidence);
                                log.info("Saved Tx: " + txDb.toString());
                            }
                        }
                    }
                )
            );

        walletAppKit
            .wallet()
            .addChangeEventListener(
                (
                    wallet -> {
                        log.info("Triggered: addChangeEventListener");
                        WalletDTO walletDTO = getWalletDTO();
                        log.info(
                            "walletChangeEventListener : " + wallet.getLastBlockSeenHeight() + " Balance : " + walletDTO.getAvailable()
                        );
                        for (org.bitcoinj.core.Transaction tx : wallet.getPendingTransactions()) {
                            log.info("numBroadcastPeers() " + tx.getConfidence().numBroadcastPeers());
                            if (tx.getConfidence().numBroadcastPeers() == 10) {
                                for (TransactionOutput output : tx.getOutputs()) {
                                    if (output.isMine(wallet)) {
                                        // Get Transaction from db
                                        Script script = output.getScriptPubKey();
                                        Address address = script.getToAddress(walletAppKit.wallet().getNetworkParameters(), true);
                                        log.info("Confirmed tx by peers for address " + address.toString());
                                        Transaction txDb = repos.transaction.findByAddress(address.toString()).get(0);

                                        // Persist updated Transaction and Confirmation to db
                                        Confidence confidence = new Confidence();
                                        confidence.setChangeAt(Instant.now());
                                        confidence.setConfidenceType(ConfidenceType.CONFIRMED);
                                        confidence.setConfirmations(0);
                                        confidence.setTransaction(txDb);
                                        confidence = repos.confidence.save(confidence);
                                        log.info("Saved confidence: " + confidence.toString());
                                        txDb.addConfidence(confidence);
                                        log.info("Saved Tx: " + txDb.toString());

                                        // Fetch BlockCypher validation
                                        ObjectMapper objectMapper = new ObjectMapper();
                                        try {
                                            BlockCypherDTO bc = objectMapper.readValue(
                                                new URL(
                                                    "https://api.blockcypher.com/v1/btc/test3/txs/" +
                                                    txDb.getTxHash() +
                                                    "?includeConfidence=true?token=4309e288604540068f4395ae1a54a907"
                                                ),
                                                BlockCypherDTO.class
                                            );

                                            BlockCypherCompactDTO blockCypherCompactDTO = new BlockCypherCompactDTO(
                                                bc.getTotal(),
                                                bc.getFees(),
                                                bc.getSize(),
                                                bc.getVsize(),
                                                bc.getPreference(),
                                                bc.getDoubleSpend(),
                                                bc.getConfirmations(),
                                                bc.getConfidence()
                                            );
                                            log.info("blockCypherDTO: " + blockCypherCompactDTO.toString());

                                            // Update Frontend using WebSocket
                                            ConfirmationDTO confirmationDTO = new ConfirmationDTO(
                                                address.toString(),
                                                confidenceMapper.toDto(confidence),
                                                transactionMapper.toDto(txDb),
                                                blockCypherCompactDTO
                                            );
                                            log.info("confirmationDTO : " + confirmationDTO.toString());
                                            txWsService.sendMessage(confirmationDTO);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        log.info("Received tx NOT for this wallet!");
                                    }
                                }
                            }
                        }
                        walletWsService.sendMessage(walletDTO);
                    }
                )
            );

        walletAppKit
            .chain()
            .addNewBestBlockListener(
                block -> {
                    log.info("Triggered: addNewBestBlockListener");
                    WalletDTO walletDTO = getWalletDTO();
                    log.info("NewBestBlockListener : " + walletDTO.getBlockHeight());
                    log.info("NewBestBlockListener : " + walletDTO.toString());
                    walletWsService.sendMessage(getWalletDTO());
                }
            );

        Address sendToAddress = LegacyAddress.fromKey(networkParameters, walletAppKit.wallet().currentReceiveKey());
        System.out.println("Wallet address: " + sendToAddress);
    }

    public Address newAddress() {
        return walletAppKit.wallet().freshReceiveAddress();
    }

    public void send(String value, String to) {
        try {
            Address toAddress = LegacyAddress.fromBase58(networkParameters, to);
            SendRequest sendRequest = SendRequest.to(toAddress, Coin.parseCoin(value));
            sendRequest.feePerKb = Coin.parseCoin("0.0005");
            Wallet.SendResult sendResult = walletAppKit.wallet().sendCoins(walletAppKit.peerGroup(), sendRequest);
            sendResult.broadcastComplete.addListener(
                () -> System.out.println("Sent coins onwards! Transaction hash is " + sendResult.tx.getTxId()),
                MoreExecutors.directExecutor()
            );
        } catch (InsufficientMoneyException e) {
            throw new RuntimeException(e);
        }
    }

    float getBalance() {
        return walletAppKit.wallet().getBalance().getValue();
    }

    String getAddress() {
        return LegacyAddress.fromKey(networkParameters, walletAppKit.wallet().currentReceiveKey()).toString();
    }

    StoredBlock getCurrentBlock() {
        return walletAppKit.chain().getChainHead();
    }

    public WalletDTO getWalletDTO() {
        Wallet wallet = walletAppKit.wallet();
        WalletDTO walletDto = new WalletDTO(
            wallet.getLastBlockSeenHeight(),
            Objects.requireNonNull(wallet.getLastBlockSeenTime()).toInstant(),
            wallet.getBalance(Wallet.BalanceType.AVAILABLE).longValue(),
            wallet.getBalance(Wallet.BalanceType.AVAILABLE_SPENDABLE).longValue(),
            wallet.getBalance(Wallet.BalanceType.ESTIMATED).longValue(),
            wallet.getBalance(Wallet.BalanceType.ESTIMATED_SPENDABLE).longValue(),
            wallet.getPendingTransactions().size(),
            wallet.getUnspents().size()
        );
        log.info("getWalletDTO : " + walletDto.getBlockHeight());
        return walletDto;
    }
}
