package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Region.
 */
@Entity
@Table(name = "region")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lib_region")
    private String libRegion;

    @OneToMany(mappedBy = "region")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Tour> tours = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "regions", allowSetters = true)
    private Pays pays;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibRegion() {
        return libRegion;
    }

    public Region libRegion(String libRegion) {
        this.libRegion = libRegion;
        return this;
    }

    public void setLibRegion(String libRegion) {
        this.libRegion = libRegion;
    }

    public Set<Tour> getTours() {
        return tours;
    }

    public Region tours(Set<Tour> tours) {
        this.tours = tours;
        return this;
    }

    public Region addTour(Tour tour) {
        this.tours.add(tour);
        tour.setRegion(this);
        return this;
    }

    public Region removeTour(Tour tour) {
        this.tours.remove(tour);
        tour.setRegion(null);
        return this;
    }

    public void setTours(Set<Tour> tours) {
        this.tours = tours;
    }

    public Pays getPays() {
        return pays;
    }

    public Region pays(Pays pays) {
        this.pays = pays;
        return this;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Region)) {
            return false;
        }
        return id != null && id.equals(((Region) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Region{" +
            "id=" + getId() +
            ", libRegion='" + getLibRegion() + "'" +
            "}";
    }
}
