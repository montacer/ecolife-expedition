package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ServiceSupplementaire.
 */
@Entity
@Table(name = "service_supplementaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceSupplementaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle_service")
    private String libelleService;

    @ManyToOne
    @JsonIgnoreProperties(value = "serviceSupplementaires", allowSetters = true)
    private Tour tour;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleService() {
        return libelleService;
    }

    public ServiceSupplementaire libelleService(String libelleService) {
        this.libelleService = libelleService;
        return this;
    }

    public void setLibelleService(String libelleService) {
        this.libelleService = libelleService;
    }

    public Tour getTour() {
        return tour;
    }

    public ServiceSupplementaire tour(Tour tour) {
        this.tour = tour;
        return this;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceSupplementaire)) {
            return false;
        }
        return id != null && id.equals(((ServiceSupplementaire) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceSupplementaire{" +
            "id=" + getId() +
            ", libelleService='" + getLibelleService() + "'" +
            "}";
    }
}
