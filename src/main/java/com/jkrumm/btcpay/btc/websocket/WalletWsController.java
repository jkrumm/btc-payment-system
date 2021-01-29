package com.jkrumm.btcpay.btc.websocket;

import com.jkrumm.btcpay.btc.websocket.dto.WalletDTO;
import java.util.Objects;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WalletWsController {

    private static final Logger log = LoggerFactory.getLogger(WalletWsController.class);

    @Autowired
    private WalletAppKit walletAppKit;

    @MessageMapping("/walletWs")
    @SendTo("/topic/wallet")
    public WalletDTO connectWalletWs() {
        Wallet wallet = walletAppKit.wallet();
        WalletDTO walletDTO = new WalletDTO(
            wallet.getLastBlockSeenHeight(),
            Objects.requireNonNull(wallet.getLastBlockSeenTime()).toInstant(),
            wallet.getBalance(Wallet.BalanceType.AVAILABLE).longValue(),
            wallet.getBalance(Wallet.BalanceType.AVAILABLE_SPENDABLE).longValue(),
            wallet.getBalance(Wallet.BalanceType.ESTIMATED).longValue(),
            wallet.getBalance(Wallet.BalanceType.ESTIMATED_SPENDABLE).longValue(),
            wallet.getPendingTransactions().size(),
            wallet.getUnspents().size()
        );
        log.info("Registered new listener for topic/wallet / Current Block: " + wallet.getLastBlockSeenHeight());
        return walletDTO;
    }
}
