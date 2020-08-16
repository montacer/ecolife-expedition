package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.Chambre;
import ch.itsforward.ecolifeexpedition.repository.ChambreRepository;

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
 * Integration tests for the {@link ChambreResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChambreResourceIT {

    private static final Float DEFAULT_PRIX_TTC = 1F;
    private static final Float UPDATED_PRIX_TTC = 2F;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChambreMockMvc;

    private Chambre chambre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chambre createEntity(EntityManager em) {
        Chambre chambre = new Chambre()
            .prixTTC(DEFAULT_PRIX_TTC);
        return chambre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chambre createUpdatedEntity(EntityManager em) {
        Chambre chambre = new Chambre()
            .prixTTC(UPDATED_PRIX_TTC);
        return chambre;
    }

    @BeforeEach
    public void initTest() {
        chambre = createEntity(em);
    }

    @Test
    @Transactional
    public void createChambre() throws Exception {
        int databaseSizeBeforeCreate = chambreRepository.findAll().size();
        // Create the Chambre
        restChambreMockMvc.perform(post("/api/chambres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chambre)))
            .andExpect(status().isCreated());

        // Validate the Chambre in the database
        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeCreate + 1);
        Chambre testChambre = chambreList.get(chambreList.size() - 1);
        assertThat(testChambre.getPrixTTC()).isEqualTo(DEFAULT_PRIX_TTC);
    }

    @Test
    @Transactional
    public void createChambreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chambreRepository.findAll().size();

        // Create the Chambre with an existing ID
        chambre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChambreMockMvc.perform(post("/api/chambres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chambre)))
            .andExpect(status().isBadRequest());

        // Validate the Chambre in the database
        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllChambres() throws Exception {
        // Initialize the database
        chambreRepository.saveAndFlush(chambre);

        // Get all the chambreList
        restChambreMockMvc.perform(get("/api/chambres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chambre.getId().intValue())))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getChambre() throws Exception {
        // Initialize the database
        chambreRepository.saveAndFlush(chambre);

        // Get the chambre
        restChambreMockMvc.perform(get("/api/chambres/{id}", chambre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chambre.getId().intValue()))
            .andExpect(jsonPath("$.prixTTC").value(DEFAULT_PRIX_TTC.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingChambre() throws Exception {
        // Get the chambre
        restChambreMockMvc.perform(get("/api/chambres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChambre() throws Exception {
        // Initialize the database
        chambreRepository.saveAndFlush(chambre);

        int databaseSizeBeforeUpdate = chambreRepository.findAll().size();

        // Update the chambre
        Chambre updatedChambre = chambreRepository.findById(chambre.getId()).get();
        // Disconnect from session so that the updates on updatedChambre are not directly saved in db
        em.detach(updatedChambre);
        updatedChambre
            .prixTTC(UPDATED_PRIX_TTC);

        restChambreMockMvc.perform(put("/api/chambres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedChambre)))
            .andExpect(status().isOk());

        // Validate the Chambre in the database
        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeUpdate);
        Chambre testChambre = chambreList.get(chambreList.size() - 1);
        assertThat(testChambre.getPrixTTC()).isEqualTo(UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingChambre() throws Exception {
        int databaseSizeBeforeUpdate = chambreRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChambreMockMvc.perform(put("/api/chambres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chambre)))
            .andExpect(status().isBadRequest());

        // Validate the Chambre in the database
        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChambre() throws Exception {
        // Initialize the database
        chambreRepository.saveAndFlush(chambre);

        int databaseSizeBeforeDelete = chambreRepository.findAll().size();

        // Delete the chambre
        restChambreMockMvc.perform(delete("/api/chambres/{id}", chambre.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
