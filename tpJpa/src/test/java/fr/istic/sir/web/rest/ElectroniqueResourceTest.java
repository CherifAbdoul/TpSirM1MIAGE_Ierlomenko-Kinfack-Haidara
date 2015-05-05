package fr.istic.sir.web.rest;

import fr.istic.sir.Application;
import fr.istic.sir.domain.Electronique;
import fr.istic.sir.repository.ElectroniqueRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ElectroniqueResource REST controller.
 *
 * @see ElectroniqueResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ElectroniqueResourceTest {


    @Inject
    private ElectroniqueRepository electroniqueRepository;

    private MockMvc restElectroniqueMockMvc;

    private Electronique electronique;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ElectroniqueResource electroniqueResource = new ElectroniqueResource();
        ReflectionTestUtils.setField(electroniqueResource, "electroniqueRepository", electroniqueRepository);
        this.restElectroniqueMockMvc = MockMvcBuilders.standaloneSetup(electroniqueResource).build();
    }

    @Before
    public void initTest() {
        electronique = new Electronique();
    }

    @Test
    @Transactional
    public void createElectronique() throws Exception {
        // Validate the database is empty
        assertThat(electroniqueRepository.findAll()).hasSize(0);

        // Create the Electronique
        restElectroniqueMockMvc.perform(post("/api/electroniques")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(electronique)))
                .andExpect(status().isCreated());

        // Validate the Electronique in the database
        List<Electronique> electroniques = electroniqueRepository.findAll();
        assertThat(electroniques).hasSize(1);
        Electronique testElectronique = electroniques.iterator().next();
    }

    @Test
    @Transactional
    public void getAllElectroniques() throws Exception {
        // Initialize the database
        electroniqueRepository.saveAndFlush(electronique);

        // Get all the electroniques
        restElectroniqueMockMvc.perform(get("/api/electroniques"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(electronique.getId().intValue()));
    }

    @Test
    @Transactional
    public void getElectronique() throws Exception {
        // Initialize the database
        electroniqueRepository.saveAndFlush(electronique);

        // Get the electronique
        restElectroniqueMockMvc.perform(get("/api/electroniques/{id}", electronique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(electronique.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingElectronique() throws Exception {
        // Get the electronique
        restElectroniqueMockMvc.perform(get("/api/electroniques/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElectronique() throws Exception {
        // Initialize the database
        electroniqueRepository.saveAndFlush(electronique);

        // Update the electronique
        restElectroniqueMockMvc.perform(put("/api/electroniques")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(electronique)))
                .andExpect(status().isOk());

        // Validate the Electronique in the database
        List<Electronique> electroniques = electroniqueRepository.findAll();
        assertThat(electroniques).hasSize(1);
        Electronique testElectronique = electroniques.iterator().next();
    }

    @Test
    @Transactional
    public void deleteElectronique() throws Exception {
        // Initialize the database
        electroniqueRepository.saveAndFlush(electronique);

        // Get the electronique
        restElectroniqueMockMvc.perform(delete("/api/electroniques/{id}", electronique.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Electronique> electroniques = electroniqueRepository.findAll();
        assertThat(electroniques).hasSize(0);
    }
}
