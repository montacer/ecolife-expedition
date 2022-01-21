package ch.itsforward.ecolifeexpedition.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * MODULE RESERVATION\n
 */
@ApiModel(description = "MODULE RESERVATION\n")
@Entity
@Table(name = "agence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Agence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lib_agence")
    private String libAgence;

    @Column(name = "adresse_agence")
    private String adresseAgence;

    @Column(name = "contact_agence")
    private String contactAgence;

    @OneToMany(mappedBy = "agence")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Hotel> hotels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibAgence() {
        return libAgence;
    }

    public Agence libAgence(String libAgence) {
        this.libAgence = libAgence;
        return this;
    }

    public void setLibAgence(String libAgence) {
        this.libAgence = libAgence;
    }

    public String getAdresseAgence() {
        return adresseAgence;
    }

    public Agence adresseAgence(String adresseAgence) {
        this.adresseAgence = adresseAgence;
        return this;
    }

    public void setAdresseAgence(String adresseAgence) {
        this.adresseAgence = adresseAgence;
    }

    public String getContactAgence() {
        return contactAgence;
    }

    public Agence contactAgence(String contactAgence) {
        this.contactAgence = contactAgence;
        return this;
    }

    public void setContactAgence(String contactAgence) {
        this.contactAgence = contactAgence;
    }

    public Set<Hotel> getHotels() {
        return hotels;
    }

    public Agence hotels(Set<Hotel> hotels) {
        this.hotels = hotels;
        return this;
    }

    public Agence addHotel(Hotel hotel) {
        this.hotels.add(hotel);
        hotel.setAgence(this);
        return this;
    }

    public Agence removeHotel(Hotel hotel) {
        this.hotels.remove(hotel);
        hotel.setAgence(null);
        return this;
    }

    public void setHotels(Set<Hotel> hotels) {
        this.hotels = hotels;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agence)) {
            return false;
        }
        return id != null && id.equals(((Agence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agence{" +
            "id=" + getId() +
            ", libAgence='" + getLibAgence() + "'" +
            ", adresseAgence='" + getAdresseAgence() + "'" +
            ", contactAgence='" + getContactAgence() + "'" +
            "}";
    }
}
