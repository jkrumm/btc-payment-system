package com.jkrumm.btcpay.web.rest;

import com.jkrumm.btcpay.service.MerchantService;
import com.jkrumm.btcpay.service.dto.MerchantDTO;
import com.jkrumm.btcpay.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.jkrumm.btcpay.domain.Merchant}.
 */
@RestController
@RequestMapping("/api")
public class MerchantResource {
    private final Logger log = LoggerFactory.getLogger(MerchantResource.class);

    private static final String ENTITY_NAME = "merchant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MerchantService merchantService;

    public MerchantResource(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    /**
     * {@code POST  /merchants} : Create a new merchant.
     *
     * @param merchantDTO the merchantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new merchantDTO, or with status {@code 400 (Bad Request)} if the merchant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/merchants")
    public ResponseEntity<MerchantDTO> createMerchant(@Valid @RequestBody MerchantDTO merchantDTO) throws URISyntaxException {
        log.debug("REST request to save Merchant : {}", merchantDTO);
        if (merchantDTO.getId() != null) {
            throw new BadRequestAlertException("A new merchant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MerchantDTO result = merchantService.save(merchantDTO);
        return ResponseEntity
            .created(new URI("/api/merchants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /merchants} : Updates an existing merchant.
     *
     * @param merchantDTO the merchantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated merchantDTO,
     * or with status {@code 400 (Bad Request)} if the merchantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the merchantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/merchants")
    public ResponseEntity<MerchantDTO> updateMerchant(@Valid @RequestBody MerchantDTO merchantDTO) throws URISyntaxException {
        log.debug("REST request to update Merchant : {}", merchantDTO);
        if (merchantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MerchantDTO result = merchantService.save(merchantDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, merchantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /merchants} : get all the merchants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of merchants in body.
     */
    @GetMapping("/merchants")
    public ResponseEntity<List<MerchantDTO>> getAllMerchants(Pageable pageable) {
        log.debug("REST request to get a page of Merchants");
        Page<MerchantDTO> page = merchantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /merchants/:id} : get the "id" merchant.
     *
     * @param id the id of the merchantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the merchantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/merchants/{id}")
    public ResponseEntity<MerchantDTO> getMerchant(@PathVariable Long id) {
        log.debug("REST request to get Merchant : {}", id);
        Optional<MerchantDTO> merchantDTO = merchantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(merchantDTO);
    }

    /**
     * {@code DELETE  /merchants/:id} : delete the "id" merchant.
     *
     * @param id the id of the merchantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/merchants/{id}")
    public ResponseEntity<Void> deleteMerchant(@PathVariable Long id) {
        log.debug("REST request to delete Merchant : {}", id);
        merchantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
