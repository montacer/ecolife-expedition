package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.AvisTourMedia;
import ch.itsforward.ecolifeexpedition.repository.AvisTourMediaRepository;
import ch.itsforward.ecolifeexpedition.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.AvisTourMedia}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AvisTourMediaResource {

    private final Logger log = LoggerFactory.getLogger(AvisTourMediaResource.class);

    private static final String ENTITY_NAME = "avisTourMedia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvisTourMediaRepository avisTourMediaRepository;

    public AvisTourMediaResource(AvisTourMediaRepository avisTourMediaRepository) {
        this.avisTourMediaRepository = avisTourMediaRepository;
    }

    /**
     * {@code POST  /avis-tour-medias} : Create a new avisTourMedia.
     *
     * @param avisTourMedia the avisTourMedia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avisTourMedia, or with status {@code 400 (Bad Request)} if the avisTourMedia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avis-tour-medias")
    public ResponseEntity<AvisTourMedia> createAvisTourMedia(@RequestBody AvisTourMedia avisTourMedia) throws URISyntaxException {
        log.debug("REST request to save AvisTourMedia : {}", avisTourMedia);
        if (avisTourMedia.getId() != null) {
            throw new BadRequestAlertException("A new avisTourMedia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvisTourMedia result = avisTourMediaRepository.save(avisTourMedia);
        return ResponseEntity.created(new URI("/api/avis-tour-medias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avis-tour-medias} : Updates an existing avisTourMedia.
     *
     * @param avisTourMedia the avisTourMedia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avisTourMedia,
     * or with status {@code 400 (Bad Request)} if the avisTourMedia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avisTourMedia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avis-tour-medias")
    public ResponseEntity<AvisTourMedia> updateAvisTourMedia(@RequestBody AvisTourMedia avisTourMedia) throws URISyntaxException {
        log.debug("REST request to update AvisTourMedia : {}", avisTourMedia);
        if (avisTourMedia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvisTourMedia result = avisTourMediaRepository.save(avisTourMedia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avisTourMedia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /avis-tour-medias} : get all the avisTourMedias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avisTourMedias in body.
     */
    @GetMapping("/avis-tour-medias")
    public List<AvisTourMedia> getAllAvisTourMedias() {
        log.debug("REST request to get all AvisTourMedias");
        return avisTourMediaRepository.findAll();
    }

    /**
     * {@code GET  /avis-tour-medias/:id} : get the "id" avisTourMedia.
     *
     * @param id the id of the avisTourMedia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avisTourMedia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avis-tour-medias/{id}")
    public ResponseEntity<AvisTourMedia> getAvisTourMedia(@PathVariable Long id) {
        log.debug("REST request to get AvisTourMedia : {}", id);
        Optional<AvisTourMedia> avisTourMedia = avisTourMediaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(avisTourMedia);
    }

    /**
     * {@code DELETE  /avis-tour-medias/:id} : delete the "id" avisTourMedia.
     *
     * @param id the id of the avisTourMedia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avis-tour-medias/{id}")
    public ResponseEntity<Void> deleteAvisTourMedia(@PathVariable Long id) {
        log.debug("REST request to delete AvisTourMedia : {}", id);
        avisTourMediaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
