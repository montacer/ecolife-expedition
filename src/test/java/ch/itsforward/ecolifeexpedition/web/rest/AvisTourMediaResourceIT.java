package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.AvisTourMedia;
import ch.itsforward.ecolifeexpedition.repository.AvisTourMediaRepository;

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
import ch.itsforward.ecolifeexpedition.domain.enumeration.Stars;
/**
 * Integration tests for the {@link AvisTourMediaResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvisTourMediaResourceIT {

    private static final MediaType DEFAULT_MEDIA_TYPE = MediaType.TEXT;
    private static final MediaType UPDATED_MEDIA_TYPE = MediaType.AUDIO;

    private static final String DEFAULT_MEDIA_URL = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_MEDIACONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIACONTENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MEDIACONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIACONTENT_CONTENT_TYPE = "image/png";

    private static final Stars DEFAULT_SCORE = Stars.ONE;
    private static final Stars UPDATED_SCORE = Stars.TWO;

    @Autowired
    private AvisTourMediaRepository avisTourMediaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvisTourMediaMockMvc;

    private AvisTourMedia avisTourMedia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisTourMedia createEntity(EntityManager em) {
        AvisTourMedia avisTourMedia = new AvisTourMedia()
            .mediaType(DEFAULT_MEDIA_TYPE)
            .mediaUrl(DEFAULT_MEDIA_URL)
            .mediacontent(DEFAULT_MEDIACONTENT)
            .mediacontentContentType(DEFAULT_MEDIACONTENT_CONTENT_TYPE)
            .score(DEFAULT_SCORE);
        return avisTourMedia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisTourMedia createUpdatedEntity(EntityManager em) {
        AvisTourMedia avisTourMedia = new AvisTourMedia()
            .mediaType(UPDATED_MEDIA_TYPE)
            .mediaUrl(UPDATED_MEDIA_URL)
            .mediacontent(UPDATED_MEDIACONTENT)
            .mediacontentContentType(UPDATED_MEDIACONTENT_CONTENT_TYPE)
            .score(UPDATED_SCORE);
        return avisTourMedia;
    }

    @BeforeEach
    public void initTest() {
        avisTourMedia = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvisTourMedia() throws Exception {
        int databaseSizeBeforeCreate = avisTourMediaRepository.findAll().size();
        // Create the AvisTourMedia
        restAvisTourMediaMockMvc.perform(post("/api/avis-tour-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisTourMedia)))
            .andExpect(status().isCreated());

        // Validate the AvisTourMedia in the database
        List<AvisTourMedia> avisTourMediaList = avisTourMediaRepository.findAll();
        assertThat(avisTourMediaList).hasSize(databaseSizeBeforeCreate + 1);
        AvisTourMedia testAvisTourMedia = avisTourMediaList.get(avisTourMediaList.size() - 1);
        assertThat(testAvisTourMedia.getMediaType()).isEqualTo(DEFAULT_MEDIA_TYPE);
        assertThat(testAvisTourMedia.getMediaUrl()).isEqualTo(DEFAULT_MEDIA_URL);
        assertThat(testAvisTourMedia.getMediacontent()).isEqualTo(DEFAULT_MEDIACONTENT);
        assertThat(testAvisTourMedia.getMediacontentContentType()).isEqualTo(DEFAULT_MEDIACONTENT_CONTENT_TYPE);
        assertThat(testAvisTourMedia.getScore()).isEqualTo(DEFAULT_SCORE);
    }

    @Test
    @Transactional
    public void createAvisTourMediaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avisTourMediaRepository.findAll().size();

        // Create the AvisTourMedia with an existing ID
        avisTourMedia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvisTourMediaMockMvc.perform(post("/api/avis-tour-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisTourMedia)))
            .andExpect(status().isBadRequest());

        // Validate the AvisTourMedia in the database
        List<AvisTourMedia> avisTourMediaList = avisTourMediaRepository.findAll();
        assertThat(avisTourMediaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAvisTourMedias() throws Exception {
        // Initialize the database
        avisTourMediaRepository.saveAndFlush(avisTourMedia);

        // Get all the avisTourMediaList
        restAvisTourMediaMockMvc.perform(get("/api/avis-tour-medias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avisTourMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mediaUrl").value(hasItem(DEFAULT_MEDIA_URL)))
            .andExpect(jsonPath("$.[*].mediacontentContentType").value(hasItem(DEFAULT_MEDIACONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].mediacontent").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIACONTENT))))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.toString())));
    }
    
    @Test
    @Transactional
    public void getAvisTourMedia() throws Exception {
        // Initialize the database
        avisTourMediaRepository.saveAndFlush(avisTourMedia);

        // Get the avisTourMedia
        restAvisTourMediaMockMvc.perform(get("/api/avis-tour-medias/{id}", avisTourMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avisTourMedia.getId().intValue()))
            .andExpect(jsonPath("$.mediaType").value(DEFAULT_MEDIA_TYPE.toString()))
            .andExpect(jsonPath("$.mediaUrl").value(DEFAULT_MEDIA_URL))
            .andExpect(jsonPath("$.mediacontentContentType").value(DEFAULT_MEDIACONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.mediacontent").value(Base64Utils.encodeToString(DEFAULT_MEDIACONTENT)))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAvisTourMedia() throws Exception {
        // Get the avisTourMedia
        restAvisTourMediaMockMvc.perform(get("/api/avis-tour-medias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvisTourMedia() throws Exception {
        // Initialize the database
        avisTourMediaRepository.saveAndFlush(avisTourMedia);

        int databaseSizeBeforeUpdate = avisTourMediaRepository.findAll().size();

        // Update the avisTourMedia
        AvisTourMedia updatedAvisTourMedia = avisTourMediaRepository.findById(avisTourMedia.getId()).get();
        // Disconnect from session so that the updates on updatedAvisTourMedia are not directly saved in db
        em.detach(updatedAvisTourMedia);
        updatedAvisTourMedia
            .mediaType(UPDATED_MEDIA_TYPE)
            .mediaUrl(UPDATED_MEDIA_URL)
            .mediacontent(UPDATED_MEDIACONTENT)
            .mediacontentContentType(UPDATED_MEDIACONTENT_CONTENT_TYPE)
            .score(UPDATED_SCORE);

        restAvisTourMediaMockMvc.perform(put("/api/avis-tour-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvisTourMedia)))
            .andExpect(status().isOk());

        // Validate the AvisTourMedia in the database
        List<AvisTourMedia> avisTourMediaList = avisTourMediaRepository.findAll();
        assertThat(avisTourMediaList).hasSize(databaseSizeBeforeUpdate);
        AvisTourMedia testAvisTourMedia = avisTourMediaList.get(avisTourMediaList.size() - 1);
        assertThat(testAvisTourMedia.getMediaType()).isEqualTo(UPDATED_MEDIA_TYPE);
        assertThat(testAvisTourMedia.getMediaUrl()).isEqualTo(UPDATED_MEDIA_URL);
        assertThat(testAvisTourMedia.getMediacontent()).isEqualTo(UPDATED_MEDIACONTENT);
        assertThat(testAvisTourMedia.getMediacontentContentType()).isEqualTo(UPDATED_MEDIACONTENT_CONTENT_TYPE);
        assertThat(testAvisTourMedia.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void updateNonExistingAvisTourMedia() throws Exception {
        int databaseSizeBeforeUpdate = avisTourMediaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvisTourMediaMockMvc.perform(put("/api/avis-tour-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisTourMedia)))
            .andExpect(status().isBadRequest());

        // Validate the AvisTourMedia in the database
        List<AvisTourMedia> avisTourMediaList = avisTourMediaRepository.findAll();
        assertThat(avisTourMediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvisTourMedia() throws Exception {
        // Initialize the database
        avisTourMediaRepository.saveAndFlush(avisTourMedia);

        int databaseSizeBeforeDelete = avisTourMediaRepository.findAll().size();

        // Delete the avisTourMedia
        restAvisTourMediaMockMvc.perform(delete("/api/avis-tour-medias/{id}", avisTourMedia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AvisTourMedia> avisTourMediaList = avisTourMediaRepository.findAll();
        assertThat(avisTourMediaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
