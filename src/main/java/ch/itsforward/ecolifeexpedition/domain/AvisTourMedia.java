package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import ch.itsforward.ecolifeexpedition.domain.enumeration.MediaType;

import ch.itsforward.ecolifeexpedition.domain.enumeration.Stars;

/**
 * A AvisTourMedia.
 */
@Entity
@Table(name = "avis_tour_media")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AvisTourMedia implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "score")
    private Stars score;

    @ManyToOne
    @JsonIgnoreProperties(value = "avisTourMedias", allowSetters = true)
    private AvisTour avisTour;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public AvisTourMedia mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public AvisTourMedia mediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
        return this;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public byte[] getMediacontent() {
        return mediacontent;
    }

    public AvisTourMedia mediacontent(byte[] mediacontent) {
        this.mediacontent = mediacontent;
        return this;
    }

    public void setMediacontent(byte[] mediacontent) {
        this.mediacontent = mediacontent;
    }

    public String getMediacontentContentType() {
        return mediacontentContentType;
    }

    public AvisTourMedia mediacontentContentType(String mediacontentContentType) {
        this.mediacontentContentType = mediacontentContentType;
        return this;
    }

    public void setMediacontentContentType(String mediacontentContentType) {
        this.mediacontentContentType = mediacontentContentType;
    }

    public Stars getScore() {
        return score;
    }

    public AvisTourMedia score(Stars score) {
        this.score = score;
        return this;
    }

    public void setScore(Stars score) {
        this.score = score;
    }

    public AvisTour getAvisTour() {
        return avisTour;
    }

    public AvisTourMedia avisTour(AvisTour avisTour) {
        this.avisTour = avisTour;
        return this;
    }

    public void setAvisTour(AvisTour avisTour) {
        this.avisTour = avisTour;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvisTourMedia)) {
            return false;
        }
        return id != null && id.equals(((AvisTourMedia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvisTourMedia{" +
            "id=" + getId() +
            ", mediaType='" + getMediaType() + "'" +
            ", mediaUrl='" + getMediaUrl() + "'" +
            ", mediacontent='" + getMediacontent() + "'" +
            ", mediacontentContentType='" + getMediacontentContentType() + "'" +
            ", score='" + getScore() + "'" +
            "}";
    }
}
