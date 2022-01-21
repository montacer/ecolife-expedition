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

    @OneToMany(mappedBy = "regionOrigine")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Pays> listPays = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "listRegions", allowSetters = true)
    private Pays pays;

    @ManyToOne
    @JsonIgnoreProperties(value = "regions", allowSetters = true)
    private TypeRegion typeRegion;

    // jhipster-needle-entity-add-field - JHipster will add fields here
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

    public Set<Pays> getListPays() {
        return listPays;
    }

    public Region listPays(Set<Pays> pays) {
        this.listPays = pays;
        return this;
    }

    public Region addListPays(Pays pays) {
        this.listPays.add(pays);
        pays.setRegionOrigine(this);
        return this;
    }

    public Region removeListPays(Pays pays) {
        this.listPays.remove(pays);
        pays.setRegionOrigine(null);
        return this;
    }

    public void setListPays(Set<Pays> pays) {
        this.listPays = pays;
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

    public TypeRegion getTypeRegion() {
        return typeRegion;
    }

    public Region typeRegion(TypeRegion typeRegion) {
        this.typeRegion = typeRegion;
        return this;
    }

    public void setTypeRegion(TypeRegion typeRegion) {
        this.typeRegion = typeRegion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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
