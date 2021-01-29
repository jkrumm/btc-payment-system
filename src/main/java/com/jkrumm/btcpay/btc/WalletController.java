package com.jkrumm.btcpay.btc;

import java.net.URISyntaxException;
import org.bitcoinj.core.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WalletController {

    private final Logger log = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private WalletService walletService;

    @RequestMapping
    public String index() {
        return "Greetings from Spring Boot!";
    }

    /**
     * {@code POST  /transactions} : Create a new transaction.
     *
     * @param amount amount of btc
     * @param address address to send btc to
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with amount and address in body, or with status {@code 400 (Bad Request)} if the transaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestParam String amount, @RequestParam String address) throws URISyntaxException {
        log.debug("REST request to send btc");
        walletService.send(amount, address);
        return ResponseEntity.ok().body("Sent " + amount + " BTC to " + address);
    }

    /**
     * {@code GET  /balance} : get wallet balance
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the wallet balance in body.
     */
    @GetMapping("/balance")
    public ResponseEntity<Float> balance() {
        log.debug("REST request to get wallet balance");
        return ResponseEntity.ok().body(walletService.getBalance());
    }

    /**
     * {@code GET  /address} : get wallet address
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the wallet address in body.
     */
    @GetMapping("/address")
    public ResponseEntity<String> address() {
        log.debug("REST request to get wallet address");
        return ResponseEntity.ok().body(walletService.getAddress());
    }

    /**
     * {@code GET  /address} : get current Block
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the current block in body.
     */
    @GetMapping("/currentBlock")
    public ResponseEntity<Block> currentBlock() {
        log.debug("REST request to get current block");
        return ResponseEntity.ok().body(walletService.getCurrentBlock().getHeader());
    }
}
