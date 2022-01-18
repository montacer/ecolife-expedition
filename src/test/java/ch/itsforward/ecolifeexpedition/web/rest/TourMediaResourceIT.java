package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.TourMedia;
import ch.itsforward.ecolifeexpedition.repository.TourMediaRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.itsforward.ecolifeexpedition.domain.enumeration.MediaType;
/**
 * Integration tests for the {@link TourMediaResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TourMediaResourceIT {

    private static final String DEFAULT_MEDIA_URL = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_URL = "BBBBBBBBBB";

    private static final MediaType DEFAULT_MEDIA_TYPE = MediaType.AUDIO;
    private static final MediaType UPDATED_MEDIA_TYPE = MediaType.VIDEO;

    private static final byte[] DEFAULT_MEDIACONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIACONTENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MEDIACONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIACONTENT_CONTENT_TYPE = "image/png";

    @Autowired
    private TourMediaRepository tourMediaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTourMediaMockMvc;

    private TourMedia tourMedia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourMedia createEntity(EntityManager em) {
        TourMedia tourMedia = new TourMedia()
            .mediaUrl(DEFAULT_MEDIA_URL)
            .mediaType(DEFAULT_MEDIA_TYPE)
            .mediacontent(DEFAULT_MEDIACONTENT)
            .mediacontentContentType(DEFAULT_MEDIACONTENT_CONTENT_TYPE);
        return tourMedia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourMedia createUpdatedEntity(EntityManager em) {
        TourMedia tourMedia = new TourMedia()
            .mediaUrl(UPDATED_MEDIA_URL)
            .mediaType(UPDATED_MEDIA_TYPE)
            .mediacontent(UPDATED_MEDIACONTENT)
            .mediacontentContentType(UPDATED_MEDIACONTENT_CONTENT_TYPE);
        return tourMedia;
    }

    @BeforeEach
    public void initTest() {
        tourMedia = createEntity(em);
    }

    @Test
    @Transactional
    public void createTourMedia() throws Exception {
        int databaseSizeBeforeCreate = tourMediaRepository.findAll().size();
        // Create the TourMedia
        restTourMediaMockMvc.perform(post("/api/tour-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tourMedia)))
            .andExpect(status().isCreated());

        // Validate the TourMedia in the database
        List<TourMedia> tourMediaList = tourMediaRepository.findAll();
        assertThat(tourMediaList).hasSize(databaseSizeBeforeCreate + 1);
        TourMedia testTourMedia = tourMediaList.get(tourMediaList.size() - 1);
        assertThat(testTourMedia.getMediaUrl()).isEqualTo(DEFAULT_MEDIA_URL);
        assertThat(testTourMedia.getMediaType()).isEqualTo(DEFAULT_MEDIA_TYPE);
        assertThat(testTourMedia.getMediacontent()).isEqualTo(DEFAULT_MEDIACONTENT);
        assertThat(testTourMedia.getMediacontentContentType()).isEqualTo(DEFAULT_MEDIACONTENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createTourMediaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tourMediaRepository.findAll().size();

        // Create the TourMedia with an existing ID
        tourMedia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTourMediaMockMvc.perform(post("/api/tour-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tourMedia)))
            .andExpect(status().isBadRequest());

        // Validate the TourMedia in the database
        List<TourMedia> tourMediaList = tourMediaRepository.findAll();
        assertThat(tourMediaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTourMedias() throws Exception {
        // Initialize the database
        tourMediaRepository.saveAndFlush(tourMedia);

        // Get all the tourMediaList
        restTourMediaMockMvc.perform(get("/api/tour-medias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tourMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaUrl").value(hasItem(DEFAULT_MEDIA_URL)))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mediacontentContentType").value(hasItem(DEFAULT_MEDIACONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].mediacontent").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIACONTENT))));
    }
    
    @Test
    @Transactional
    public void getTourMedia() throws Exception {
        // Initialize the database
        tourMediaRepository.saveAndFlush(tourMedia);

        // Get the tourMedia
        restTourMediaMockMvc.perform(get("/api/tour-medias/{id}", tourMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tourMedia.getId().intValue()))
            .andExpect(jsonPath("$.mediaUrl").value(DEFAULT_MEDIA_URL))
            .andExpect(jsonPath("$.mediaType").value(DEFAULT_MEDIA_TYPE.toString()))
            .andExpect(jsonPath("$.mediacontentContentType").value(DEFAULT_MEDIACONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.mediacontent").value(Base64Utils.encodeToString(DEFAULT_MEDIACONTENT)));
    }
    @Test
    @Transactional
    public void getNonExistingTourMedia() throws Exception {
        // Get the tourMedia
        restTourMediaMockMvc.perform(get("/api/tour-medias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTourMedia() throws Exception {
        // Initialize the database
        tourMediaRepository.saveAndFlush(tourMedia);

        int databaseSizeBeforeUpdate = tourMediaRepository.findAll().size();

        // Update the tourMedia
        TourMedia updatedTourMedia = tourMediaRepository.findById(tourMedia.getId()).get();
        // Disconnect from session so that the updates on updatedTourMedia are not directly saved in db
        em.detach(updatedTourMedia);
        updatedTourMedia
            .mediaUrl(UPDATED_MEDIA_URL)
            .mediaType(UPDATED_MEDIA_TYPE)
            .mediacontent(UPDATED_MEDIACONTENT)
            .mediacontentContentType(UPDATED_MEDIACONTENT_CONTENT_TYPE);

        restTourMediaMockMvc.perform(put("/api/tour-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTourMedia)))
            .andExpect(status().isOk());

        // Validate the TourMedia in the database
        List<TourMedia> tourMediaList = tourMediaRepository.findAll();
        assertThat(tourMediaList).hasSize(databaseSizeBeforeUpdate);
        TourMedia testTourMedia = tourMediaList.get(tourMediaList.size() - 1);
        assertThat(testTourMedia.getMediaUrl()).isEqualTo(UPDATED_MEDIA_URL);
        assertThat(testTourMedia.getMediaType()).isEqualTo(UPDATED_MEDIA_TYPE);
        assertThat(testTourMedia.getMediacontent()).isEqualTo(UPDATED_MEDIACONTENT);
        assertThat(testTourMedia.getMediacontentContentType()).isEqualTo(UPDATED_MEDIACONTENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTourMedia() throws Exception {
        int databaseSizeBeforeUpdate = tourMediaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourMediaMockMvc.perform(put("/api/tour-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tourMedia)))
            .andExpect(status().isBadRequest());

        // Validate the TourMedia in the database
        List<TourMedia> tourMediaList = tourMediaRepository.findAll();
        assertThat(tourMediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTourMedia() throws Exception {
        // Initialize the database
        tourMediaRepository.saveAndFlush(tourMedia);

        int databaseSizeBeforeDelete = tourMediaRepository.findAll().size();

        // Delete the tourMedia
        restTourMediaMockMvc.perform(delete("/api/tour-medias/{id}", tourMedia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TourMedia> tourMediaList = tourMediaRepository.findAll();
        assertThat(tourMediaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
