package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.ReservationTour;
import ch.itsforward.ecolifeexpedition.repository.ReservationTourRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReservationTourResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReservationTourResourceIT {

    private static final Float DEFAULT_MONTANT_TOTAL_TTC = 1F;
    private static final Float UPDATED_MONTANT_TOTAL_TTC = 2F;

    @Autowired
    private ReservationTourRepository reservationTourRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReservationTourMockMvc;

    private ReservationTour reservationTour;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservationTour createEntity(EntityManager em) {
        ReservationTour reservationTour = new ReservationTour()
            .montantTotalTTC(DEFAULT_MONTANT_TOTAL_TTC);
        return reservationTour;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservationTour createUpdatedEntity(EntityManager em) {
        ReservationTour reservationTour = new ReservationTour()
            .montantTotalTTC(UPDATED_MONTANT_TOTAL_TTC);
        return reservationTour;
    }

    @BeforeEach
    public void initTest() {
        reservationTour = createEntity(em);
    }

    @Test
    @Transactional
    public void createReservationTour() throws Exception {
        int databaseSizeBeforeCreate = reservationTourRepository.findAll().size();
        // Create the ReservationTour
        restReservationTourMockMvc.perform(post("/api/reservation-tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reservationTour)))
            .andExpect(status().isCreated());

        // Validate the ReservationTour in the database
        List<ReservationTour> reservationTourList = reservationTourRepository.findAll();
        assertThat(reservationTourList).hasSize(databaseSizeBeforeCreate + 1);
        ReservationTour testReservationTour = reservationTourList.get(reservationTourList.size() - 1);
        assertThat(testReservationTour.getMontantTotalTTC()).isEqualTo(DEFAULT_MONTANT_TOTAL_TTC);
    }

    @Test
    @Transactional
    public void createReservationTourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reservationTourRepository.findAll().size();

        // Create the ReservationTour with an existing ID
        reservationTour.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservationTourMockMvc.perform(post("/api/reservation-tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reservationTour)))
            .andExpect(status().isBadRequest());

        // Validate the ReservationTour in the database
        List<ReservationTour> reservationTourList = reservationTourRepository.findAll();
        assertThat(reservationTourList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReservationTours() throws Exception {
        // Initialize the database
        reservationTourRepository.saveAndFlush(reservationTour);

        // Get all the reservationTourList
        restReservationTourMockMvc.perform(get("/api/reservation-tours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservationTour.getId().intValue())))
            .andExpect(jsonPath("$.[*].montantTotalTTC").value(hasItem(DEFAULT_MONTANT_TOTAL_TTC.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getReservationTour() throws Exception {
        // Initialize the database
        reservationTourRepository.saveAndFlush(reservationTour);

        // Get the reservationTour
        restReservationTourMockMvc.perform(get("/api/reservation-tours/{id}", reservationTour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reservationTour.getId().intValue()))
            .andExpect(jsonPath("$.montantTotalTTC").value(DEFAULT_MONTANT_TOTAL_TTC.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingReservationTour() throws Exception {
        // Get the reservationTour
        restReservationTourMockMvc.perform(get("/api/reservation-tours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReservationTour() throws Exception {
        // Initialize the database
        reservationTourRepository.saveAndFlush(reservationTour);

        int databaseSizeBeforeUpdate = reservationTourRepository.findAll().size();

        // Update the reservationTour
        ReservationTour updatedReservationTour = reservationTourRepository.findById(reservationTour.getId()).get();
        // Disconnect from session so that the updates on updatedReservationTour are not directly saved in db
        em.detach(updatedReservationTour);
        updatedReservationTour
            .montantTotalTTC(UPDATED_MONTANT_TOTAL_TTC);

        restReservationTourMockMvc.perform(put("/api/reservation-tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReservationTour)))
            .andExpect(status().isOk());

        // Validate the ReservationTour in the database
        List<ReservationTour> reservationTourList = reservationTourRepository.findAll();
        assertThat(reservationTourList).hasSize(databaseSizeBeforeUpdate);
        ReservationTour testReservationTour = reservationTourList.get(reservationTourList.size() - 1);
        assertThat(testReservationTour.getMontantTotalTTC()).isEqualTo(UPDATED_MONTANT_TOTAL_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingReservationTour() throws Exception {
        int databaseSizeBeforeUpdate = reservationTourRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationTourMockMvc.perform(put("/api/reservation-tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reservationTour)))
            .andExpect(status().isBadRequest());

        // Validate the ReservationTour in the database
        List<ReservationTour> reservationTourList = reservationTourRepository.findAll();
        assertThat(reservationTourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReservationTour() throws Exception {
        // Initialize the database
        reservationTourRepository.saveAndFlush(reservationTour);

        int databaseSizeBeforeDelete = reservationTourRepository.findAll().size();

        // Delete the reservationTour
        restReservationTourMockMvc.perform(delete("/api/reservation-tours/{id}", reservationTour.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReservationTour> reservationTourList = reservationTourRepository.findAll();
        assertThat(reservationTourList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
