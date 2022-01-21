package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import ch.itsforward.ecolifeexpedition.domain.enumeration.Stars;

/**
 * A AvisTransfert.
 */
@Entity
@Table(name = "avis_transfert")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AvisTransfert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author")
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "nbre_etoile")
    private Stars nbreEtoile;

    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne
    @JsonIgnoreProperties(value = "avisTransferts", allowSetters = true)
    private Transfert transfert;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public AvisTransfert author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Stars getNbreEtoile() {
        return nbreEtoile;
    }

    public AvisTransfert nbreEtoile(Stars nbreEtoile) {
        this.nbreEtoile = nbreEtoile;
        return this;
    }

    public void setNbreEtoile(Stars nbreEtoile) {
        this.nbreEtoile = nbreEtoile;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public AvisTransfert commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Transfert getTransfert() {
        return transfert;
    }

    public AvisTransfert transfert(Transfert transfert) {
        this.transfert = transfert;
        return this;
    }

    public void setTransfert(Transfert transfert) {
        this.transfert = transfert;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvisTransfert)) {
            return false;
        }
        return id != null && id.equals(((AvisTransfert) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvisTransfert{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", nbreEtoile='" + getNbreEtoile() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
