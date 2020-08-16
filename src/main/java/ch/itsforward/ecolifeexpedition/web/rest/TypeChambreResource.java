package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.TypeChambre;
import ch.itsforward.ecolifeexpedition.repository.TypeChambreRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.TypeChambre}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TypeChambreResource {

    private final Logger log = LoggerFactory.getLogger(TypeChambreResource.class);

    private static final String ENTITY_NAME = "typeChambre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeChambreRepository typeChambreRepository;

    public TypeChambreResource(TypeChambreRepository typeChambreRepository) {
        this.typeChambreRepository = typeChambreRepository;
    }

    /**
     * {@code POST  /type-chambres} : Create a new typeChambre.
     *
     * @param typeChambre the typeChambre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeChambre, or with status {@code 400 (Bad Request)} if the typeChambre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-chambres")
    public ResponseEntity<TypeChambre> createTypeChambre(@RequestBody TypeChambre typeChambre) throws URISyntaxException {
        log.debug("REST request to save TypeChambre : {}", typeChambre);
        if (typeChambre.getId() != null) {
            throw new BadRequestAlertException("A new typeChambre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeChambre result = typeChambreRepository.save(typeChambre);
        return ResponseEntity.created(new URI("/api/type-chambres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-chambres} : Updates an existing typeChambre.
     *
     * @param typeChambre the typeChambre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeChambre,
     * or with status {@code 400 (Bad Request)} if the typeChambre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeChambre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-chambres")
    public ResponseEntity<TypeChambre> updateTypeChambre(@RequestBody TypeChambre typeChambre) throws URISyntaxException {
        log.debug("REST request to update TypeChambre : {}", typeChambre);
        if (typeChambre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeChambre result = typeChambreRepository.save(typeChambre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeChambre.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-chambres} : get all the typeChambres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeChambres in body.
     */
    @GetMapping("/type-chambres")
    public List<TypeChambre> getAllTypeChambres() {
        log.debug("REST request to get all TypeChambres");
        return typeChambreRepository.findAll();
    }

    /**
     * {@code GET  /type-chambres/:id} : get the "id" typeChambre.
     *
     * @param id the id of the typeChambre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeChambre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-chambres/{id}")
    public ResponseEntity<TypeChambre> getTypeChambre(@PathVariable Long id) {
        log.debug("REST request to get TypeChambre : {}", id);
        Optional<TypeChambre> typeChambre = typeChambreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeChambre);
    }

    /**
     * {@code DELETE  /type-chambres/:id} : delete the "id" typeChambre.
     *
     * @param id the id of the typeChambre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-chambres/{id}")
    public ResponseEntity<Void> deleteTypeChambre(@PathVariable Long id) {
        log.debug("REST request to delete TypeChambre : {}", id);
        typeChambreRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
