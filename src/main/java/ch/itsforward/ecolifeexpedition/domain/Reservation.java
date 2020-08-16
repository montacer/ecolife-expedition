package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "nombre_adulte")
    private Integer nombreAdulte;

    @Column(name = "nombre_jeune")
    private Integer nombreJeune;

    @Column(name = "nombre_enfant")
    private Integer nombreEnfant;

    @Column(name = "montant_ttc")
    private Float montantTTC;

    @Column(name = "lib_devise")
    private String libDevise;

    @OneToMany(mappedBy = "reservation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Chambre> chambres = new HashSet<>();

    @OneToMany(mappedBy = "reservation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Client> clients = new HashSet<>();

    @OneToOne(mappedBy = "reservation")
    @JsonIgnore
    private Tour tour;

    @ManyToOne
    @JsonIgnoreProperties(value = "reservations", allowSetters = true)
    private DonneurOrdre donneurOrdre;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public Reservation dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public Reservation dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getNombreAdulte() {
        return nombreAdulte;
    }

    public Reservation nombreAdulte(Integer nombreAdulte) {
        this.nombreAdulte = nombreAdulte;
        return this;
    }

    public void setNombreAdulte(Integer nombreAdulte) {
        this.nombreAdulte = nombreAdulte;
    }

    public Integer getNombreJeune() {
        return nombreJeune;
    }

    public Reservation nombreJeune(Integer nombreJeune) {
        this.nombreJeune = nombreJeune;
        return this;
    }

    public void setNombreJeune(Integer nombreJeune) {
        this.nombreJeune = nombreJeune;
    }

    public Integer getNombreEnfant() {
        return nombreEnfant;
    }

    public Reservation nombreEnfant(Integer nombreEnfant) {
        this.nombreEnfant = nombreEnfant;
        return this;
    }

    public void setNombreEnfant(Integer nombreEnfant) {
        this.nombreEnfant = nombreEnfant;
    }

    public Float getMontantTTC() {
        return montantTTC;
    }

    public Reservation montantTTC(Float montantTTC) {
        this.montantTTC = montantTTC;
        return this;
    }

    public void setMontantTTC(Float montantTTC) {
        this.montantTTC = montantTTC;
    }

    public String getLibDevise() {
        return libDevise;
    }

    public Reservation libDevise(String libDevise) {
        this.libDevise = libDevise;
        return this;
    }

    public void setLibDevise(String libDevise) {
        this.libDevise = libDevise;
    }

    public Set<Chambre> getChambres() {
        return chambres;
    }

    public Reservation chambres(Set<Chambre> chambres) {
        this.chambres = chambres;
        return this;
    }

    public Reservation addChambre(Chambre chambre) {
        this.chambres.add(chambre);
        chambre.setReservation(this);
        return this;
    }

    public Reservation removeChambre(Chambre chambre) {
        this.chambres.remove(chambre);
        chambre.setReservation(null);
        return this;
    }

    public void setChambres(Set<Chambre> chambres) {
        this.chambres = chambres;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public Reservation clients(Set<Client> clients) {
        this.clients = clients;
        return this;
    }

    public Reservation addClient(Client client) {
        this.clients.add(client);
        client.setReservation(this);
        return this;
    }

    public Reservation removeClient(Client client) {
        this.clients.remove(client);
        client.setReservation(null);
        return this;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public Tour getTour() {
        return tour;
    }

    public Reservation tour(Tour tour) {
        this.tour = tour;
        return this;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public DonneurOrdre getDonneurOrdre() {
        return donneurOrdre;
    }

    public Reservation donneurOrdre(DonneurOrdre donneurOrdre) {
        this.donneurOrdre = donneurOrdre;
        return this;
    }

    public void setDonneurOrdre(DonneurOrdre donneurOrdre) {
        this.donneurOrdre = donneurOrdre;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation)) {
            return false;
        }
        return id != null && id.equals(((Reservation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", nombreAdulte=" + getNombreAdulte() +
            ", nombreJeune=" + getNombreJeune() +
            ", nombreEnfant=" + getNombreEnfant() +
            ", montantTTC=" + getMontantTTC() +
            ", libDevise='" + getLibDevise() + "'" +
            "}";
    }
}
