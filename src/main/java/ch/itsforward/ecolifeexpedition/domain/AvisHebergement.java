package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import ch.itsforward.ecolifeexpedition.domain.enumeration.Stars;

/**
 * A AvisHebergement.
 */
@Entity
@Table(name = "avis_hebergement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AvisHebergement implements Serializable {

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

    @OneToMany(mappedBy = "avisHebergement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AvisHebergementMedia> avisHebergementMedias = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "avisHebergements", allowSetters = true)
    private Hebergement hebergement;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public AvisHebergement author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Stars getNbreEtoile() {
        return nbreEtoile;
    }

    public AvisHebergement nbreEtoile(Stars nbreEtoile) {
        this.nbreEtoile = nbreEtoile;
        return this;
    }

    public void setNbreEtoile(Stars nbreEtoile) {
        this.nbreEtoile = nbreEtoile;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public AvisHebergement commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Set<AvisHebergementMedia> getAvisHebergementMedias() {
        return avisHebergementMedias;
    }

    public AvisHebergement avisHebergementMedias(Set<AvisHebergementMedia> avisHebergementMedias) {
        this.avisHebergementMedias = avisHebergementMedias;
        return this;
    }

    public AvisHebergement addAvisHebergementMedia(AvisHebergementMedia avisHebergementMedia) {
        this.avisHebergementMedias.add(avisHebergementMedia);
        avisHebergementMedia.setAvisHebergement(this);
        return this;
    }

    public AvisHebergement removeAvisHebergementMedia(AvisHebergementMedia avisHebergementMedia) {
        this.avisHebergementMedias.remove(avisHebergementMedia);
        avisHebergementMedia.setAvisHebergement(null);
        return this;
    }

    public void setAvisHebergementMedias(Set<AvisHebergementMedia> avisHebergementMedias) {
        this.avisHebergementMedias = avisHebergementMedias;
    }

    public Hebergement getHebergement() {
        return hebergement;
    }

    public AvisHebergement hebergement(Hebergement hebergement) {
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
        if (!(o instanceof AvisHebergement)) {
            return false;
        }
        return id != null && id.equals(((AvisHebergement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvisHebergement{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", nbreEtoile='" + getNbreEtoile() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
