package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.Hotel;
import ch.itsforward.ecolifeexpedition.repository.HotelRepository;

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
 * Integration tests for the {@link HotelResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HotelResourceIT {

    private static final String DEFAULT_LIBELLE_HOTEL = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_HOTEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_CLASSE_HOTEL = 1;
    private static final Integer UPDATED_CLASSE_HOTEL = 2;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHotelMockMvc;

    private Hotel hotel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hotel createEntity(EntityManager em) {
        Hotel hotel = new Hotel()
            .libelleHotel(DEFAULT_LIBELLE_HOTEL)
            .classeHotel(DEFAULT_CLASSE_HOTEL);
        return hotel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hotel createUpdatedEntity(EntityManager em) {
        Hotel hotel = new Hotel()
            .libelleHotel(UPDATED_LIBELLE_HOTEL)
            .classeHotel(UPDATED_CLASSE_HOTEL);
        return hotel;
    }

    @BeforeEach
    public void initTest() {
        hotel = createEntity(em);
    }

    @Test
    @Transactional
    public void createHotel() throws Exception {
        int databaseSizeBeforeCreate = hotelRepository.findAll().size();
        // Create the Hotel
        restHotelMockMvc.perform(post("/api/hotels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hotel)))
            .andExpect(status().isCreated());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeCreate + 1);
        Hotel testHotel = hotelList.get(hotelList.size() - 1);
        assertThat(testHotel.getLibelleHotel()).isEqualTo(DEFAULT_LIBELLE_HOTEL);
        assertThat(testHotel.getClasseHotel()).isEqualTo(DEFAULT_CLASSE_HOTEL);
    }

    @Test
    @Transactional
    public void createHotelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hotelRepository.findAll().size();

        // Create the Hotel with an existing ID
        hotel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotelMockMvc.perform(post("/api/hotels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hotel)))
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHotels() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList
        restHotelMockMvc.perform(get("/api/hotels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotel.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleHotel").value(hasItem(DEFAULT_LIBELLE_HOTEL)))
            .andExpect(jsonPath("$.[*].classeHotel").value(hasItem(DEFAULT_CLASSE_HOTEL)));
    }
    
    @Test
    @Transactional
    public void getHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get the hotel
        restHotelMockMvc.perform(get("/api/hotels/{id}", hotel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hotel.getId().intValue()))
            .andExpect(jsonPath("$.libelleHotel").value(DEFAULT_LIBELLE_HOTEL))
            .andExpect(jsonPath("$.classeHotel").value(DEFAULT_CLASSE_HOTEL));
    }
    @Test
    @Transactional
    public void getNonExistingHotel() throws Exception {
        // Get the hotel
        restHotelMockMvc.perform(get("/api/hotels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        int databaseSizeBeforeUpdate = hotelRepository.findAll().size();

        // Update the hotel
        Hotel updatedHotel = hotelRepository.findById(hotel.getId()).get();
        // Disconnect from session so that the updates on updatedHotel are not directly saved in db
        em.detach(updatedHotel);
        updatedHotel
            .libelleHotel(UPDATED_LIBELLE_HOTEL)
            .classeHotel(UPDATED_CLASSE_HOTEL);

        restHotelMockMvc.perform(put("/api/hotels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHotel)))
            .andExpect(status().isOk());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeUpdate);
        Hotel testHotel = hotelList.get(hotelList.size() - 1);
        assertThat(testHotel.getLibelleHotel()).isEqualTo(UPDATED_LIBELLE_HOTEL);
        assertThat(testHotel.getClasseHotel()).isEqualTo(UPDATED_CLASSE_HOTEL);
    }

    @Test
    @Transactional
    public void updateNonExistingHotel() throws Exception {
        int databaseSizeBeforeUpdate = hotelRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelMockMvc.perform(put("/api/hotels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hotel)))
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        int databaseSizeBeforeDelete = hotelRepository.findAll().size();

        // Delete the hotel
        restHotelMockMvc.perform(delete("/api/hotels/{id}", hotel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
