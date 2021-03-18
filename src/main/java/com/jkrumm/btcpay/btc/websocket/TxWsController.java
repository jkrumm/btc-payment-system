package com.jkrumm.btcpay.btc.websocket;

import com.jkrumm.btcpay.btc.BtcRepositoryContainer;
import com.jkrumm.btcpay.btc.TxService;
import com.jkrumm.btcpay.btc.WalletService;
import com.jkrumm.btcpay.btc.websocket.dto.ConfirmationDTO;
import com.jkrumm.btcpay.btc.websocket.dto.WalletDTO;
import com.jkrumm.btcpay.service.dto.TransactionDTO;
import com.jkrumm.btcpay.service.mapper.TransactionMapper;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.kits.WalletAppKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TxWsController {

    private static final Logger log = LoggerFactory.getLogger(TxWsController.class);

    @MessageMapping("/txWs")
    @SendTo("/topic/tx")
    public ConfirmationDTO connectTxtWs() {
        ConfirmationDTO confirmationDTO = new ConfirmationDTO("", null, null, null);
        log.info("Registered new listener for topic/tx / Current Tx: " + confirmationDTO.toString());
        return confirmationDTO;
    }
}
