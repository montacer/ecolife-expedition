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

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "lattitude")
    private Float lattitude;

    @Column(name = "longitude")
    private Float longitude;

    @OneToMany(mappedBy = "hotel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Chambre> chambres = new HashSet<>();

    @OneToMany(mappedBy = "hotel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<HotelMedia> hotelMedias = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "hotels", allowSetters = true)
    private Agence agence;

    // jhipster-needle-entity-add-field - JHipster will add fields here
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

    public String getAdresse() {
        return adresse;
    }

    public Hotel adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Float getLattitude() {
        return lattitude;
    }

    public Hotel lattitude(Float lattitude) {
        this.lattitude = lattitude;
        return this;
    }

    public void setLattitude(Float lattitude) {
        this.lattitude = lattitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Hotel longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
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

    public Set<HotelMedia> getHotelMedias() {
        return hotelMedias;
    }

    public Hotel hotelMedias(Set<HotelMedia> hotelMedias) {
        this.hotelMedias = hotelMedias;
        return this;
    }

    public Hotel addHotelMedia(HotelMedia hotelMedia) {
        this.hotelMedias.add(hotelMedia);
        hotelMedia.setHotel(this);
        return this;
    }

    public Hotel removeHotelMedia(HotelMedia hotelMedia) {
        this.hotelMedias.remove(hotelMedia);
        hotelMedia.setHotel(null);
        return this;
    }

    public void setHotelMedias(Set<HotelMedia> hotelMedias) {
        this.hotelMedias = hotelMedias;
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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
            ", adresse='" + getAdresse() + "'" +
            ", lattitude=" + getLattitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
