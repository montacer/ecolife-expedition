package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.Reservation;
import ch.itsforward.ecolifeexpedition.repository.ReservationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReservationResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReservationResourceIT {

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NOMBRE_ADULTE = 1;
    private static final Integer UPDATED_NOMBRE_ADULTE = 2;

    private static final Integer DEFAULT_NOMBRE_JEUNE = 1;
    private static final Integer UPDATED_NOMBRE_JEUNE = 2;

    private static final Integer DEFAULT_NOMBRE_ENFANT = 1;
    private static final Integer UPDATED_NOMBRE_ENFANT = 2;

    private static final Float DEFAULT_MONTANT_TTC = 1F;
    private static final Float UPDATED_MONTANT_TTC = 2F;

    private static final String DEFAULT_LIB_DEVISE = "AAAAAAAAAA";
    private static final String UPDATED_LIB_DEVISE = "BBBBBBBBBB";

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReservationMockMvc;

    private Reservation reservation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservation createEntity(EntityManager em) {
        Reservation reservation = new Reservation()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .nombreAdulte(DEFAULT_NOMBRE_ADULTE)
            .nombreJeune(DEFAULT_NOMBRE_JEUNE)
            .nombreEnfant(DEFAULT_NOMBRE_ENFANT)
            .montantTTC(DEFAULT_MONTANT_TTC)
            .libDevise(DEFAULT_LIB_DEVISE);
        return reservation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservation createUpdatedEntity(EntityManager em) {
        Reservation reservation = new Reservation()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .nombreAdulte(UPDATED_NOMBRE_ADULTE)
            .nombreJeune(UPDATED_NOMBRE_JEUNE)
            .nombreEnfant(UPDATED_NOMBRE_ENFANT)
            .montantTTC(UPDATED_MONTANT_TTC)
            .libDevise(UPDATED_LIB_DEVISE);
        return reservation;
    }

    @BeforeEach
    public void initTest() {
        reservation = createEntity(em);
    }

    @Test
    @Transactional
    public void createReservation() throws Exception {
        int databaseSizeBeforeCreate = reservationRepository.findAll().size();
        // Create the Reservation
        restReservationMockMvc.perform(post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
            .andExpect(status().isCreated());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeCreate + 1);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testReservation.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testReservation.getNombreAdulte()).isEqualTo(DEFAULT_NOMBRE_ADULTE);
        assertThat(testReservation.getNombreJeune()).isEqualTo(DEFAULT_NOMBRE_JEUNE);
        assertThat(testReservation.getNombreEnfant()).isEqualTo(DEFAULT_NOMBRE_ENFANT);
        assertThat(testReservation.getMontantTTC()).isEqualTo(DEFAULT_MONTANT_TTC);
        assertThat(testReservation.getLibDevise()).isEqualTo(DEFAULT_LIB_DEVISE);
    }

    @Test
    @Transactional
    public void createReservationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reservationRepository.findAll().size();

        // Create the Reservation with an existing ID
        reservation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservationMockMvc.perform(post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReservations() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList
        restReservationMockMvc.perform(get("/api/reservations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].nombreAdulte").value(hasItem(DEFAULT_NOMBRE_ADULTE)))
            .andExpect(jsonPath("$.[*].nombreJeune").value(hasItem(DEFAULT_NOMBRE_JEUNE)))
            .andExpect(jsonPath("$.[*].nombreEnfant").value(hasItem(DEFAULT_NOMBRE_ENFANT)))
            .andExpect(jsonPath("$.[*].montantTTC").value(hasItem(DEFAULT_MONTANT_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].libDevise").value(hasItem(DEFAULT_LIB_DEVISE)));
    }
    
    @Test
    @Transactional
    public void getReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get the reservation
        restReservationMockMvc.perform(get("/api/reservations/{id}", reservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reservation.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.nombreAdulte").value(DEFAULT_NOMBRE_ADULTE))
            .andExpect(jsonPath("$.nombreJeune").value(DEFAULT_NOMBRE_JEUNE))
            .andExpect(jsonPath("$.nombreEnfant").value(DEFAULT_NOMBRE_ENFANT))
            .andExpect(jsonPath("$.montantTTC").value(DEFAULT_MONTANT_TTC.doubleValue()))
            .andExpect(jsonPath("$.libDevise").value(DEFAULT_LIB_DEVISE));
    }
    @Test
    @Transactional
    public void getNonExistingReservation() throws Exception {
        // Get the reservation
        restReservationMockMvc.perform(get("/api/reservations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Update the reservation
        Reservation updatedReservation = reservationRepository.findById(reservation.getId()).get();
        // Disconnect from session so that the updates on updatedReservation are not directly saved in db
        em.detach(updatedReservation);
        updatedReservation
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .nombreAdulte(UPDATED_NOMBRE_ADULTE)
            .nombreJeune(UPDATED_NOMBRE_JEUNE)
            .nombreEnfant(UPDATED_NOMBRE_ENFANT)
            .montantTTC(UPDATED_MONTANT_TTC)
            .libDevise(UPDATED_LIB_DEVISE);

        restReservationMockMvc.perform(put("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReservation)))
            .andExpect(status().isOk());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testReservation.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testReservation.getNombreAdulte()).isEqualTo(UPDATED_NOMBRE_ADULTE);
        assertThat(testReservation.getNombreJeune()).isEqualTo(UPDATED_NOMBRE_JEUNE);
        assertThat(testReservation.getNombreEnfant()).isEqualTo(UPDATED_NOMBRE_ENFANT);
        assertThat(testReservation.getMontantTTC()).isEqualTo(UPDATED_MONTANT_TTC);
        assertThat(testReservation.getLibDevise()).isEqualTo(UPDATED_LIB_DEVISE);
    }

    @Test
    @Transactional
    public void updateNonExistingReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationMockMvc.perform(put("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        int databaseSizeBeforeDelete = reservationRepository.findAll().size();

        // Delete the reservation
        restReservationMockMvc.perform(delete("/api/reservations/{id}", reservation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
