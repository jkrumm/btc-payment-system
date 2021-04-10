package com.jkrumm.btcpay.btc;

import com.jkrumm.btcpay.btc.dto.Forward;
import com.jkrumm.btcpay.btc.dto.MerchantWallet;
import com.jkrumm.btcpay.btc.dto.TransactionHistory;
import com.jkrumm.btcpay.domain.Merchant;
import com.jkrumm.btcpay.domain.Transaction;
import com.jkrumm.btcpay.service.dto.MerchantDTO;
import com.jkrumm.btcpay.web.rest.MerchantResource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class ProfileController {

    private final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * {@code GET  /merchants} : get all the merchants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of merchants in body.
     */
    @GetMapping("/merchant")
    public Merchant getMerchant(Principal principal) {
        log.debug("REST request to get merchant");
        return profileService.getMerchant(principal);
    }

    @GetMapping("/wallet")
    public MerchantWallet getWallet(Principal principal) throws IOException {
        log.debug("REST request to get merchant wallet");
        return profileService.getWallet(principal);
    }

    @GetMapping("/transactions")
    public List<TransactionHistory> getTransactions(Principal principal) {
        log.debug("REST request to get all merchant transactions");
        return profileService.getTransactions(principal);
    }
}
