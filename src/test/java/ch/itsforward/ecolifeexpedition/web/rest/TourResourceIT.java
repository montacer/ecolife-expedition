package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.Tour;
import ch.itsforward.ecolifeexpedition.repository.TourRepository;

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
 * Integration tests for the {@link TourResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TourResourceIT {

    private static final String DEFAULT_LIB_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_LIB_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CONSEIL = "AAAAAAAAAA";
    private static final String UPDATED_CONSEIL = "BBBBBBBBBB";

    private static final Float DEFAULT_PRIX_TTC = 1F;
    private static final Float UPDATED_PRIX_TTC = 2F;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTourMockMvc;

    private Tour tour;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tour createEntity(EntityManager em) {
        Tour tour = new Tour()
            .libTitre(DEFAULT_LIB_TITRE)
            .imageUrl(DEFAULT_IMAGE_URL)
            .videoUrl(DEFAULT_VIDEO_URL)
            .conseil(DEFAULT_CONSEIL)
            .prixTTC(DEFAULT_PRIX_TTC);
        return tour;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tour createUpdatedEntity(EntityManager em) {
        Tour tour = new Tour()
            .libTitre(UPDATED_LIB_TITRE)
            .imageUrl(UPDATED_IMAGE_URL)
            .videoUrl(UPDATED_VIDEO_URL)
            .conseil(UPDATED_CONSEIL)
            .prixTTC(UPDATED_PRIX_TTC);
        return tour;
    }

    @BeforeEach
    public void initTest() {
        tour = createEntity(em);
    }

    @Test
    @Transactional
    public void createTour() throws Exception {
        int databaseSizeBeforeCreate = tourRepository.findAll().size();
        // Create the Tour
        restTourMockMvc.perform(post("/api/tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isCreated());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeCreate + 1);
        Tour testTour = tourList.get(tourList.size() - 1);
        assertThat(testTour.getLibTitre()).isEqualTo(DEFAULT_LIB_TITRE);
        assertThat(testTour.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testTour.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testTour.getConseil()).isEqualTo(DEFAULT_CONSEIL);
        assertThat(testTour.getPrixTTC()).isEqualTo(DEFAULT_PRIX_TTC);
    }

    @Test
    @Transactional
    public void createTourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tourRepository.findAll().size();

        // Create the Tour with an existing ID
        tour.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTourMockMvc.perform(post("/api/tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isBadRequest());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTours() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        // Get all the tourList
        restTourMockMvc.perform(get("/api/tours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tour.getId().intValue())))
            .andExpect(jsonPath("$.[*].libTitre").value(hasItem(DEFAULT_LIB_TITRE)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].conseil").value(hasItem(DEFAULT_CONSEIL)))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTour() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        // Get the tour
        restTourMockMvc.perform(get("/api/tours/{id}", tour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tour.getId().intValue()))
            .andExpect(jsonPath("$.libTitre").value(DEFAULT_LIB_TITRE))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL))
            .andExpect(jsonPath("$.conseil").value(DEFAULT_CONSEIL))
            .andExpect(jsonPath("$.prixTTC").value(DEFAULT_PRIX_TTC.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTour() throws Exception {
        // Get the tour
        restTourMockMvc.perform(get("/api/tours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTour() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        int databaseSizeBeforeUpdate = tourRepository.findAll().size();

        // Update the tour
        Tour updatedTour = tourRepository.findById(tour.getId()).get();
        // Disconnect from session so that the updates on updatedTour are not directly saved in db
        em.detach(updatedTour);
        updatedTour
            .libTitre(UPDATED_LIB_TITRE)
            .imageUrl(UPDATED_IMAGE_URL)
            .videoUrl(UPDATED_VIDEO_URL)
            .conseil(UPDATED_CONSEIL)
            .prixTTC(UPDATED_PRIX_TTC);

        restTourMockMvc.perform(put("/api/tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTour)))
            .andExpect(status().isOk());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeUpdate);
        Tour testTour = tourList.get(tourList.size() - 1);
        assertThat(testTour.getLibTitre()).isEqualTo(UPDATED_LIB_TITRE);
        assertThat(testTour.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testTour.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testTour.getConseil()).isEqualTo(UPDATED_CONSEIL);
        assertThat(testTour.getPrixTTC()).isEqualTo(UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingTour() throws Exception {
        int databaseSizeBeforeUpdate = tourRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourMockMvc.perform(put("/api/tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isBadRequest());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTour() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        int databaseSizeBeforeDelete = tourRepository.findAll().size();

        // Delete the tour
        restTourMockMvc.perform(delete("/api/tours/{id}", tour.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
