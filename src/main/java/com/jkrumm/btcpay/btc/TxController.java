package com.jkrumm.btcpay.btc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jkrumm.btcpay.service.dto.TransactionDTO;
import com.jkrumm.btcpay.web.rest.MerchantResource;
import java.io.IOException;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class TxController {

    private final Logger log = LoggerFactory.getLogger(TxController.class);

    private final ProfileService profileService;
    private final TxService txService;

    public TxController(ProfileService profileService, TxService txService) {
        this.profileService = profileService;
        this.txService = txService;
    }

    /**
     * {@code GET  /initTx} : get all the merchants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of merchants in body.
     */
    @GetMapping("/initTx/{amount}")
    public TransactionDTO initTx(Principal principal, @PathVariable Double amount) throws IOException {
        log.info("REST request to init transaction with amount: " + amount.toString());
        return txService.initTx(principal, amount);
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
}
