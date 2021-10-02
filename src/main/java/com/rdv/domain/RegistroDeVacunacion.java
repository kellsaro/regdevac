package com.rdv.domain;

import com.rdv.domain.enumeration.TipoDeVacuna;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RegistroDeVacunacion.
 */
@Entity
@Table(name = "registro_de_vacunacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RegistroDeVacunacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_vacuna", nullable = false)
    private TipoDeVacuna tipoDeVacuna;

    @NotNull
    @Column(name = "fecha_de_vacunacion", nullable = false)
    private LocalDate fechaDeVacunacion;

    @NotNull
    @Column(name = "numero_de_dosis", nullable = false)
    private Integer numeroDeDosis;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RegistroDeVacunacion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDeVacuna getTipoDeVacuna() {
        return this.tipoDeVacuna;
    }

    public RegistroDeVacunacion tipoDeVacuna(TipoDeVacuna tipoDeVacuna) {
        this.setTipoDeVacuna(tipoDeVacuna);
        return this;
    }

    public void setTipoDeVacuna(TipoDeVacuna tipoDeVacuna) {
        this.tipoDeVacuna = tipoDeVacuna;
    }

    public LocalDate getFechaDeVacunacion() {
        return this.fechaDeVacunacion;
    }

    public RegistroDeVacunacion fechaDeVacunacion(LocalDate fechaDeVacunacion) {
        this.setFechaDeVacunacion(fechaDeVacunacion);
        return this;
    }

    public void setFechaDeVacunacion(LocalDate fechaDeVacunacion) {
        this.fechaDeVacunacion = fechaDeVacunacion;
    }

    public Integer getNumeroDeDosis() {
        return this.numeroDeDosis;
    }

    public RegistroDeVacunacion numeroDeDosis(Integer numeroDeDosis) {
        this.setNumeroDeDosis(numeroDeDosis);
        return this;
    }

    public void setNumeroDeDosis(Integer numeroDeDosis) {
        this.numeroDeDosis = numeroDeDosis;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RegistroDeVacunacion user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistroDeVacunacion)) {
            return false;
        }
        return id != null && id.equals(((RegistroDeVacunacion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegistroDeVacunacion{" +
            "id=" + getId() +
            ", tipoDeVacuna='" + getTipoDeVacuna() + "'" +
            ", fechaDeVacunacion='" + getFechaDeVacunacion() + "'" +
            ", numeroDeDosis=" + getNumeroDeDosis() +
            "}";
    }
}
