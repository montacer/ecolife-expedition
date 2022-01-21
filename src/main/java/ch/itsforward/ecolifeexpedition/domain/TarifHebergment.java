package ch.itsforward.ecolifeexpedition.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * MODULE HEBERGEMENT
 */
@ApiModel(description = "MODULE HEBERGEMENT")
@Entity
@Table(name = "tarif_hebergment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TarifHebergment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lib_tarif_hergement")
    private String libTarifHergement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibTarifHergement() {
        return libTarifHergement;
    }

    public TarifHebergment libTarifHergement(String libTarifHergement) {
        this.libTarifHergement = libTarifHergement;
        return this;
    }

    public void setLibTarifHergement(String libTarifHergement) {
        this.libTarifHergement = libTarifHergement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarifHebergment)) {
            return false;
        }
        return id != null && id.equals(((TarifHebergment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarifHebergment{" +
            "id=" + getId() +
            ", libTarifHergement='" + getLibTarifHergement() + "'" +
            "}";
    }
}
