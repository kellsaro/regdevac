package com.rdv.web.rest;

import com.rdv.domain.RegistroDeVacunacion;
import com.rdv.repository.RegistroDeVacunacionRepository;
import com.rdv.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.rdv.domain.RegistroDeVacunacion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RegistroDeVacunacionResource {

    private final Logger log = LoggerFactory.getLogger(RegistroDeVacunacionResource.class);

    private static final String ENTITY_NAME = "registroDeVacunacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegistroDeVacunacionRepository registroDeVacunacionRepository;

    public RegistroDeVacunacionResource(RegistroDeVacunacionRepository registroDeVacunacionRepository) {
        this.registroDeVacunacionRepository = registroDeVacunacionRepository;
    }

    /**
     * {@code POST  /registro-de-vacunacions} : Create a new registroDeVacunacion.
     *
     * @param registroDeVacunacion the registroDeVacunacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registroDeVacunacion, or with status {@code 400 (Bad Request)} if the registroDeVacunacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/registro-de-vacunacions")
    public ResponseEntity<RegistroDeVacunacion> createRegistroDeVacunacion(@Valid @RequestBody RegistroDeVacunacion registroDeVacunacion)
        throws URISyntaxException {
        log.debug("REST request to save RegistroDeVacunacion : {}", registroDeVacunacion);
        if (registroDeVacunacion.getId() != null) {
            throw new BadRequestAlertException("A new registroDeVacunacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegistroDeVacunacion result = registroDeVacunacionRepository.save(registroDeVacunacion);
        return ResponseEntity
            .created(new URI("/api/registro-de-vacunacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /registro-de-vacunacions/:id} : Updates an existing registroDeVacunacion.
     *
     * @param id the id of the registroDeVacunacion to save.
     * @param registroDeVacunacion the registroDeVacunacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registroDeVacunacion,
     * or with status {@code 400 (Bad Request)} if the registroDeVacunacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registroDeVacunacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/registro-de-vacunacions/{id}")
    public ResponseEntity<RegistroDeVacunacion> updateRegistroDeVacunacion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RegistroDeVacunacion registroDeVacunacion
    ) throws URISyntaxException {
        log.debug("REST request to update RegistroDeVacunacion : {}, {}", id, registroDeVacunacion);
        if (registroDeVacunacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registroDeVacunacion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!registroDeVacunacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RegistroDeVacunacion result = registroDeVacunacionRepository.save(registroDeVacunacion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registroDeVacunacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /registro-de-vacunacions/:id} : Partial updates given fields of an existing registroDeVacunacion, field will ignore if it is null
     *
     * @param id the id of the registroDeVacunacion to save.
     * @param registroDeVacunacion the registroDeVacunacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registroDeVacunacion,
     * or with status {@code 400 (Bad Request)} if the registroDeVacunacion is not valid,
     * or with status {@code 404 (Not Found)} if the registroDeVacunacion is not found,
     * or with status {@code 500 (Internal Server Error)} if the registroDeVacunacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/registro-de-vacunacions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RegistroDeVacunacion> partialUpdateRegistroDeVacunacion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RegistroDeVacunacion registroDeVacunacion
    ) throws URISyntaxException {
        log.debug("REST request to partial update RegistroDeVacunacion partially : {}, {}", id, registroDeVacunacion);
        if (registroDeVacunacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registroDeVacunacion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!registroDeVacunacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RegistroDeVacunacion> result = registroDeVacunacionRepository
            .findById(registroDeVacunacion.getId())
            .map(existingRegistroDeVacunacion -> {
                if (registroDeVacunacion.getTipoDeVacuna() != null) {
                    existingRegistroDeVacunacion.setTipoDeVacuna(registroDeVacunacion.getTipoDeVacuna());
                }
                if (registroDeVacunacion.getFechaDeVacunacion() != null) {
                    existingRegistroDeVacunacion.setFechaDeVacunacion(registroDeVacunacion.getFechaDeVacunacion());
                }
                if (registroDeVacunacion.getNumeroDeDosis() != null) {
                    existingRegistroDeVacunacion.setNumeroDeDosis(registroDeVacunacion.getNumeroDeDosis());
                }

                return existingRegistroDeVacunacion;
            })
            .map(registroDeVacunacionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registroDeVacunacion.getId().toString())
        );
    }

    /**
     * {@code GET  /registro-de-vacunacions} : get all the registroDeVacunacions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registroDeVacunacions in body.
     */
    @GetMapping("/registro-de-vacunacions")
    public ResponseEntity<List<RegistroDeVacunacion>> getAllRegistroDeVacunacions(Pageable pageable) {
        log.debug("REST request to get a page of RegistroDeVacunacions");
        Page<RegistroDeVacunacion> page = registroDeVacunacionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /registro-de-vacunacions/:id} : get the "id" registroDeVacunacion.
     *
     * @param id the id of the registroDeVacunacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registroDeVacunacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/registro-de-vacunacions/{id}")
    public ResponseEntity<RegistroDeVacunacion> getRegistroDeVacunacion(@PathVariable Long id) {
        log.debug("REST request to get RegistroDeVacunacion : {}", id);
        Optional<RegistroDeVacunacion> registroDeVacunacion = registroDeVacunacionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(registroDeVacunacion);
    }

    /**
     * {@code DELETE  /registro-de-vacunacions/:id} : delete the "id" registroDeVacunacion.
     *
     * @param id the id of the registroDeVacunacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/registro-de-vacunacions/{id}")
    public ResponseEntity<Void> deleteRegistroDeVacunacion(@PathVariable Long id) {
        log.debug("REST request to delete RegistroDeVacunacion : {}", id);
        registroDeVacunacionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
