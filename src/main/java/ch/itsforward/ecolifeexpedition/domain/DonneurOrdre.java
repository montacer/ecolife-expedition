package ch.itsforward.ecolifeexpedition.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DonneurOrdre.
 */
@Entity
@Table(name = "donneur_ordre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DonneurOrdre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "num_telephone")
    private String numTelephone;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "donneurOrdre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Reservation> reservations = new HashSet<>();

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public DonneurOrdre nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public DonneurOrdre prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public DonneurOrdre adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumTelephone() {
        return numTelephone;
    }

    public DonneurOrdre numTelephone(String numTelephone) {
        this.numTelephone = numTelephone;
        return this;
    }

    public void setNumTelephone(String numTelephone) {
        this.numTelephone = numTelephone;
    }

    public String getEmail() {
        return email;
    }

    public DonneurOrdre email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public DonneurOrdre reservations(Set<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public DonneurOrdre addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setDonneurOrdre(this);
        return this;
    }

    public DonneurOrdre removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setDonneurOrdre(null);
        return this;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonneurOrdre)) {
            return false;
        }
        return id != null && id.equals(((DonneurOrdre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonneurOrdre{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", numTelephone='" + getNumTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
