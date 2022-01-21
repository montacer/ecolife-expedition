package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A TarifHebergement.
 */
@Entity
@Table(name = "tarif_hebergement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TarifHebergement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "montant_ttc")
    private Float montantTTC;

    @ManyToOne
    @JsonIgnoreProperties(value = "tarifHebergements", allowSetters = true)
    private TypeTarif typeTarif;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMontantTTC() {
        return montantTTC;
    }

    public TarifHebergement montantTTC(Float montantTTC) {
        this.montantTTC = montantTTC;
        return this;
    }

    public void setMontantTTC(Float montantTTC) {
        this.montantTTC = montantTTC;
    }

    public TypeTarif getTypeTarif() {
        return typeTarif;
    }

    public TarifHebergement typeTarif(TypeTarif typeTarif) {
        this.typeTarif = typeTarif;
        return this;
    }

    public void setTypeTarif(TypeTarif typeTarif) {
        this.typeTarif = typeTarif;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarifHebergement)) {
            return false;
        }
        return id != null && id.equals(((TarifHebergement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarifHebergement{" +
            "id=" + getId() +
            ", montantTTC=" + getMontantTTC() +
            "}";
    }
}
