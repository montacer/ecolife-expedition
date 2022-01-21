package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.Transfert;
import ch.itsforward.ecolifeexpedition.repository.TransfertRepository;

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
 * Integration tests for the {@link TransfertResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransfertResourceIT {

    private static final Float DEFAULT_MONTANT_TTC = 1F;
    private static final Float UPDATED_MONTANT_TTC = 2F;

    @Autowired
    private TransfertRepository transfertRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransfertMockMvc;

    private Transfert transfert;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfert createEntity(EntityManager em) {
        Transfert transfert = new Transfert()
            .montantTTC(DEFAULT_MONTANT_TTC);
        return transfert;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfert createUpdatedEntity(EntityManager em) {
        Transfert transfert = new Transfert()
            .montantTTC(UPDATED_MONTANT_TTC);
        return transfert;
    }

    @BeforeEach
    public void initTest() {
        transfert = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransfert() throws Exception {
        int databaseSizeBeforeCreate = transfertRepository.findAll().size();
        // Create the Transfert
        restTransfertMockMvc.perform(post("/api/transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transfert)))
            .andExpect(status().isCreated());

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeCreate + 1);
        Transfert testTransfert = transfertList.get(transfertList.size() - 1);
        assertThat(testTransfert.getMontantTTC()).isEqualTo(DEFAULT_MONTANT_TTC);
    }

    @Test
    @Transactional
    public void createTransfertWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transfertRepository.findAll().size();

        // Create the Transfert with an existing ID
        transfert.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransfertMockMvc.perform(post("/api/transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transfert)))
            .andExpect(status().isBadRequest());

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTransferts() throws Exception {
        // Initialize the database
        transfertRepository.saveAndFlush(transfert);

        // Get all the transfertList
        restTransfertMockMvc.perform(get("/api/transferts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transfert.getId().intValue())))
            .andExpect(jsonPath("$.[*].montantTTC").value(hasItem(DEFAULT_MONTANT_TTC.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTransfert() throws Exception {
        // Initialize the database
        transfertRepository.saveAndFlush(transfert);

        // Get the transfert
        restTransfertMockMvc.perform(get("/api/transferts/{id}", transfert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transfert.getId().intValue()))
            .andExpect(jsonPath("$.montantTTC").value(DEFAULT_MONTANT_TTC.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTransfert() throws Exception {
        // Get the transfert
        restTransfertMockMvc.perform(get("/api/transferts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransfert() throws Exception {
        // Initialize the database
        transfertRepository.saveAndFlush(transfert);

        int databaseSizeBeforeUpdate = transfertRepository.findAll().size();

        // Update the transfert
        Transfert updatedTransfert = transfertRepository.findById(transfert.getId()).get();
        // Disconnect from session so that the updates on updatedTransfert are not directly saved in db
        em.detach(updatedTransfert);
        updatedTransfert
            .montantTTC(UPDATED_MONTANT_TTC);

        restTransfertMockMvc.perform(put("/api/transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransfert)))
            .andExpect(status().isOk());

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
        Transfert testTransfert = transfertList.get(transfertList.size() - 1);
        assertThat(testTransfert.getMontantTTC()).isEqualTo(UPDATED_MONTANT_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingTransfert() throws Exception {
        int databaseSizeBeforeUpdate = transfertRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransfertMockMvc.perform(put("/api/transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transfert)))
            .andExpect(status().isBadRequest());

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransfert() throws Exception {
        // Initialize the database
        transfertRepository.saveAndFlush(transfert);

        int databaseSizeBeforeDelete = transfertRepository.findAll().size();

        // Delete the transfert
        restTransfertMockMvc.perform(delete("/api/transferts/{id}", transfert.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
