package com.jkrumm.btcpay.btc.websocket;

import com.jkrumm.btcpay.btc.WalletService;
import com.jkrumm.btcpay.btc.websocket.dto.WalletDTO;
import org.bitcoinj.kits.WalletAppKit;
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

    @Autowired
    private WalletService walletService;

    @MessageMapping("/walletWs")
    @SendTo("/topic/wallet")
    public WalletDTO connectWalletWs() {
        WalletDTO walletDTO = walletService.getWalletDTO();
        log.info("Registered new listener for topic/wallet / Current Block: " + walletDTO.getBlockHeight());
        return walletDTO;
    }
}
