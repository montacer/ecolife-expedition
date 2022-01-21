package ch.itsforward.ecolifeexpedition.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeRegion.
 */
@Entity
@Table(name = "type_region")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TypeRegion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lib_type_region")
    private String libTypeRegion;

    @OneToMany(mappedBy = "typeRegion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Region> regions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibTypeRegion() {
        return libTypeRegion;
    }

    public TypeRegion libTypeRegion(String libTypeRegion) {
        this.libTypeRegion = libTypeRegion;
        return this;
    }

    public void setLibTypeRegion(String libTypeRegion) {
        this.libTypeRegion = libTypeRegion;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public TypeRegion regions(Set<Region> regions) {
        this.regions = regions;
        return this;
    }

    public TypeRegion addRegion(Region region) {
        this.regions.add(region);
        region.setTypeRegion(this);
        return this;
    }

    public TypeRegion removeRegion(Region region) {
        this.regions.remove(region);
        region.setTypeRegion(null);
        return this;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeRegion)) {
            return false;
        }
        return id != null && id.equals(((TypeRegion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeRegion{" +
            "id=" + getId() +
            ", libTypeRegion='" + getLibTypeRegion() + "'" +
            "}";
    }
}
