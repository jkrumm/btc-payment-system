package com.jkrumm.btcpay.wallet;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import java.time.Instant;
import javax.annotation.PostConstruct;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class WalletService {

    private final Logger log = LoggerFactory.getLogger(WalletConfiguration.class);

    @Autowired
    private WalletAppKit walletAppKit;

    @Autowired
    private NetworkParameters networkParameters;

    @Autowired
    private WalletRepositoryContainer.Container repos;

    @PostConstruct
    public void start() {
        walletAppKit.startAsync();
        walletAppKit.awaitRunning();

        walletAppKit
            .wallet()
            .addCoinsReceivedEventListener(
                (wallet, tx, prevBalance, newBalance) -> {
                    Coin value = tx.getValueSentToMe(wallet);
                    System.out.println("Received tx for " + value.toFriendlyString());
                    Futures.addCallback(
                        tx.getConfidence().getDepthFuture(1),
                        new FutureCallback<TransactionConfidence>() {
                            @Override
                            public void onSuccess(TransactionConfidence result) {
                                System.out.println("Received tx " + value.toFriendlyString() + " is confirmed. ");
                            }

                            @Override
                            public void onFailure(Throwable t) {}
                        },
                        MoreExecutors.directExecutor()
                    );
                }
            );

        walletAppKit
            .chain()
            .addNewBestBlockListener(
                block -> {
                    long newBlockHeight = block.getHeight();
                    Instant newBlockMinedAt = Instant.now();
                    String newBlockHash = block.getHeader().getHashAsString();
                    long newAvailable = walletAppKit.wallet().getBalance(Wallet.BalanceType.AVAILABLE).longValue();
                    long newAvailableSpendable = walletAppKit.wallet().getBalance(Wallet.BalanceType.AVAILABLE_SPENDABLE).longValue();
                    long newEstimated = walletAppKit.wallet().getBalance(Wallet.BalanceType.ESTIMATED).longValue();
                    long newEstimatedSpendable = walletAppKit.wallet().getBalance(Wallet.BalanceType.ESTIMATED_SPENDABLE).longValue();

                    log.info("New Block Height: " + newBlockHeight + " | Hash: " + newBlockHash + " | Mined At: " + newBlockMinedAt);
                    log.info("Balance " + Wallet.BalanceType.AVAILABLE + " " + newAvailable);
                    log.info("Balance: " + Wallet.BalanceType.AVAILABLE_SPENDABLE + " " + newAvailableSpendable);
                    log.info("Balance: " + Wallet.BalanceType.ESTIMATED + " " + newEstimated);
                    log.info("Balance: " + Wallet.BalanceType.ESTIMATED_SPENDABLE + " " + newEstimatedSpendable);
                    /* Block newBlock = new Block();
                    newBlock.setMinedAt(newBlockMinedAt);
                    newBlock.setBlockHeight(newBlockHeight);
                    newBlock.setBlockHash(newBlockHash);
                    newBlock.setAvailable(newAvailable);
                    newBlock.setAvailableSpendable(newAvailableSpendable);
                    newBlock.setEstimated(newEstimated);
                    newBlock.setEstimatedSpendable(newEstimatedSpendable);

                    log.info(newBlock.toString());
                    Block newBlockSave = repos.block.save(newBlock);
                    log.info(newBlockSave.toString()); */
                }
            );

        Address sendToAddress = LegacyAddress.fromKey(networkParameters, walletAppKit.wallet().currentReceiveKey());
        System.out.println("Wallet address: " + sendToAddress);
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
}
