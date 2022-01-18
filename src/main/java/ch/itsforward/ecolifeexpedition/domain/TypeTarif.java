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
    private Set<TarifTour> tarifTours = new HashSet<>();

    @OneToMany(mappedBy = "typeTarif")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TarifServiceSupplementaire> tarifServiceSupplementaires = new HashSet<>();

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

    public Set<TarifTour> getTarifTours() {
        return tarifTours;
    }

    public TypeTarif tarifTours(Set<TarifTour> tarifTours) {
        this.tarifTours = tarifTours;
        return this;
    }

    public TypeTarif addTarifTour(TarifTour tarifTour) {
        this.tarifTours.add(tarifTour);
        tarifTour.setTypeTarif(this);
        return this;
    }

    public TypeTarif removeTarifTour(TarifTour tarifTour) {
        this.tarifTours.remove(tarifTour);
        tarifTour.setTypeTarif(null);
        return this;
    }

    public void setTarifTours(Set<TarifTour> tarifTours) {
        this.tarifTours = tarifTours;
    }

    public Set<TarifServiceSupplementaire> getTarifServiceSupplementaires() {
        return tarifServiceSupplementaires;
    }

    public TypeTarif tarifServiceSupplementaires(Set<TarifServiceSupplementaire> tarifServiceSupplementaires) {
        this.tarifServiceSupplementaires = tarifServiceSupplementaires;
        return this;
    }

    public TypeTarif addTarifServiceSupplementaire(TarifServiceSupplementaire tarifServiceSupplementaire) {
        this.tarifServiceSupplementaires.add(tarifServiceSupplementaire);
        tarifServiceSupplementaire.setTypeTarif(this);
        return this;
    }

    public TypeTarif removeTarifServiceSupplementaire(TarifServiceSupplementaire tarifServiceSupplementaire) {
        this.tarifServiceSupplementaires.remove(tarifServiceSupplementaire);
        tarifServiceSupplementaire.setTypeTarif(null);
        return this;
    }

    public void setTarifServiceSupplementaires(Set<TarifServiceSupplementaire> tarifServiceSupplementaires) {
        this.tarifServiceSupplementaires = tarifServiceSupplementaires;
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
