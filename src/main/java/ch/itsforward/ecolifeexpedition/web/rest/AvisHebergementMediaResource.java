package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.AvisHebergementMedia;
import ch.itsforward.ecolifeexpedition.repository.AvisHebergementMediaRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.AvisHebergementMedia}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AvisHebergementMediaResource {

    private final Logger log = LoggerFactory.getLogger(AvisHebergementMediaResource.class);

    private static final String ENTITY_NAME = "avisHebergementMedia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvisHebergementMediaRepository avisHebergementMediaRepository;

    public AvisHebergementMediaResource(AvisHebergementMediaRepository avisHebergementMediaRepository) {
        this.avisHebergementMediaRepository = avisHebergementMediaRepository;
    }

    /**
     * {@code POST  /avis-hebergement-medias} : Create a new avisHebergementMedia.
     *
     * @param avisHebergementMedia the avisHebergementMedia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avisHebergementMedia, or with status {@code 400 (Bad Request)} if the avisHebergementMedia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avis-hebergement-medias")
    public ResponseEntity<AvisHebergementMedia> createAvisHebergementMedia(@RequestBody AvisHebergementMedia avisHebergementMedia) throws URISyntaxException {
        log.debug("REST request to save AvisHebergementMedia : {}", avisHebergementMedia);
        if (avisHebergementMedia.getId() != null) {
            throw new BadRequestAlertException("A new avisHebergementMedia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvisHebergementMedia result = avisHebergementMediaRepository.save(avisHebergementMedia);
        return ResponseEntity.created(new URI("/api/avis-hebergement-medias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avis-hebergement-medias} : Updates an existing avisHebergementMedia.
     *
     * @param avisHebergementMedia the avisHebergementMedia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avisHebergementMedia,
     * or with status {@code 400 (Bad Request)} if the avisHebergementMedia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avisHebergementMedia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avis-hebergement-medias")
    public ResponseEntity<AvisHebergementMedia> updateAvisHebergementMedia(@RequestBody AvisHebergementMedia avisHebergementMedia) throws URISyntaxException {
        log.debug("REST request to update AvisHebergementMedia : {}", avisHebergementMedia);
        if (avisHebergementMedia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvisHebergementMedia result = avisHebergementMediaRepository.save(avisHebergementMedia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avisHebergementMedia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /avis-hebergement-medias} : get all the avisHebergementMedias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avisHebergementMedias in body.
     */
    @GetMapping("/avis-hebergement-medias")
    public List<AvisHebergementMedia> getAllAvisHebergementMedias() {
        log.debug("REST request to get all AvisHebergementMedias");
        return avisHebergementMediaRepository.findAll();
    }

    /**
     * {@code GET  /avis-hebergement-medias/:id} : get the "id" avisHebergementMedia.
     *
     * @param id the id of the avisHebergementMedia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avisHebergementMedia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avis-hebergement-medias/{id}")
    public ResponseEntity<AvisHebergementMedia> getAvisHebergementMedia(@PathVariable Long id) {
        log.debug("REST request to get AvisHebergementMedia : {}", id);
        Optional<AvisHebergementMedia> avisHebergementMedia = avisHebergementMediaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(avisHebergementMedia);
    }

    /**
     * {@code DELETE  /avis-hebergement-medias/:id} : delete the "id" avisHebergementMedia.
     *
     * @param id the id of the avisHebergementMedia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avis-hebergement-medias/{id}")
    public ResponseEntity<Void> deleteAvisHebergementMedia(@PathVariable Long id) {
        log.debug("REST request to delete AvisHebergementMedia : {}", id);
        avisHebergementMediaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
