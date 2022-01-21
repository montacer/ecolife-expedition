package ch.itsforward.ecolifeexpedition.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeHebergement.
 */
@Entity
@Table(name = "type_hebergement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TypeHebergement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lib_type_hebergement")
    private String libTypeHebergement;

    @OneToMany(mappedBy = "typeHebergement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Hebergement> hebergements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibTypeHebergement() {
        return libTypeHebergement;
    }

    public TypeHebergement libTypeHebergement(String libTypeHebergement) {
        this.libTypeHebergement = libTypeHebergement;
        return this;
    }

    public void setLibTypeHebergement(String libTypeHebergement) {
        this.libTypeHebergement = libTypeHebergement;
    }

    public Set<Hebergement> getHebergements() {
        return hebergements;
    }

    public TypeHebergement hebergements(Set<Hebergement> hebergements) {
        this.hebergements = hebergements;
        return this;
    }

    public TypeHebergement addHebergement(Hebergement hebergement) {
        this.hebergements.add(hebergement);
        hebergement.setTypeHebergement(this);
        return this;
    }

    public TypeHebergement removeHebergement(Hebergement hebergement) {
        this.hebergements.remove(hebergement);
        hebergement.setTypeHebergement(null);
        return this;
    }

    public void setHebergements(Set<Hebergement> hebergements) {
        this.hebergements = hebergements;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeHebergement)) {
            return false;
        }
        return id != null && id.equals(((TypeHebergement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeHebergement{" +
            "id=" + getId() +
            ", libTypeHebergement='" + getLibTypeHebergement() + "'" +
            "}";
    }
}
