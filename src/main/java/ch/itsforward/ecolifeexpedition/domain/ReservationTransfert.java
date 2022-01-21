package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ReservationTransfert.
 */
@Entity
@Table(name = "reservation_transfert")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReservationTransfert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "montant_total_ttc")
    private Float montantTotalTTC;

 
    @OneToOne(mappedBy = "reservationTransfert")
    @JsonIgnore
    private Reservation reservation;

    @ManyToOne
    @JsonIgnoreProperties(value = "reservationTransferts", allowSetters = true)
    private Transfert transfert;

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

    public ReservationTransfert montantTotalTTC(Float montantTotalTTC) {
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



    public Transfert getTransfert() {
        return transfert;
    }

    public ReservationTransfert transfert(Transfert transfert) {
        this.transfert = transfert;
        return this;
    }

    public void setTransfert(Transfert transfert) {
        this.transfert = transfert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationTransfert)) {
            return false;
        }
        return id != null && id.equals(((ReservationTransfert) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservationTransfert{" +
            "id=" + getId() +
            ", montantTotalTTC=" + getMontantTotalTTC() +
            "}";
    }
}
