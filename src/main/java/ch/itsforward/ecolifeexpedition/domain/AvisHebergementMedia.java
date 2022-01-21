package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import ch.itsforward.ecolifeexpedition.domain.enumeration.MediaType;

/**
 * A AvisHebergementMedia.
 */
@Entity
@Table(name = "avis_hebergement_media")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AvisHebergementMedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type")
    private MediaType mediaType;

    @Column(name = "media_url")
    private String mediaUrl;

    
    @Column(name = "mediacontent")
    private byte[] mediacontent;

    @Column(name = "mediacontent_content_type")
    private String mediacontentContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "avisHebergementMedias", allowSetters = true)
    private AvisHebergement avisHebergement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public AvisHebergementMedia mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public AvisHebergementMedia mediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
        return this;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public byte[] getMediacontent() {
        return mediacontent;
    }

    public AvisHebergementMedia mediacontent(byte[] mediacontent) {
        this.mediacontent = mediacontent;
        return this;
    }

    public void setMediacontent(byte[] mediacontent) {
        this.mediacontent = mediacontent;
    }

    public String getMediacontentContentType() {
        return mediacontentContentType;
    }

    public AvisHebergementMedia mediacontentContentType(String mediacontentContentType) {
        this.mediacontentContentType = mediacontentContentType;
        return this;
    }

    public void setMediacontentContentType(String mediacontentContentType) {
        this.mediacontentContentType = mediacontentContentType;
    }

    public AvisHebergement getAvisHebergement() {
        return avisHebergement;
    }

    public AvisHebergementMedia avisHebergement(AvisHebergement avisHebergement) {
        this.avisHebergement = avisHebergement;
        return this;
    }

    public void setAvisHebergement(AvisHebergement avisHebergement) {
        this.avisHebergement = avisHebergement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvisHebergementMedia)) {
            return false;
        }
        return id != null && id.equals(((AvisHebergementMedia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvisHebergementMedia{" +
            "id=" + getId() +
            ", mediaType='" + getMediaType() + "'" +
            ", mediaUrl='" + getMediaUrl() + "'" +
            ", mediacontent='" + getMediacontent() + "'" +
            ", mediacontentContentType='" + getMediacontentContentType() + "'" +
            "}";
    }
}
