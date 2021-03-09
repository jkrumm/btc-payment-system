package com.jkrumm.btcpay.btc.websocket;

import com.jkrumm.btcpay.btc.WalletConfiguration;
import com.jkrumm.btcpay.btc.websocket.dto.ConfirmationDTO;
import com.jkrumm.btcpay.service.dto.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class TxWsService {

    private final Logger log = LoggerFactory.getLogger(TxWsService.class);
    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final String WS_MESSAGE_TRANSFER_DESTINATION = "/topic/tx";

    TxWsService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendMessage(ConfirmationDTO confirmationDTO) {
        log.info("TxWsService sendMessage(): " + confirmationDTO.toString());
        simpMessagingTemplate.convertAndSend(WS_MESSAGE_TRANSFER_DESTINATION, confirmationDTO);
    }
}
