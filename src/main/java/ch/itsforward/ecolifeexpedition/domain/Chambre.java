package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Chambre.
 */
@Entity
@Table(name = "chambre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Chambre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prix_ttc")
    private Float prixTTC;

    @ManyToOne
    @JsonIgnoreProperties(value = "chambres", allowSetters = true)
    private Reservation reservation;

    @ManyToOne
    @JsonIgnoreProperties(value = "chambres", allowSetters = true)
    private TypeChambre typeChambre;

    @ManyToOne
    @JsonIgnoreProperties(value = "chambres", allowSetters = true)
    private Hotel hotel;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrixTTC() {
        return prixTTC;
    }

    public Chambre prixTTC(Float prixTTC) {
        this.prixTTC = prixTTC;
        return this;
    }

    public void setPrixTTC(Float prixTTC) {
        this.prixTTC = prixTTC;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Chambre reservation(Reservation reservation) {
        this.reservation = reservation;
        return this;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public TypeChambre getTypeChambre() {
        return typeChambre;
    }

    public Chambre typeChambre(TypeChambre typeChambre) {
        this.typeChambre = typeChambre;
        return this;
    }

    public void setTypeChambre(TypeChambre typeChambre) {
        this.typeChambre = typeChambre;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Chambre hotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chambre)) {
            return false;
        }
        return id != null && id.equals(((Chambre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Chambre{" +
            "id=" + getId() +
            ", prixTTC=" + getPrixTTC() +
            "}";
    }
}
