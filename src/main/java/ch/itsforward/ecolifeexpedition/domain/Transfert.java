package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Transfert.
 */
@Entity
@Table(name = "transfert")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transfert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "montant_ttc")
    private Float montantTTC;

    @OneToMany(mappedBy = "transfert")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AvisTransfert> avisTransferts = new HashSet<>();

    @OneToMany(mappedBy = "transfert")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ReservationTransfert> reservationTransferts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "transferts", allowSetters = true)
    private TypeTransfert typeTransfert;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMontantTTC() {
        return montantTTC;
    }

    public Transfert montantTTC(Float montantTTC) {
        this.montantTTC = montantTTC;
        return this;
    }

    public void setMontantTTC(Float montantTTC) {
        this.montantTTC = montantTTC;
    }

    public Set<AvisTransfert> getAvisTransferts() {
        return avisTransferts;
    }

    public Transfert avisTransferts(Set<AvisTransfert> avisTransferts) {
        this.avisTransferts = avisTransferts;
        return this;
    }

    public Transfert addAvisTransfert(AvisTransfert avisTransfert) {
        this.avisTransferts.add(avisTransfert);
        avisTransfert.setTransfert(this);
        return this;
    }

    public Transfert removeAvisTransfert(AvisTransfert avisTransfert) {
        this.avisTransferts.remove(avisTransfert);
        avisTransfert.setTransfert(null);
        return this;
    }

    public void setAvisTransferts(Set<AvisTransfert> avisTransferts) {
        this.avisTransferts = avisTransferts;
    }

    public Set<ReservationTransfert> getReservationTransferts() {
        return reservationTransferts;
    }

    public Transfert reservationTransferts(Set<ReservationTransfert> reservationTransferts) {
        this.reservationTransferts = reservationTransferts;
        return this;
    }

    public Transfert addReservationTransfert(ReservationTransfert reservationTransfert) {
        this.reservationTransferts.add(reservationTransfert);
        reservationTransfert.setTransfert(this);
        return this;
    }

    public Transfert removeReservationTransfert(ReservationTransfert reservationTransfert) {
        this.reservationTransferts.remove(reservationTransfert);
        reservationTransfert.setTransfert(null);
        return this;
    }

    public void setReservationTransferts(Set<ReservationTransfert> reservationTransferts) {
        this.reservationTransferts = reservationTransferts;
    }

    public TypeTransfert getTypeTransfert() {
        return typeTransfert;
    }

    public Transfert typeTransfert(TypeTransfert typeTransfert) {
        this.typeTransfert = typeTransfert;
        return this;
    }

    public void setTypeTransfert(TypeTransfert typeTransfert) {
        this.typeTransfert = typeTransfert;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transfert)) {
            return false;
        }
        return id != null && id.equals(((Transfert) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transfert{" +
            "id=" + getId() +
            ", montantTTC=" + getMontantTTC() +
            "}";
    }
}
