package com.rdv.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rdv.IntegrationTest;
import com.rdv.domain.RegistroDeVacunacion;
import com.rdv.domain.enumeration.TipoDeVacuna;
import com.rdv.repository.RegistroDeVacunacionRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RegistroDeVacunacionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegistroDeVacunacionResourceIT {

    private static final TipoDeVacuna DEFAULT_TIPO_DE_VACUNA = TipoDeVacuna.SPUTNIK;
    private static final TipoDeVacuna UPDATED_TIPO_DE_VACUNA = TipoDeVacuna.ASTRAZENECA;

    private static final LocalDate DEFAULT_FECHA_DE_VACUNACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_DE_VACUNACION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUMERO_DE_DOSIS = 1;
    private static final Integer UPDATED_NUMERO_DE_DOSIS = 2;

    private static final String ENTITY_API_URL = "/api/registro-de-vacunacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegistroDeVacunacionRepository registroDeVacunacionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegistroDeVacunacionMockMvc;

    private RegistroDeVacunacion registroDeVacunacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistroDeVacunacion createEntity(EntityManager em) {
        RegistroDeVacunacion registroDeVacunacion = new RegistroDeVacunacion()
            .tipoDeVacuna(DEFAULT_TIPO_DE_VACUNA)
            .fechaDeVacunacion(DEFAULT_FECHA_DE_VACUNACION)
            .numeroDeDosis(DEFAULT_NUMERO_DE_DOSIS);
        return registroDeVacunacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistroDeVacunacion createUpdatedEntity(EntityManager em) {
        RegistroDeVacunacion registroDeVacunacion = new RegistroDeVacunacion()
            .tipoDeVacuna(UPDATED_TIPO_DE_VACUNA)
            .fechaDeVacunacion(UPDATED_FECHA_DE_VACUNACION)
            .numeroDeDosis(UPDATED_NUMERO_DE_DOSIS);
        return registroDeVacunacion;
    }

    @BeforeEach
    public void initTest() {
        registroDeVacunacion = createEntity(em);
    }

    @Test
    @Transactional
    void createRegistroDeVacunacion() throws Exception {
        int databaseSizeBeforeCreate = registroDeVacunacionRepository.findAll().size();
        // Create the RegistroDeVacunacion
        restRegistroDeVacunacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroDeVacunacion))
            )
            .andExpect(status().isCreated());

        // Validate the RegistroDeVacunacion in the database
        List<RegistroDeVacunacion> registroDeVacunacionList = registroDeVacunacionRepository.findAll();
        assertThat(registroDeVacunacionList).hasSize(databaseSizeBeforeCreate + 1);
        RegistroDeVacunacion testRegistroDeVacunacion = registroDeVacunacionList.get(registroDeVacunacionList.size() - 1);
        assertThat(testRegistroDeVacunacion.getTipoDeVacuna()).isEqualTo(DEFAULT_TIPO_DE_VACUNA);
        assertThat(testRegistroDeVacunacion.getFechaDeVacunacion()).isEqualTo(DEFAULT_FECHA_DE_VACUNACION);
        assertThat(testRegistroDeVacunacion.getNumeroDeDosis()).isEqualTo(DEFAULT_NUMERO_DE_DOSIS);
    }

    @Test
    @Transactional
    void createRegistroDeVacunacionWithExistingId() throws Exception {
        // Create the RegistroDeVacunacion with an existing ID
        registroDeVacunacion.setId(1L);

        int databaseSizeBeforeCreate = registroDeVacunacionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistroDeVacunacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroDeVacunacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistroDeVacunacion in the database
        List<RegistroDeVacunacion> registroDeVacunacionList = registroDeVacunacionRepository.findAll();
        assertThat(registroDeVacunacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoDeVacunaIsRequired() throws Exception {
        int databaseSizeBeforeTest = registroDeVacunacionRepository.findAll().size();
        // set the field null
        registroDeVacunacion.setTipoDeVacuna(null);

        // Create the RegistroDeVacunacion, which fails.

        restRegistroDeVacunacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroDeVacunacion))
            )
            .andExpect(status().isBadRequest());

        List<RegistroDeVacunacion> registroDeVacunacionList = registroDeVacunacionRepository.findAll();
        assertThat(registroDeVacunacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaDeVacunacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = registroDeVacunacionRepository.findAll().size();
        // set the field null
        registroDeVacunacion.setFechaDeVacunacion(null);

        // Create the RegistroDeVacunacion, which fails.

        restRegistroDeVacunacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroDeVacunacion))
            )
            .andExpect(status().isBadRequest());

        List<RegistroDeVacunacion> registroDeVacunacionList = registroDeVacunacionRepository.findAll();
        assertThat(registroDeVacunacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroDeDosisIsRequired() throws Exception {
        int databaseSizeBeforeTest = registroDeVacunacionRepository.findAll().size();
        // set the field null
        registroDeVacunacion.setNumeroDeDosis(null);

        // Create the RegistroDeVacunacion, which fails.

        restRegistroDeVacunacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroDeVacunacion))
            )
            .andExpect(status().isBadRequest());

        List<RegistroDeVacunacion> registroDeVacunacionList = registroDeVacunacionRepository.findAll();
        assertThat(registroDeVacunacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRegistroDeVacunacions() throws Exception {
        // Initialize the database
        registroDeVacunacionRepository.saveAndFlush(registroDeVacunacion);

        // Get all the registroDeVacunacionList
        restRegistroDeVacunacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registroDeVacunacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDeVacuna").value(hasItem(DEFAULT_TIPO_DE_VACUNA.toString())))
            .andExpect(jsonPath("$.[*].fechaDeVacunacion").value(hasItem(DEFAULT_FECHA_DE_VACUNACION.toString())))
            .andExpect(jsonPath("$.[*].numeroDeDosis").value(hasItem(DEFAULT_NUMERO_DE_DOSIS)));
    }

    @Test
    @Transactional
    void getRegistroDeVacunacion() throws Exception {
        // Initialize the database
        registroDeVacunacionRepository.saveAndFlush(registroDeVacunacion);

        // Get the registroDeVacunacion
        restRegistroDeVacunacionMockMvc
            .perform(get(ENTITY_API_URL_ID, registroDeVacunacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(registroDeVacunacion.getId().intValue()))
            .andExpect(jsonPath("$.tipoDeVacuna").value(DEFAULT_TIPO_DE_VACUNA.toString()))
            .andExpect(jsonPath("$.fechaDeVacunacion").value(DEFAULT_FECHA_DE_VACUNACION.toString()))
            .andExpect(jsonPath("$.numeroDeDosis").value(DEFAULT_NUMERO_DE_DOSIS));
    }

    @Test
    @Transactional
    void getNonExistingRegistroDeVacunacion() throws Exception {
        // Get the registroDeVacunacion
        restRegistroDeVacunacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRegistroDeVacunacion() throws Exception {
        // Initialize the database
        registroDeVacunacionRepository.saveAndFlush(registroDeVacunacion);

        int databaseSizeBeforeUpdate = registroDeVacunacionRepository.findAll().size();

        // Update the registroDeVacunacion
        RegistroDeVacunacion updatedRegistroDeVacunacion = registroDeVacunacionRepository.findById(registroDeVacunacion.getId()).get();
        // Disconnect from session so that the updates on updatedRegistroDeVacunacion are not directly saved in db
        em.detach(updatedRegistroDeVacunacion);
        updatedRegistroDeVacunacion
            .tipoDeVacuna(UPDATED_TIPO_DE_VACUNA)
            .fechaDeVacunacion(UPDATED_FECHA_DE_VACUNACION)
            .numeroDeDosis(UPDATED_NUMERO_DE_DOSIS);

        restRegistroDeVacunacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRegistroDeVacunacion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRegistroDeVacunacion))
            )
            .andExpect(status().isOk());

        // Validate the RegistroDeVacunacion in the database
        List<RegistroDeVacunacion> registroDeVacunacionList = registroDeVacunacionRepository.findAll();
        assertThat(registroDeVacunacionList).hasSize(databaseSizeBeforeUpdate);
        RegistroDeVacunacion testRegistroDeVacunacion = registroDeVacunacionList.get(registroDeVacunacionList.size() - 1);
        assertThat(testRegistroDeVacunacion.getTipoDeVacuna()).isEqualTo(UPDATED_TIPO_DE_VACUNA);
        assertThat(testRegistroDeVacunacion.getFechaDeVacunacion()).isEqualTo(UPDATED_FECHA_DE_VACUNACION);
        assertThat(testRegistroDeVacunacion.getNumeroDeDosis()).isEqualTo(UPDATED_NUMERO_DE_DOSIS);
    }

    @Test
    @Transactional
    void putNonExistingRegistroDeVacunacion() throws Exception {
        int databaseSizeBeforeUpdate = registroDeVacunacionRepository.findAll().size();
        registroDeVacunacion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistroDeVacunacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, registroDeVacunacion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroDeVacunacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistroDeVacunacion in the database
        List<RegistroDeVacunacion> registroDeVacunacionList = registroDeVacunacionRepository.findAll();
        assertThat(registroDeVacunacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegistroDeVacunacion() throws Exception {
        int databaseSizeBeforeUpdate = registroDeVacunacionRepository.findAll().size();
        registroDeVacunacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroDeVacunacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroDeVacunacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistroDeVacunacion in the database
        List<RegistroDeVacunacion> registroDeVacunacionList = registroDeVacunacionRepository.findAll();
        assertThat(registroDeVacunacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegistroDeVacunacion() throws Exception {
        int databaseSizeBeforeUpdate = registroDeVacunacionRepository.findAll().size();
        registroDeVacunacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroDeVacunacionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registroDeVacunacion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RegistroDeVacunacion in the database
        List<RegistroDeVacunacion> registroDeVacunacionList = registroDeVacunacionRepository.findAll();
        assertThat(registroDeVacunacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegistroDeVacunacionWithPatch() throws Exception {
        // Initialize the database
        registroDeVacunacionRepository.saveAndFlush(registroDeVacunacion);

        int databaseSizeBeforeUpdate = registroDeVacunacionRepository.findAll().size();

        // Update the registroDeVacunacion using partial update
        RegistroDeVacunacion partialUpdatedRegistroDeVacunacion = new RegistroDeVacunacion();
        partialUpdatedRegistroDeVacunacion.setId(registroDeVacunacion.getId());

        partialUpdatedRegistroDeVacunacion.tipoDeVacuna(UPDATED_TIPO_DE_VACUNA);

        restRegistroDeVacunacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistroDeVacunacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistroDeVacunacion))
            )
            .andExpect(status().isOk());

        // Validate the RegistroDeVacunacion in the database
        List<RegistroDeVacunacion> registroDeVacunacionList = registroDeVacunacionRepository.findAll();
        assertThat(registroDeVacunacionList).hasSize(databaseSizeBeforeUpdate);
        RegistroDeVacunacion testRegistroDeVacunacion = registroDeVacunacionList.get(registroDeVacunacionList.size() - 1);
        assertThat(testRegistroDeVacunacion.getTipoDeVacuna()).isEqualTo(UPDATED_TIPO_DE_VACUNA);
        assertThat(testRegistroDeVacunacion.getFechaDeVacunacion()).isEqualTo(DEFAULT_FECHA_DE_VACUNACION);
        assertThat(testRegistroDeVacunacion.getNumeroDeDosis()).isEqualTo(DEFAULT_NUMERO_DE_DOSIS);
    }

    @Test
    @Transactional
    void fullUpdateRegistroDeVacunacionWithPatch() throws Exception {
        // Initialize the database
        registroDeVacunacionRepository.saveAndFlush(registroDeVacunacion);

        int databaseSizeBeforeUpdate = registroDeVacunacionRepository.findAll().size();

        // Update the registroDeVacunacion using partial update
        RegistroDeVacunacion partialUpdatedRegistroDeVacunacion = new RegistroDeVacunacion();
        partialUpdatedRegistroDeVacunacion.setId(registroDeVacunacion.getId());

        partialUpdatedRegistroDeVacunacion
            .tipoDeVacuna(UPDATED_TIPO_DE_VACUNA)
            .fechaDeVacunacion(UPDATED_FECHA_DE_VACUNACION)
            .numeroDeDosis(UPDATED_NUMERO_DE_DOSIS);

        restRegistroDeVacunacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistroDeVacunacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistroDeVacunacion))
            )
            .andExpect(status().isOk());

        // Validate the RegistroDeVacunacion in the database
        List<RegistroDeVacunacion> registroDeVacunacionList = registroDeVacunacionRepository.findAll();
        assertThat(registroDeVacunacionList).hasSize(databaseSizeBeforeUpdate);
        RegistroDeVacunacion testRegistroDeVacunacion = registroDeVacunacionList.get(registroDeVacunacionList.size() - 1);
        assertThat(testRegistroDeVacunacion.getTipoDeVacuna()).isEqualTo(UPDATED_TIPO_DE_VACUNA);
        assertThat(testRegistroDeVacunacion.getFechaDeVacunacion()).isEqualTo(UPDATED_FECHA_DE_VACUNACION);
        assertThat(testRegistroDeVacunacion.getNumeroDeDosis()).isEqualTo(UPDATED_NUMERO_DE_DOSIS);
    }

    @Test
    @Transactional
    void patchNonExistingRegistroDeVacunacion() throws Exception {
        int databaseSizeBeforeUpdate = registroDeVacunacionRepository.findAll().size();
        registroDeVacunacion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistroDeVacunacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, registroDeVacunacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registroDeVacunacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistroDeVacunacion in the database
        List<RegistroDeVacunacion> registroDeVacunacionList = registroDeVacunacionRepository.findAll();
        assertThat(registroDeVacunacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegistroDeVacunacion() throws Exception {
        int databaseSizeBeforeUpdate = registroDeVacunacionRepository.findAll().size();
        registroDeVacunacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroDeVacunacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registroDeVacunacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistroDeVacunacion in the database
        List<RegistroDeVacunacion> registroDeVacunacionList = registroDeVacunacionRepository.findAll();
        assertThat(registroDeVacunacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegistroDeVacunacion() throws Exception {
        int databaseSizeBeforeUpdate = registroDeVacunacionRepository.findAll().size();
        registroDeVacunacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroDeVacunacionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registroDeVacunacion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RegistroDeVacunacion in the database
        List<RegistroDeVacunacion> registroDeVacunacionList = registroDeVacunacionRepository.findAll();
        assertThat(registroDeVacunacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegistroDeVacunacion() throws Exception {
        // Initialize the database
        registroDeVacunacionRepository.saveAndFlush(registroDeVacunacion);

        int databaseSizeBeforeDelete = registroDeVacunacionRepository.findAll().size();

        // Delete the registroDeVacunacion
        restRegistroDeVacunacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, registroDeVacunacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegistroDeVacunacion> registroDeVacunacionList = registroDeVacunacionRepository.findAll();
        assertThat(registroDeVacunacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
