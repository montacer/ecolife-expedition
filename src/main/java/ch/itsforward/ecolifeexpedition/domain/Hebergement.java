package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Hebergement.
 */
@Entity
@Table(name = "hebergement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Hebergement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "lattitude")
    private Float lattitude;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "montant_t_tc")
    private Float montantTTc;

    @OneToMany(mappedBy = "hebergement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ReservationHebergement> reservationHebergements = new HashSet<>();

    @OneToMany(mappedBy = "hebergement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AvisHebergement> avisHebergements = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "hebergements", allowSetters = true)
    private TypeHebergement typeHebergement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Hebergement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public Hebergement adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Float getLattitude() {
        return lattitude;
    }

    public Hebergement lattitude(Float lattitude) {
        this.lattitude = lattitude;
        return this;
    }

    public void setLattitude(Float lattitude) {
        this.lattitude = lattitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Hebergement longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getMontantTTc() {
        return montantTTc;
    }

    public Hebergement montantTTc(Float montantTTc) {
        this.montantTTc = montantTTc;
        return this;
    }

    public void setMontantTTc(Float montantTTc) {
        this.montantTTc = montantTTc;
    }

    public Set<ReservationHebergement> getReservationHebergements() {
        return reservationHebergements;
    }

    public Hebergement reservationHebergements(Set<ReservationHebergement> reservationHebergements) {
        this.reservationHebergements = reservationHebergements;
        return this;
    }

    public Hebergement addReservationHebergement(ReservationHebergement reservationHebergement) {
        this.reservationHebergements.add(reservationHebergement);
        reservationHebergement.setHebergement(this);
        return this;
    }

    public Hebergement removeReservationHebergement(ReservationHebergement reservationHebergement) {
        this.reservationHebergements.remove(reservationHebergement);
        reservationHebergement.setHebergement(null);
        return this;
    }

    public void setReservationHebergements(Set<ReservationHebergement> reservationHebergements) {
        this.reservationHebergements = reservationHebergements;
    }

    public Set<AvisHebergement> getAvisHebergements() {
        return avisHebergements;
    }

    public Hebergement avisHebergements(Set<AvisHebergement> avisHebergements) {
        this.avisHebergements = avisHebergements;
        return this;
    }

    public Hebergement addAvisHebergement(AvisHebergement avisHebergement) {
        this.avisHebergements.add(avisHebergement);
        avisHebergement.setHebergement(this);
        return this;
    }

    public Hebergement removeAvisHebergement(AvisHebergement avisHebergement) {
        this.avisHebergements.remove(avisHebergement);
        avisHebergement.setHebergement(null);
        return this;
    }

    public void setAvisHebergements(Set<AvisHebergement> avisHebergements) {
        this.avisHebergements = avisHebergements;
    }

    public TypeHebergement getTypeHebergement() {
        return typeHebergement;
    }

    public Hebergement typeHebergement(TypeHebergement typeHebergement) {
        this.typeHebergement = typeHebergement;
        return this;
    }

    public void setTypeHebergement(TypeHebergement typeHebergement) {
        this.typeHebergement = typeHebergement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hebergement)) {
            return false;
        }
        return id != null && id.equals(((Hebergement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hebergement{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", lattitude=" + getLattitude() +
            ", longitude=" + getLongitude() +
            ", montantTTc=" + getMontantTTc() +
            "}";
    }
}
