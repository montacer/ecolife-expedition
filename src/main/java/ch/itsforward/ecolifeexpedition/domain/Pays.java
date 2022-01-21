package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Pays.
 */
@Entity
@Table(name = "pays")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pays implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cod_iso_pays")
    private String codIsoPays;

    @Column(name = "lib_pays")
    private String libPays;

    @Column(name = "code_devise_pays")
    private String codeDevisePays;

    @OneToMany(mappedBy = "pays")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Region> listRegions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "pays", allowSetters = true)
    private Region regionOrigine;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodIsoPays() {
        return codIsoPays;
    }

    public Pays codIsoPays(String codIsoPays) {
        this.codIsoPays = codIsoPays;
        return this;
    }

    public void setCodIsoPays(String codIsoPays) {
        this.codIsoPays = codIsoPays;
    }

    public String getLibPays() {
        return libPays;
    }

    public Pays libPays(String libPays) {
        this.libPays = libPays;
        return this;
    }

    public void setLibPays(String libPays) {
        this.libPays = libPays;
    }

    public String getCodeDevisePays() {
        return codeDevisePays;
    }

    public Pays codeDevisePays(String codeDevisePays) {
        this.codeDevisePays = codeDevisePays;
        return this;
    }

    public void setCodeDevisePays(String codeDevisePays) {
        this.codeDevisePays = codeDevisePays;
    }

    public Set<Region> getListRegions() {
        return listRegions;
    }

    public Pays listRegions(Set<Region> regions) {
        this.listRegions = regions;
        return this;
    }

    public Pays addListRegions(Region region) {
        this.listRegions.add(region);
        region.setPays(this);
        return this;
    }

    public Pays removeListRegions(Region region) {
        this.listRegions.remove(region);
        region.setPays(null);
        return this;
    }

    public void setListRegions(Set<Region> regions) {
        this.listRegions = regions;
    }

    public Region getRegionOrigine() {
        return regionOrigine;
    }

    public Pays regionOrigine(Region region) {
        this.regionOrigine = region;
        return this;
    }

    public void setRegionOrigine(Region region) {
        this.regionOrigine = region;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pays)) {
            return false;
        }
        return id != null && id.equals(((Pays) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pays{" +
            "id=" + getId() +
            ", codIsoPays='" + getCodIsoPays() + "'" +
            ", libPays='" + getLibPays() + "'" +
            ", codeDevisePays='" + getCodeDevisePays() + "'" +
            "}";
    }
}
