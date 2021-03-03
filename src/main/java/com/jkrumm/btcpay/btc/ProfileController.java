package com.jkrumm.btcpay.btc;

import com.jkrumm.btcpay.domain.Merchant;
import com.jkrumm.btcpay.service.dto.MerchantDTO;
import com.jkrumm.btcpay.web.rest.MerchantResource;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class ProfileController {

    private final Logger log = LoggerFactory.getLogger(MerchantResource.class);

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
        log.debug("REST request to get all Merchants");
        return profileService.getMerchant(principal);
    }
}
