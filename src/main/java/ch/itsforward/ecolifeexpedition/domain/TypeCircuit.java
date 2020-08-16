package ch.itsforward.ecolifeexpedition.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeCircuit.
 */
@Entity
@Table(name = "type_circuit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TypeCircuit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lib_type_circuit")
    private String libTypeCircuit;

    @OneToMany(mappedBy = "typeCircuit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Tour> tours = new HashSet<>();

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibTypeCircuit() {
        return libTypeCircuit;
    }

    public TypeCircuit libTypeCircuit(String libTypeCircuit) {
        this.libTypeCircuit = libTypeCircuit;
        return this;
    }

    public void setLibTypeCircuit(String libTypeCircuit) {
        this.libTypeCircuit = libTypeCircuit;
    }

    public Set<Tour> getTours() {
        return tours;
    }

    public TypeCircuit tours(Set<Tour> tours) {
        this.tours = tours;
        return this;
    }

    public TypeCircuit addTour(Tour tour) {
        this.tours.add(tour);
        tour.setTypeCircuit(this);
        return this;
    }

    public TypeCircuit removeTour(Tour tour) {
        this.tours.remove(tour);
        tour.setTypeCircuit(null);
        return this;
    }

    public void setTours(Set<Tour> tours) {
        this.tours = tours;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeCircuit)) {
            return false;
        }
        return id != null && id.equals(((TypeCircuit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeCircuit{" +
            "id=" + getId() +
            ", libTypeCircuit='" + getLibTypeCircuit() + "'" +
            "}";
    }
}
