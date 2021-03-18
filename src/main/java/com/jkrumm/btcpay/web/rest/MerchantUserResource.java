package com.jkrumm.btcpay.web.rest;

import com.jkrumm.btcpay.service.MerchantUserService;
import com.jkrumm.btcpay.service.dto.MerchantUserDTO;
import com.jkrumm.btcpay.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jkrumm.btcpay.domain.MerchantUser}.
 */
@RestController
@RequestMapping("/api")
public class MerchantUserResource {

    private final Logger log = LoggerFactory.getLogger(MerchantUserResource.class);

    private static final String ENTITY_NAME = "merchantUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MerchantUserService merchantUserService;

    public MerchantUserResource(MerchantUserService merchantUserService) {
        this.merchantUserService = merchantUserService;
    }

    /**
     * {@code POST  /merchant-users} : Create a new merchantUser.
     *
     * @param merchantUserDTO the merchantUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new merchantUserDTO, or with status {@code 400 (Bad Request)} if the merchantUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/merchant-users")
    public ResponseEntity<MerchantUserDTO> createMerchantUser(@RequestBody MerchantUserDTO merchantUserDTO) throws URISyntaxException {
        log.debug("REST request to save MerchantUser : {}", merchantUserDTO);
        if (merchantUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new merchantUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MerchantUserDTO result = merchantUserService.save(merchantUserDTO);
        return ResponseEntity
            .created(new URI("/api/merchant-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /merchant-users} : Updates an existing merchantUser.
     *
     * @param merchantUserDTO the merchantUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated merchantUserDTO,
     * or with status {@code 400 (Bad Request)} if the merchantUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the merchantUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/merchant-users")
    public ResponseEntity<MerchantUserDTO> updateMerchantUser(@RequestBody MerchantUserDTO merchantUserDTO) throws URISyntaxException {
        log.debug("REST request to update MerchantUser : {}", merchantUserDTO);
        if (merchantUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MerchantUserDTO result = merchantUserService.save(merchantUserDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, merchantUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /merchant-users} : Updates given fields of an existing merchantUser.
     *
     * @param merchantUserDTO the merchantUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated merchantUserDTO,
     * or with status {@code 400 (Bad Request)} if the merchantUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the merchantUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the merchantUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/merchant-users", consumes = "application/merge-patch+json")
    public ResponseEntity<MerchantUserDTO> partialUpdateMerchantUser(@RequestBody MerchantUserDTO merchantUserDTO)
        throws URISyntaxException {
        log.debug("REST request to update MerchantUser partially : {}", merchantUserDTO);
        if (merchantUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<MerchantUserDTO> result = merchantUserService.partialUpdate(merchantUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, merchantUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /merchant-users} : get all the merchantUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of merchantUsers in body.
     */
    @GetMapping("/merchant-users")
    public List<MerchantUserDTO> getAllMerchantUsers() {
        log.debug("REST request to get all MerchantUsers");
        return merchantUserService.findAll();
    }

    /**
     * {@code GET  /merchant-users/:id} : get the "id" merchantUser.
     *
     * @param id the id of the merchantUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the merchantUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/merchant-users/{id}")
    public ResponseEntity<MerchantUserDTO> getMerchantUser(@PathVariable Long id) {
        log.debug("REST request to get MerchantUser : {}", id);
        Optional<MerchantUserDTO> merchantUserDTO = merchantUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(merchantUserDTO);
    }

    /**
     * {@code DELETE  /merchant-users/:id} : delete the "id" merchantUser.
     *
     * @param id the id of the merchantUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/merchant-users/{id}")
    public ResponseEntity<Void> deleteMerchantUser(@PathVariable Long id) {
        log.debug("REST request to delete MerchantUser : {}", id);
        merchantUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
