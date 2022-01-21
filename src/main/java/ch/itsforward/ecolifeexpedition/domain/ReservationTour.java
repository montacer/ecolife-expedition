package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ReservationTour.
 */
@Entity
@Table(name = "reservation_tour")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReservationTour implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "montant_total_ttc")
    private Float montantTotalTTC;

  

    @OneToOne(mappedBy = "reservationTour")
    @JsonIgnore
    private Reservation reservation;

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

    public ReservationTour montantTotalTTC(Float montantTotalTTC) {
        this.montantTotalTTC = montantTotalTTC;
        return this;
    }

    public void setMontantTotalTTC(Float montantTotalTTC) {
        this.montantTotalTTC = montantTotalTTC;
    }

    public Reservation getReservation() {
        return reservation;
    }

   
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

   

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationTour)) {
            return false;
        }
        return id != null && id.equals(((ReservationTour) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservationTour{" +
            "id=" + getId() +
            ", montantTotalTTC=" + getMontantTotalTTC() +
            "}";
    }
}
