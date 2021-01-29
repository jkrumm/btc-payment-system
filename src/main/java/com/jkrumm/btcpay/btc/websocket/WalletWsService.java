package com.jkrumm.btcpay.btc.websocket;

import com.jkrumm.btcpay.btc.websocket.dto.WalletDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WalletWsService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final String WS_MESSAGE_TRANSFER_DESTINATION = "/topic/wallet";

    WalletWsService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendMessage(WalletDTO wallet) {
        simpMessagingTemplate.convertAndSend(WS_MESSAGE_TRANSFER_DESTINATION, wallet);
    }
}
