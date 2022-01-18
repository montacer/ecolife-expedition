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
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.itsforward.ecolifeexpedition.domain.enumeration.Saison;
import ch.itsforward.ecolifeexpedition.domain.enumeration.TourStatus;
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

    private static final byte[] DEFAULT_IMAGE_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_CONTENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_VIDEO_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VIDEO_CONTENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_VIDEO_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIDEO_CONTENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CONSEIL = "AAAAAAAAAA";
    private static final String UPDATED_CONSEIL = "BBBBBBBBBB";

    private static final Float DEFAULT_PRIX_TTC = 1F;
    private static final Float UPDATED_PRIX_TTC = 2F;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMISE = false;
    private static final Boolean UPDATED_REMISE = true;

    private static final Float DEFAULT_PRIX_REMISE = 1F;
    private static final Float UPDATED_PRIX_REMISE = 2F;

    private static final Float DEFAULT_STAR_SCORE = 1F;
    private static final Float UPDATED_STAR_SCORE = 2F;

    private static final Duration DEFAULT_DUREE = Duration.ofHours(6);
    private static final Duration UPDATED_DUREE = Duration.ofHours(12);

    private static final Saison DEFAULT_SAISON = Saison.ETE;
    private static final Saison UPDATED_SAISON = Saison.AUTOMNE;

    private static final TourStatus DEFAULT_STATUS = TourStatus.ACTIVE;
    private static final TourStatus UPDATED_STATUS = TourStatus.DISABLED;

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
            .imageContent(DEFAULT_IMAGE_CONTENT)
            .imageContentContentType(DEFAULT_IMAGE_CONTENT_CONTENT_TYPE)
            .videoContent(DEFAULT_VIDEO_CONTENT)
            .videoContentContentType(DEFAULT_VIDEO_CONTENT_CONTENT_TYPE)
            .conseil(DEFAULT_CONSEIL)
            .prixTTC(DEFAULT_PRIX_TTC)
            .description(DEFAULT_DESCRIPTION)
            .remise(DEFAULT_REMISE)
            .prixRemise(DEFAULT_PRIX_REMISE)
            .starScore(DEFAULT_STAR_SCORE)
            .duree(DEFAULT_DUREE)
            .saison(DEFAULT_SAISON)
            .status(DEFAULT_STATUS);
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
            .imageContent(UPDATED_IMAGE_CONTENT)
            .imageContentContentType(UPDATED_IMAGE_CONTENT_CONTENT_TYPE)
            .videoContent(UPDATED_VIDEO_CONTENT)
            .videoContentContentType(UPDATED_VIDEO_CONTENT_CONTENT_TYPE)
            .conseil(UPDATED_CONSEIL)
            .prixTTC(UPDATED_PRIX_TTC)
            .description(UPDATED_DESCRIPTION)
            .remise(UPDATED_REMISE)
            .prixRemise(UPDATED_PRIX_REMISE)
            .starScore(UPDATED_STAR_SCORE)
            .duree(UPDATED_DUREE)
            .saison(UPDATED_SAISON)
            .status(UPDATED_STATUS);
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
        assertThat(testTour.getImageContent()).isEqualTo(DEFAULT_IMAGE_CONTENT);
        assertThat(testTour.getImageContentContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_CONTENT_TYPE);
        assertThat(testTour.getVideoContent()).isEqualTo(DEFAULT_VIDEO_CONTENT);
        assertThat(testTour.getVideoContentContentType()).isEqualTo(DEFAULT_VIDEO_CONTENT_CONTENT_TYPE);
        assertThat(testTour.getConseil()).isEqualTo(DEFAULT_CONSEIL);
        assertThat(testTour.getPrixTTC()).isEqualTo(DEFAULT_PRIX_TTC);
        assertThat(testTour.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTour.isRemise()).isEqualTo(DEFAULT_REMISE);
        assertThat(testTour.getPrixRemise()).isEqualTo(DEFAULT_PRIX_REMISE);
        assertThat(testTour.getStarScore()).isEqualTo(DEFAULT_STAR_SCORE);
        assertThat(testTour.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testTour.getSaison()).isEqualTo(DEFAULT_SAISON);
        assertThat(testTour.getStatus()).isEqualTo(DEFAULT_STATUS);
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
            .andExpect(jsonPath("$.[*].imageContentContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageContent").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_CONTENT))))
            .andExpect(jsonPath("$.[*].videoContentContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].videoContent").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO_CONTENT))))
            .andExpect(jsonPath("$.[*].conseil").value(hasItem(DEFAULT_CONSEIL)))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].remise").value(hasItem(DEFAULT_REMISE.booleanValue())))
            .andExpect(jsonPath("$.[*].prixRemise").value(hasItem(DEFAULT_PRIX_REMISE.doubleValue())))
            .andExpect(jsonPath("$.[*].starScore").value(hasItem(DEFAULT_STAR_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE.toString())))
            .andExpect(jsonPath("$.[*].saison").value(hasItem(DEFAULT_SAISON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
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
            .andExpect(jsonPath("$.imageContentContentType").value(DEFAULT_IMAGE_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageContent").value(Base64Utils.encodeToString(DEFAULT_IMAGE_CONTENT)))
            .andExpect(jsonPath("$.videoContentContentType").value(DEFAULT_VIDEO_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.videoContent").value(Base64Utils.encodeToString(DEFAULT_VIDEO_CONTENT)))
            .andExpect(jsonPath("$.conseil").value(DEFAULT_CONSEIL))
            .andExpect(jsonPath("$.prixTTC").value(DEFAULT_PRIX_TTC.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.remise").value(DEFAULT_REMISE.booleanValue()))
            .andExpect(jsonPath("$.prixRemise").value(DEFAULT_PRIX_REMISE.doubleValue()))
            .andExpect(jsonPath("$.starScore").value(DEFAULT_STAR_SCORE.doubleValue()))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE.toString()))
            .andExpect(jsonPath("$.saison").value(DEFAULT_SAISON.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
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
            .imageContent(UPDATED_IMAGE_CONTENT)
            .imageContentContentType(UPDATED_IMAGE_CONTENT_CONTENT_TYPE)
            .videoContent(UPDATED_VIDEO_CONTENT)
            .videoContentContentType(UPDATED_VIDEO_CONTENT_CONTENT_TYPE)
            .conseil(UPDATED_CONSEIL)
            .prixTTC(UPDATED_PRIX_TTC)
            .description(UPDATED_DESCRIPTION)
            .remise(UPDATED_REMISE)
            .prixRemise(UPDATED_PRIX_REMISE)
            .starScore(UPDATED_STAR_SCORE)
            .duree(UPDATED_DUREE)
            .saison(UPDATED_SAISON)
            .status(UPDATED_STATUS);

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
        assertThat(testTour.getImageContent()).isEqualTo(UPDATED_IMAGE_CONTENT);
        assertThat(testTour.getImageContentContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_CONTENT_TYPE);
        assertThat(testTour.getVideoContent()).isEqualTo(UPDATED_VIDEO_CONTENT);
        assertThat(testTour.getVideoContentContentType()).isEqualTo(UPDATED_VIDEO_CONTENT_CONTENT_TYPE);
        assertThat(testTour.getConseil()).isEqualTo(UPDATED_CONSEIL);
        assertThat(testTour.getPrixTTC()).isEqualTo(UPDATED_PRIX_TTC);
        assertThat(testTour.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTour.isRemise()).isEqualTo(UPDATED_REMISE);
        assertThat(testTour.getPrixRemise()).isEqualTo(UPDATED_PRIX_REMISE);
        assertThat(testTour.getStarScore()).isEqualTo(UPDATED_STAR_SCORE);
        assertThat(testTour.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testTour.getSaison()).isEqualTo(UPDATED_SAISON);
        assertThat(testTour.getStatus()).isEqualTo(UPDATED_STATUS);
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
