package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Hotel.
 */
@Entity
@Table(name = "hotel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle_hotel")
    private String libelleHotel;

    @Column(name = "classe_hotel")
    private Integer classeHotel;

    @OneToMany(mappedBy = "hotel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Chambre> chambres = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "hotels", allowSetters = true)
    private Agence agence;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleHotel() {
        return libelleHotel;
    }

    public Hotel libelleHotel(String libelleHotel) {
        this.libelleHotel = libelleHotel;
        return this;
    }

    public void setLibelleHotel(String libelleHotel) {
        this.libelleHotel = libelleHotel;
    }

    public Integer getClasseHotel() {
        return classeHotel;
    }

    public Hotel classeHotel(Integer classeHotel) {
        this.classeHotel = classeHotel;
        return this;
    }

    public void setClasseHotel(Integer classeHotel) {
        this.classeHotel = classeHotel;
    }

    public Set<Chambre> getChambres() {
        return chambres;
    }

    public Hotel chambres(Set<Chambre> chambres) {
        this.chambres = chambres;
        return this;
    }

    public Hotel addChambre(Chambre chambre) {
        this.chambres.add(chambre);
        chambre.setHotel(this);
        return this;
    }

    public Hotel removeChambre(Chambre chambre) {
        this.chambres.remove(chambre);
        chambre.setHotel(null);
        return this;
    }

    public void setChambres(Set<Chambre> chambres) {
        this.chambres = chambres;
    }

    public Agence getAgence() {
        return agence;
    }

    public Hotel agence(Agence agence) {
        this.agence = agence;
        return this;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hotel)) {
            return false;
        }
        return id != null && id.equals(((Hotel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hotel{" +
            "id=" + getId() +
            ", libelleHotel='" + getLibelleHotel() + "'" +
            ", classeHotel=" + getClasseHotel() +
            "}";
    }
}
