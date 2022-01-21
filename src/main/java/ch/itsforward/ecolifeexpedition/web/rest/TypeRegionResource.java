package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.TypeRegion;
import ch.itsforward.ecolifeexpedition.repository.TypeRegionRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.TypeRegion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TypeRegionResource {

    private final Logger log = LoggerFactory.getLogger(TypeRegionResource.class);

    private static final String ENTITY_NAME = "typeRegion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeRegionRepository typeRegionRepository;

    public TypeRegionResource(TypeRegionRepository typeRegionRepository) {
        this.typeRegionRepository = typeRegionRepository;
    }

    /**
     * {@code POST  /type-regions} : Create a new typeRegion.
     *
     * @param typeRegion the typeRegion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeRegion, or with status {@code 400 (Bad Request)} if the typeRegion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-regions")
    public ResponseEntity<TypeRegion> createTypeRegion(@RequestBody TypeRegion typeRegion) throws URISyntaxException {
        log.debug("REST request to save TypeRegion : {}", typeRegion);
        if (typeRegion.getId() != null) {
            throw new BadRequestAlertException("A new typeRegion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeRegion result = typeRegionRepository.save(typeRegion);
        return ResponseEntity.created(new URI("/api/type-regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-regions} : Updates an existing typeRegion.
     *
     * @param typeRegion the typeRegion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeRegion,
     * or with status {@code 400 (Bad Request)} if the typeRegion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeRegion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-regions")
    public ResponseEntity<TypeRegion> updateTypeRegion(@RequestBody TypeRegion typeRegion) throws URISyntaxException {
        log.debug("REST request to update TypeRegion : {}", typeRegion);
        if (typeRegion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeRegion result = typeRegionRepository.save(typeRegion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeRegion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-regions} : get all the typeRegions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeRegions in body.
     */
    @GetMapping("/type-regions")
    public List<TypeRegion> getAllTypeRegions() {
        log.debug("REST request to get all TypeRegions");
        return typeRegionRepository.findAll();
    }

    /**
     * {@code GET  /type-regions/:id} : get the "id" typeRegion.
     *
     * @param id the id of the typeRegion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeRegion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-regions/{id}")
    public ResponseEntity<TypeRegion> getTypeRegion(@PathVariable Long id) {
        log.debug("REST request to get TypeRegion : {}", id);
        Optional<TypeRegion> typeRegion = typeRegionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeRegion);
    }

    /**
     * {@code DELETE  /type-regions/:id} : delete the "id" typeRegion.
     *
     * @param id the id of the typeRegion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-regions/{id}")
    public ResponseEntity<Void> deleteTypeRegion(@PathVariable Long id) {
        log.debug("REST request to delete TypeRegion : {}", id);
        typeRegionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
