package ch.itsforward.ecolifeexpedition.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeTarif.
 */
@Entity
@Table(name = "type_tarif")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TypeTarif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lib_type_tarif")
    private String libTypeTarif;

    @OneToMany(mappedBy = "typeTarif")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TarifTransfert> tarifTransferts = new HashSet<>();

    @OneToMany(mappedBy = "typeTarif")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TarifHebergement> tarifHebergements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibTypeTarif() {
        return libTypeTarif;
    }

    public TypeTarif libTypeTarif(String libTypeTarif) {
        this.libTypeTarif = libTypeTarif;
        return this;
    }

    public void setLibTypeTarif(String libTypeTarif) {
        this.libTypeTarif = libTypeTarif;
    }

    public Set<TarifTransfert> getTarifTransferts() {
        return tarifTransferts;
    }

    public TypeTarif tarifTransferts(Set<TarifTransfert> tarifTransferts) {
        this.tarifTransferts = tarifTransferts;
        return this;
    }

    public TypeTarif addTarifTransfert(TarifTransfert tarifTransfert) {
        this.tarifTransferts.add(tarifTransfert);
        tarifTransfert.setTypeTarif(this);
        return this;
    }

    public TypeTarif removeTarifTransfert(TarifTransfert tarifTransfert) {
        this.tarifTransferts.remove(tarifTransfert);
        tarifTransfert.setTypeTarif(null);
        return this;
    }

    public void setTarifTransferts(Set<TarifTransfert> tarifTransferts) {
        this.tarifTransferts = tarifTransferts;
    }

    public Set<TarifHebergement> getTarifHebergements() {
        return tarifHebergements;
    }

    public TypeTarif tarifHebergements(Set<TarifHebergement> tarifHebergements) {
        this.tarifHebergements = tarifHebergements;
        return this;
    }

    public TypeTarif addTarifHebergement(TarifHebergement tarifHebergement) {
        this.tarifHebergements.add(tarifHebergement);
        tarifHebergement.setTypeTarif(this);
        return this;
    }

    public TypeTarif removeTarifHebergement(TarifHebergement tarifHebergement) {
        this.tarifHebergements.remove(tarifHebergement);
        tarifHebergement.setTypeTarif(null);
        return this;
    }

    public void setTarifHebergements(Set<TarifHebergement> tarifHebergements) {
        this.tarifHebergements = tarifHebergements;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeTarif)) {
            return false;
        }
        return id != null && id.equals(((TypeTarif) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeTarif{" +
            "id=" + getId() +
            ", libTypeTarif='" + getLibTypeTarif() + "'" +
            "}";
    }
}
