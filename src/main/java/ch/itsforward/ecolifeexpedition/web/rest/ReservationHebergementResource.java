package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.ReservationHebergement;
import ch.itsforward.ecolifeexpedition.repository.ReservationHebergementRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.ReservationHebergement}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReservationHebergementResource {

    private final Logger log = LoggerFactory.getLogger(ReservationHebergementResource.class);

    private static final String ENTITY_NAME = "reservationHebergement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReservationHebergementRepository reservationHebergementRepository;

    public ReservationHebergementResource(ReservationHebergementRepository reservationHebergementRepository) {
        this.reservationHebergementRepository = reservationHebergementRepository;
    }

    /**
     * {@code POST  /reservation-hebergements} : Create a new reservationHebergement.
     *
     * @param reservationHebergement the reservationHebergement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reservationHebergement, or with status {@code 400 (Bad Request)} if the reservationHebergement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reservation-hebergements")
    public ResponseEntity<ReservationHebergement> createReservationHebergement(@RequestBody ReservationHebergement reservationHebergement) throws URISyntaxException {
        log.debug("REST request to save ReservationHebergement : {}", reservationHebergement);
        if (reservationHebergement.getId() != null) {
            throw new BadRequestAlertException("A new reservationHebergement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReservationHebergement result = reservationHebergementRepository.save(reservationHebergement);
        return ResponseEntity.created(new URI("/api/reservation-hebergements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reservation-hebergements} : Updates an existing reservationHebergement.
     *
     * @param reservationHebergement the reservationHebergement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservationHebergement,
     * or with status {@code 400 (Bad Request)} if the reservationHebergement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reservationHebergement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reservation-hebergements")
    public ResponseEntity<ReservationHebergement> updateReservationHebergement(@RequestBody ReservationHebergement reservationHebergement) throws URISyntaxException {
        log.debug("REST request to update ReservationHebergement : {}", reservationHebergement);
        if (reservationHebergement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReservationHebergement result = reservationHebergementRepository.save(reservationHebergement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservationHebergement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reservation-hebergements} : get all the reservationHebergements.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reservationHebergements in body.
     */
    @GetMapping("/reservation-hebergements")
    public List<ReservationHebergement> getAllReservationHebergements(@RequestParam(required = false) String filter) {
        if ("reservation-is-null".equals(filter)) {
            log.debug("REST request to get all ReservationHebergements where reservation is null");
            return StreamSupport
                .stream(reservationHebergementRepository.findAll().spliterator(), false)
                .filter(reservationHebergement -> reservationHebergement.getReservation() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all ReservationHebergements");
        return reservationHebergementRepository.findAll();
    }

    /**
     * {@code GET  /reservation-hebergements/:id} : get the "id" reservationHebergement.
     *
     * @param id the id of the reservationHebergement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reservationHebergement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reservation-hebergements/{id}")
    public ResponseEntity<ReservationHebergement> getReservationHebergement(@PathVariable Long id) {
        log.debug("REST request to get ReservationHebergement : {}", id);
        Optional<ReservationHebergement> reservationHebergement = reservationHebergementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reservationHebergement);
    }

    /**
     * {@code DELETE  /reservation-hebergements/:id} : delete the "id" reservationHebergement.
     *
     * @param id the id of the reservationHebergement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reservation-hebergements/{id}")
    public ResponseEntity<Void> deleteReservationHebergement(@PathVariable Long id) {
        log.debug("REST request to delete ReservationHebergement : {}", id);
        reservationHebergementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
