package com.jkrumm.btcpay.btc;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import com.jkrumm.btcpay.btc.websocket.WalletWsService;
import com.jkrumm.btcpay.btc.websocket.dto.WalletDTO;
import java.util.Objects;
import javax.annotation.PostConstruct;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WalletService {

    private final Logger log = LoggerFactory.getLogger(WalletConfiguration.class);

    @Autowired
    private WalletAppKit walletAppKit;

    @Autowired
    private NetworkParameters networkParameters;

    @Autowired
    private BtcRepositoryContainer.Container repos;

    @Autowired
    private WalletWsService walletWsService;

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
                                System.out.println("Received tx " + value.toFriendlyString() + " is confirmed.");
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                System.out.println("Received tx " + value.toFriendlyString() + " failed.");
                            }
                        },
                        MoreExecutors.directExecutor()
                    );
                }
            );

        walletAppKit
            .wallet()
            .addChangeEventListener(
                (
                    wallet -> {
                        WalletDTO walletDTO = getWalletDTO();
                        log.info(
                            "walletChangeEventListener : " + wallet.getLastBlockSeenHeight() + " Balance : " + walletDTO.getAvailable()
                        );
                        log.info(wallet.toString());
                        walletWsService.sendMessage(walletDTO);
                    }
                )
            );

        walletAppKit
            .chain()
            .addNewBestBlockListener(
                block -> {
                    WalletDTO walletDTO = getWalletDTO();
                    log.info("NewBestBlockListener : " + walletDTO.getBlockHeight());
                    log.info(walletDTO.toString());
                    walletWsService.sendMessage(getWalletDTO());
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
