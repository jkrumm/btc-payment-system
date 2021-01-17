package com.jkrumm.btcpay.web.rest;

import com.jkrumm.btcpay.service.ConfidenceService;
import com.jkrumm.btcpay.service.dto.ConfidenceDTO;
import com.jkrumm.btcpay.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
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
 * REST controller for managing {@link com.jkrumm.btcpay.domain.Confidence}.
 */
@RestController
@RequestMapping("/api")
public class ConfidenceResource {
    private final Logger log = LoggerFactory.getLogger(ConfidenceResource.class);

    private static final String ENTITY_NAME = "confidence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfidenceService confidenceService;

    public ConfidenceResource(ConfidenceService confidenceService) {
        this.confidenceService = confidenceService;
    }

    /**
     * {@code POST  /confidences} : Create a new confidence.
     *
     * @param confidenceDTO the confidenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new confidenceDTO, or with status {@code 400 (Bad Request)} if the confidence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/confidences")
    public ResponseEntity<ConfidenceDTO> createConfidence(@RequestBody ConfidenceDTO confidenceDTO) throws URISyntaxException {
        log.debug("REST request to save Confidence : {}", confidenceDTO);
        if (confidenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new confidence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfidenceDTO result = confidenceService.save(confidenceDTO);
        return ResponseEntity
            .created(new URI("/api/confidences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /confidences} : Updates an existing confidence.
     *
     * @param confidenceDTO the confidenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated confidenceDTO,
     * or with status {@code 400 (Bad Request)} if the confidenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the confidenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/confidences")
    public ResponseEntity<ConfidenceDTO> updateConfidence(@RequestBody ConfidenceDTO confidenceDTO) throws URISyntaxException {
        log.debug("REST request to update Confidence : {}", confidenceDTO);
        if (confidenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConfidenceDTO result = confidenceService.save(confidenceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, confidenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /confidences} : get all the confidences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of confidences in body.
     */
    @GetMapping("/confidences")
    public ResponseEntity<List<ConfidenceDTO>> getAllConfidences(Pageable pageable) {
        log.debug("REST request to get a page of Confidences");
        Page<ConfidenceDTO> page = confidenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /confidences/:id} : get the "id" confidence.
     *
     * @param id the id of the confidenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the confidenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/confidences/{id}")
    public ResponseEntity<ConfidenceDTO> getConfidence(@PathVariable Long id) {
        log.debug("REST request to get Confidence : {}", id);
        Optional<ConfidenceDTO> confidenceDTO = confidenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(confidenceDTO);
    }

    /**
     * {@code DELETE  /confidences/:id} : delete the "id" confidence.
     *
     * @param id the id of the confidenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/confidences/{id}")
    public ResponseEntity<Void> deleteConfidence(@PathVariable Long id) {
        log.debug("REST request to delete Confidence : {}", id);
        confidenceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
