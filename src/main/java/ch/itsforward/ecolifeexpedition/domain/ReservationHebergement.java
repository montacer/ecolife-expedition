package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ReservationHebergement.
 */
@Entity
@Table(name = "reservation_hebergement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReservationHebergement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "montant_total_ttc")
    private Float montantTotalTTC;

 
    @OneToOne(mappedBy = "reservationHebergement")
    @JsonIgnore
    private Reservation reservation;

    @ManyToOne
    @JsonIgnoreProperties(value = "reservationHebergements", allowSetters = true)
    private Hebergement hebergement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMontantTotalTTC() {
        return montantTotalTTC;
    }

    public ReservationHebergement montantTotalTTC(Float montantTotalTTC) {
        this.montantTotalTTC = montantTotalTTC;
        return this;
    }

    public void setMontantTotalTTC(Float montantTotalTTC) {
        this.montantTotalTTC = montantTotalTTC;
    }

   
    public Reservation getReservation() {
        return reservation;
    }

    public ReservationHebergement reservation(Reservation reservation) {
        this.reservation = reservation;
        return this;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Hebergement getHebergement() {
        return hebergement;
    }

    public ReservationHebergement hebergement(Hebergement hebergement) {
        this.hebergement = hebergement;
        return this;
    }

    public void setHebergement(Hebergement hebergement) {
        this.hebergement = hebergement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationHebergement)) {
            return false;
        }
        return id != null && id.equals(((ReservationHebergement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservationHebergement{" +
            "id=" + getId() +
            ", montantTotalTTC=" + getMontantTotalTTC() +
            "}";
    }
}
