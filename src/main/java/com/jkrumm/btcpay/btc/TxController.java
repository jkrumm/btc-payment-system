package com.jkrumm.btcpay.btc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jkrumm.btcpay.btc.dto.Forward;
import com.jkrumm.btcpay.service.dto.TransactionDTO;
import com.jkrumm.btcpay.web.rest.MerchantResource;
import java.io.IOException;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class TxController {

    private final Logger log = LoggerFactory.getLogger(TxController.class);

    @Autowired
    private TxService txService;

    /**
     * {@code GET  /initTx} : get all the merchants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of merchants in body.
     */
    @GetMapping("/initTx")
    public TransactionDTO initTx(Principal principal, @RequestParam Double amount, @RequestParam String type) throws IOException {
        log.info("REST request to init transaction with amount: " + amount.toString() + " and type: " + type);
        return txService.initTx(principal, amount, type);
    }

    /**
     * {@code GET  /initTx} : get all the merchants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of merchants in body.
     */
    @GetMapping("/btcPrice")
    public Double btcPrice() throws IOException {
        log.info("REST request to get btc price");
        double price = txService.getBtcEuroPrice();
        log.info("get btc price : " + String.valueOf(price));
        return price;
    }

    @GetMapping("/forward")
    public Forward forward(Principal principal) throws IOException {
        log.debug("REST request to send user btc");
        return txService.send(principal);
    }
}
