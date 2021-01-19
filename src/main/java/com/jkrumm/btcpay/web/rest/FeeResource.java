package com.jkrumm.btcpay.web.rest;

import com.jkrumm.btcpay.service.FeeService;
import com.jkrumm.btcpay.service.dto.FeeDTO;
import com.jkrumm.btcpay.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jkrumm.btcpay.domain.Fee}.
 */
@RestController
@RequestMapping("/api")
public class FeeResource {

    private final Logger log = LoggerFactory.getLogger(FeeResource.class);

    private static final String ENTITY_NAME = "fee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeeService feeService;

    public FeeResource(FeeService feeService) {
        this.feeService = feeService;
    }

    /**
     * {@code POST  /fees} : Create a new fee.
     *
     * @param feeDTO the feeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feeDTO, or with status {@code 400 (Bad Request)} if the fee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fees")
    public ResponseEntity<FeeDTO> createFee(@Valid @RequestBody FeeDTO feeDTO) throws URISyntaxException {
        log.debug("REST request to save Fee : {}", feeDTO);
        if (feeDTO.getId() != null) {
            throw new BadRequestAlertException("A new fee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeeDTO result = feeService.save(feeDTO);
        return ResponseEntity
            .created(new URI("/api/fees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fees} : Updates an existing fee.
     *
     * @param feeDTO the feeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feeDTO,
     * or with status {@code 400 (Bad Request)} if the feeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fees")
    public ResponseEntity<FeeDTO> updateFee(@Valid @RequestBody FeeDTO feeDTO) throws URISyntaxException {
        log.debug("REST request to update Fee : {}", feeDTO);
        if (feeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FeeDTO result = feeService.save(feeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fees} : Updates given fields of an existing fee.
     *
     * @param feeDTO the feeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feeDTO,
     * or with status {@code 400 (Bad Request)} if the feeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the feeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the feeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fees", consumes = "application/merge-patch+json")
    public ResponseEntity<FeeDTO> partialUpdateFee(@NotNull @RequestBody FeeDTO feeDTO) throws URISyntaxException {
        log.debug("REST request to update Fee partially : {}", feeDTO);
        if (feeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<FeeDTO> result = feeService.partialUpdate(feeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fees} : get all the fees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fees in body.
     */
    @GetMapping("/fees")
    public List<FeeDTO> getAllFees() {
        log.debug("REST request to get all Fees");
        return feeService.findAll();
    }

    /**
     * {@code GET  /fees/:id} : get the "id" fee.
     *
     * @param id the id of the feeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fees/{id}")
    public ResponseEntity<FeeDTO> getFee(@PathVariable Long id) {
        log.debug("REST request to get Fee : {}", id);
        Optional<FeeDTO> feeDTO = feeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feeDTO);
    }

    /**
     * {@code DELETE  /fees/:id} : delete the "id" fee.
     *
     * @param id the id of the feeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fees/{id}")
    public ResponseEntity<Void> deleteFee(@PathVariable Long id) {
        log.debug("REST request to delete Fee : {}", id);
        feeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
