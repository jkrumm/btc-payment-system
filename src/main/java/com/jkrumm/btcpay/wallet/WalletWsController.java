package com.jkrumm.btcpay.wallet;

import com.jkrumm.btcpay.wallet.dto.GreetingDTO;
import com.jkrumm.btcpay.wallet.dto.TestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WalletWsController {

    private static final Logger log = LoggerFactory.getLogger(WalletWsController.class);

    @MessageMapping("/walletWs")
    @SendTo("/topic/wallet")
    public GreetingDTO greeting(TestDTO message) throws Exception {
        log.info("Registered new user for topic/greetings");
        return new GreetingDTO("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
