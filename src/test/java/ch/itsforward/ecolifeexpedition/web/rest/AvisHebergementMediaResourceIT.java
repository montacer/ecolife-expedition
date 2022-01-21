package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.AvisHebergementMedia;
import ch.itsforward.ecolifeexpedition.repository.AvisHebergementMediaRepository;

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
 * Integration tests for the {@link AvisHebergementMediaResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvisHebergementMediaResourceIT {

    private static final MediaType DEFAULT_MEDIA_TYPE = MediaType.TEXT;
    private static final MediaType UPDATED_MEDIA_TYPE = MediaType.AUDIO;

    private static final String DEFAULT_MEDIA_URL = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_MEDIACONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIACONTENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MEDIACONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIACONTENT_CONTENT_TYPE = "image/png";

    @Autowired
    private AvisHebergementMediaRepository avisHebergementMediaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvisHebergementMediaMockMvc;

    private AvisHebergementMedia avisHebergementMedia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisHebergementMedia createEntity(EntityManager em) {
        AvisHebergementMedia avisHebergementMedia = new AvisHebergementMedia()
            .mediaType(DEFAULT_MEDIA_TYPE)
            .mediaUrl(DEFAULT_MEDIA_URL)
            .mediacontent(DEFAULT_MEDIACONTENT)
            .mediacontentContentType(DEFAULT_MEDIACONTENT_CONTENT_TYPE);
        return avisHebergementMedia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisHebergementMedia createUpdatedEntity(EntityManager em) {
        AvisHebergementMedia avisHebergementMedia = new AvisHebergementMedia()
            .mediaType(UPDATED_MEDIA_TYPE)
            .mediaUrl(UPDATED_MEDIA_URL)
            .mediacontent(UPDATED_MEDIACONTENT)
            .mediacontentContentType(UPDATED_MEDIACONTENT_CONTENT_TYPE);
        return avisHebergementMedia;
    }

    @BeforeEach
    public void initTest() {
        avisHebergementMedia = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvisHebergementMedia() throws Exception {
        int databaseSizeBeforeCreate = avisHebergementMediaRepository.findAll().size();
        // Create the AvisHebergementMedia
        restAvisHebergementMediaMockMvc.perform(post("/api/avis-hebergement-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisHebergementMedia)))
            .andExpect(status().isCreated());

        // Validate the AvisHebergementMedia in the database
        List<AvisHebergementMedia> avisHebergementMediaList = avisHebergementMediaRepository.findAll();
        assertThat(avisHebergementMediaList).hasSize(databaseSizeBeforeCreate + 1);
        AvisHebergementMedia testAvisHebergementMedia = avisHebergementMediaList.get(avisHebergementMediaList.size() - 1);
        assertThat(testAvisHebergementMedia.getMediaType()).isEqualTo(DEFAULT_MEDIA_TYPE);
        assertThat(testAvisHebergementMedia.getMediaUrl()).isEqualTo(DEFAULT_MEDIA_URL);
        assertThat(testAvisHebergementMedia.getMediacontent()).isEqualTo(DEFAULT_MEDIACONTENT);
        assertThat(testAvisHebergementMedia.getMediacontentContentType()).isEqualTo(DEFAULT_MEDIACONTENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAvisHebergementMediaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avisHebergementMediaRepository.findAll().size();

        // Create the AvisHebergementMedia with an existing ID
        avisHebergementMedia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvisHebergementMediaMockMvc.perform(post("/api/avis-hebergement-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisHebergementMedia)))
            .andExpect(status().isBadRequest());

        // Validate the AvisHebergementMedia in the database
        List<AvisHebergementMedia> avisHebergementMediaList = avisHebergementMediaRepository.findAll();
        assertThat(avisHebergementMediaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAvisHebergementMedias() throws Exception {
        // Initialize the database
        avisHebergementMediaRepository.saveAndFlush(avisHebergementMedia);

        // Get all the avisHebergementMediaList
        restAvisHebergementMediaMockMvc.perform(get("/api/avis-hebergement-medias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avisHebergementMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mediaUrl").value(hasItem(DEFAULT_MEDIA_URL)))
            .andExpect(jsonPath("$.[*].mediacontentContentType").value(hasItem(DEFAULT_MEDIACONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].mediacontent").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIACONTENT))));
    }
    
    @Test
    @Transactional
    public void getAvisHebergementMedia() throws Exception {
        // Initialize the database
        avisHebergementMediaRepository.saveAndFlush(avisHebergementMedia);

        // Get the avisHebergementMedia
        restAvisHebergementMediaMockMvc.perform(get("/api/avis-hebergement-medias/{id}", avisHebergementMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avisHebergementMedia.getId().intValue()))
            .andExpect(jsonPath("$.mediaType").value(DEFAULT_MEDIA_TYPE.toString()))
            .andExpect(jsonPath("$.mediaUrl").value(DEFAULT_MEDIA_URL))
            .andExpect(jsonPath("$.mediacontentContentType").value(DEFAULT_MEDIACONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.mediacontent").value(Base64Utils.encodeToString(DEFAULT_MEDIACONTENT)));
    }
    @Test
    @Transactional
    public void getNonExistingAvisHebergementMedia() throws Exception {
        // Get the avisHebergementMedia
        restAvisHebergementMediaMockMvc.perform(get("/api/avis-hebergement-medias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvisHebergementMedia() throws Exception {
        // Initialize the database
        avisHebergementMediaRepository.saveAndFlush(avisHebergementMedia);

        int databaseSizeBeforeUpdate = avisHebergementMediaRepository.findAll().size();

        // Update the avisHebergementMedia
        AvisHebergementMedia updatedAvisHebergementMedia = avisHebergementMediaRepository.findById(avisHebergementMedia.getId()).get();
        // Disconnect from session so that the updates on updatedAvisHebergementMedia are not directly saved in db
        em.detach(updatedAvisHebergementMedia);
        updatedAvisHebergementMedia
            .mediaType(UPDATED_MEDIA_TYPE)
            .mediaUrl(UPDATED_MEDIA_URL)
            .mediacontent(UPDATED_MEDIACONTENT)
            .mediacontentContentType(UPDATED_MEDIACONTENT_CONTENT_TYPE);

        restAvisHebergementMediaMockMvc.perform(put("/api/avis-hebergement-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvisHebergementMedia)))
            .andExpect(status().isOk());

        // Validate the AvisHebergementMedia in the database
        List<AvisHebergementMedia> avisHebergementMediaList = avisHebergementMediaRepository.findAll();
        assertThat(avisHebergementMediaList).hasSize(databaseSizeBeforeUpdate);
        AvisHebergementMedia testAvisHebergementMedia = avisHebergementMediaList.get(avisHebergementMediaList.size() - 1);
        assertThat(testAvisHebergementMedia.getMediaType()).isEqualTo(UPDATED_MEDIA_TYPE);
        assertThat(testAvisHebergementMedia.getMediaUrl()).isEqualTo(UPDATED_MEDIA_URL);
        assertThat(testAvisHebergementMedia.getMediacontent()).isEqualTo(UPDATED_MEDIACONTENT);
        assertThat(testAvisHebergementMedia.getMediacontentContentType()).isEqualTo(UPDATED_MEDIACONTENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAvisHebergementMedia() throws Exception {
        int databaseSizeBeforeUpdate = avisHebergementMediaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvisHebergementMediaMockMvc.perform(put("/api/avis-hebergement-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisHebergementMedia)))
            .andExpect(status().isBadRequest());

        // Validate the AvisHebergementMedia in the database
        List<AvisHebergementMedia> avisHebergementMediaList = avisHebergementMediaRepository.findAll();
        assertThat(avisHebergementMediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvisHebergementMedia() throws Exception {
        // Initialize the database
        avisHebergementMediaRepository.saveAndFlush(avisHebergementMedia);

        int databaseSizeBeforeDelete = avisHebergementMediaRepository.findAll().size();

        // Delete the avisHebergementMedia
        restAvisHebergementMediaMockMvc.perform(delete("/api/avis-hebergement-medias/{id}", avisHebergementMedia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AvisHebergementMedia> avisHebergementMediaList = avisHebergementMediaRepository.findAll();
        assertThat(avisHebergementMediaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
