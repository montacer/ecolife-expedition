package ch.itsforward.ecolifeexpedition.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import ch.itsforward.ecolifeexpedition.domain.enumeration.Stars;

/**
 * MODULE AVIS ET COMMENTAIRE
 */
@ApiModel(description = "MODULE AVIS ET COMMENTAIRE")
@Entity
@Table(name = "avis_tour")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AvisTour implements Serializable {

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

    @OneToMany(mappedBy = "avisTour")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AvisTourMedia> avisTourMedias = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public AvisTour author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Stars getNbreEtoile() {
        return nbreEtoile;
    }

    public AvisTour nbreEtoile(Stars nbreEtoile) {
        this.nbreEtoile = nbreEtoile;
        return this;
    }

    public void setNbreEtoile(Stars nbreEtoile) {
        this.nbreEtoile = nbreEtoile;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public AvisTour commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Set<AvisTourMedia> getAvisTourMedias() {
        return avisTourMedias;
    }

    public AvisTour avisTourMedias(Set<AvisTourMedia> avisTourMedias) {
        this.avisTourMedias = avisTourMedias;
        return this;
    }

    public AvisTour addAvisTourMedia(AvisTourMedia avisTourMedia) {
        this.avisTourMedias.add(avisTourMedia);
        avisTourMedia.setAvisTour(this);
        return this;
    }

    public AvisTour removeAvisTourMedia(AvisTourMedia avisTourMedia) {
        this.avisTourMedias.remove(avisTourMedia);
        avisTourMedia.setAvisTour(null);
        return this;
    }

    public void setAvisTourMedias(Set<AvisTourMedia> avisTourMedias) {
        this.avisTourMedias = avisTourMedias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvisTour)) {
            return false;
        }
        return id != null && id.equals(((AvisTour) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvisTour{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", nbreEtoile='" + getNbreEtoile() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
