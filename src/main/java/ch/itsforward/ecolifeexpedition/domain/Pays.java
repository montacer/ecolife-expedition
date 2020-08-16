package ch.itsforward.ecolifeexpedition.domain;

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
    private Set<Region> regions = new HashSet<>();

    
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

    public Set<Region> getRegions() {
        return regions;
    }

    public Pays regions(Set<Region> regions) {
        this.regions = regions;
        return this;
    }

    public Pays addRegion(Region region) {
        this.regions.add(region);
        region.setPays(this);
        return this;
    }

    public Pays removeRegion(Region region) {
        this.regions.remove(region);
        region.setPays(null);
        return this;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }


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
