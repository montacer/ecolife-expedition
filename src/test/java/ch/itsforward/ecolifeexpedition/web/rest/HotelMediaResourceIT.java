package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.HotelMedia;
import ch.itsforward.ecolifeexpedition.repository.HotelMediaRepository;

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
 * Integration tests for the {@link HotelMediaResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HotelMediaResourceIT {

    private static final String DEFAULT_MEDIA_URL = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_URL = "BBBBBBBBBB";

    private static final MediaType DEFAULT_MEDIA_TYPE = MediaType.TEXT;
    private static final MediaType UPDATED_MEDIA_TYPE = MediaType.AUDIO;

    private static final byte[] DEFAULT_MEDIACONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIACONTENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MEDIACONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIACONTENT_CONTENT_TYPE = "image/png";

    @Autowired
    private HotelMediaRepository hotelMediaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHotelMediaMockMvc;

    private HotelMedia hotelMedia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelMedia createEntity(EntityManager em) {
        HotelMedia hotelMedia = new HotelMedia()
            .mediaUrl(DEFAULT_MEDIA_URL)
            .mediaType(DEFAULT_MEDIA_TYPE)
            .mediacontent(DEFAULT_MEDIACONTENT)
            .mediacontentContentType(DEFAULT_MEDIACONTENT_CONTENT_TYPE);
        return hotelMedia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelMedia createUpdatedEntity(EntityManager em) {
        HotelMedia hotelMedia = new HotelMedia()
            .mediaUrl(UPDATED_MEDIA_URL)
            .mediaType(UPDATED_MEDIA_TYPE)
            .mediacontent(UPDATED_MEDIACONTENT)
            .mediacontentContentType(UPDATED_MEDIACONTENT_CONTENT_TYPE);
        return hotelMedia;
    }

    @BeforeEach
    public void initTest() {
        hotelMedia = createEntity(em);
    }

    @Test
    @Transactional
    public void createHotelMedia() throws Exception {
        int databaseSizeBeforeCreate = hotelMediaRepository.findAll().size();
        // Create the HotelMedia
        restHotelMediaMockMvc.perform(post("/api/hotel-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hotelMedia)))
            .andExpect(status().isCreated());

        // Validate the HotelMedia in the database
        List<HotelMedia> hotelMediaList = hotelMediaRepository.findAll();
        assertThat(hotelMediaList).hasSize(databaseSizeBeforeCreate + 1);
        HotelMedia testHotelMedia = hotelMediaList.get(hotelMediaList.size() - 1);
        assertThat(testHotelMedia.getMediaUrl()).isEqualTo(DEFAULT_MEDIA_URL);
        assertThat(testHotelMedia.getMediaType()).isEqualTo(DEFAULT_MEDIA_TYPE);
        assertThat(testHotelMedia.getMediacontent()).isEqualTo(DEFAULT_MEDIACONTENT);
        assertThat(testHotelMedia.getMediacontentContentType()).isEqualTo(DEFAULT_MEDIACONTENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createHotelMediaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hotelMediaRepository.findAll().size();

        // Create the HotelMedia with an existing ID
        hotelMedia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotelMediaMockMvc.perform(post("/api/hotel-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hotelMedia)))
            .andExpect(status().isBadRequest());

        // Validate the HotelMedia in the database
        List<HotelMedia> hotelMediaList = hotelMediaRepository.findAll();
        assertThat(hotelMediaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHotelMedias() throws Exception {
        // Initialize the database
        hotelMediaRepository.saveAndFlush(hotelMedia);

        // Get all the hotelMediaList
        restHotelMediaMockMvc.perform(get("/api/hotel-medias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotelMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaUrl").value(hasItem(DEFAULT_MEDIA_URL)))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mediacontentContentType").value(hasItem(DEFAULT_MEDIACONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].mediacontent").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIACONTENT))));
    }
    
    @Test
    @Transactional
    public void getHotelMedia() throws Exception {
        // Initialize the database
        hotelMediaRepository.saveAndFlush(hotelMedia);

        // Get the hotelMedia
        restHotelMediaMockMvc.perform(get("/api/hotel-medias/{id}", hotelMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hotelMedia.getId().intValue()))
            .andExpect(jsonPath("$.mediaUrl").value(DEFAULT_MEDIA_URL))
            .andExpect(jsonPath("$.mediaType").value(DEFAULT_MEDIA_TYPE.toString()))
            .andExpect(jsonPath("$.mediacontentContentType").value(DEFAULT_MEDIACONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.mediacontent").value(Base64Utils.encodeToString(DEFAULT_MEDIACONTENT)));
    }
    @Test
    @Transactional
    public void getNonExistingHotelMedia() throws Exception {
        // Get the hotelMedia
        restHotelMediaMockMvc.perform(get("/api/hotel-medias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHotelMedia() throws Exception {
        // Initialize the database
        hotelMediaRepository.saveAndFlush(hotelMedia);

        int databaseSizeBeforeUpdate = hotelMediaRepository.findAll().size();

        // Update the hotelMedia
        HotelMedia updatedHotelMedia = hotelMediaRepository.findById(hotelMedia.getId()).get();
        // Disconnect from session so that the updates on updatedHotelMedia are not directly saved in db
        em.detach(updatedHotelMedia);
        updatedHotelMedia
            .mediaUrl(UPDATED_MEDIA_URL)
            .mediaType(UPDATED_MEDIA_TYPE)
            .mediacontent(UPDATED_MEDIACONTENT)
            .mediacontentContentType(UPDATED_MEDIACONTENT_CONTENT_TYPE);

        restHotelMediaMockMvc.perform(put("/api/hotel-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHotelMedia)))
            .andExpect(status().isOk());

        // Validate the HotelMedia in the database
        List<HotelMedia> hotelMediaList = hotelMediaRepository.findAll();
        assertThat(hotelMediaList).hasSize(databaseSizeBeforeUpdate);
        HotelMedia testHotelMedia = hotelMediaList.get(hotelMediaList.size() - 1);
        assertThat(testHotelMedia.getMediaUrl()).isEqualTo(UPDATED_MEDIA_URL);
        assertThat(testHotelMedia.getMediaType()).isEqualTo(UPDATED_MEDIA_TYPE);
        assertThat(testHotelMedia.getMediacontent()).isEqualTo(UPDATED_MEDIACONTENT);
        assertThat(testHotelMedia.getMediacontentContentType()).isEqualTo(UPDATED_MEDIACONTENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingHotelMedia() throws Exception {
        int databaseSizeBeforeUpdate = hotelMediaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelMediaMockMvc.perform(put("/api/hotel-medias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hotelMedia)))
            .andExpect(status().isBadRequest());

        // Validate the HotelMedia in the database
        List<HotelMedia> hotelMediaList = hotelMediaRepository.findAll();
        assertThat(hotelMediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHotelMedia() throws Exception {
        // Initialize the database
        hotelMediaRepository.saveAndFlush(hotelMedia);

        int databaseSizeBeforeDelete = hotelMediaRepository.findAll().size();

        // Delete the hotelMedia
        restHotelMediaMockMvc.perform(delete("/api/hotel-medias/{id}", hotelMedia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HotelMedia> hotelMediaList = hotelMediaRepository.findAll();
        assertThat(hotelMediaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
