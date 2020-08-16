package ch.itsforward.ecolifeexpedition.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeChambre.
 */
@Entity
@Table(name = "type_chambre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TypeChambre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle_type_chambre")
    private String libelleTypeChambre;

    @OneToMany(mappedBy = "typeChambre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Chambre> chambres = new HashSet<>();

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleTypeChambre() {
        return libelleTypeChambre;
    }

    public TypeChambre libelleTypeChambre(String libelleTypeChambre) {
        this.libelleTypeChambre = libelleTypeChambre;
        return this;
    }

    public void setLibelleTypeChambre(String libelleTypeChambre) {
        this.libelleTypeChambre = libelleTypeChambre;
    }

    public Set<Chambre> getChambres() {
        return chambres;
    }

    public TypeChambre chambres(Set<Chambre> chambres) {
        this.chambres = chambres;
        return this;
    }

    public TypeChambre addChambre(Chambre chambre) {
        this.chambres.add(chambre);
        chambre.setTypeChambre(this);
        return this;
    }

    public TypeChambre removeChambre(Chambre chambre) {
        this.chambres.remove(chambre);
        chambre.setTypeChambre(null);
        return this;
    }

    public void setChambres(Set<Chambre> chambres) {
        this.chambres = chambres;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeChambre)) {
            return false;
        }
        return id != null && id.equals(((TypeChambre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeChambre{" +
            "id=" + getId() +
            ", libelleTypeChambre='" + getLibelleTypeChambre() + "'" +
            "}";
    }
}
