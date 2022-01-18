package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import ch.itsforward.ecolifeexpedition.domain.enumeration.MediaType;

/**
 * A TourMedia.
 */
@Entity
@Table(name = "tour_media")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TourMedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "media_url")
    private String mediaUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type")
    private MediaType mediaType;

    @Lob
    @Column(name = "mediacontent")
    private byte[] mediacontent;

    @Column(name = "mediacontent_content_type")
    private String mediacontentContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "tourMedias", allowSetters = true)
    private Tour tour;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public TourMedia mediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
        return this;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public TourMedia mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getMediacontent() {
        return mediacontent;
    }

    public TourMedia mediacontent(byte[] mediacontent) {
        this.mediacontent = mediacontent;
        return this;
    }

    public void setMediacontent(byte[] mediacontent) {
        this.mediacontent = mediacontent;
    }

    public String getMediacontentContentType() {
        return mediacontentContentType;
    }

    public TourMedia mediacontentContentType(String mediacontentContentType) {
        this.mediacontentContentType = mediacontentContentType;
        return this;
    }

    public void setMediacontentContentType(String mediacontentContentType) {
        this.mediacontentContentType = mediacontentContentType;
    }

    public Tour getTour() {
        return tour;
    }

    public TourMedia tour(Tour tour) {
        this.tour = tour;
        return this;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TourMedia)) {
            return false;
        }
        return id != null && id.equals(((TourMedia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TourMedia{" +
            "id=" + getId() +
            ", mediaUrl='" + getMediaUrl() + "'" +
            ", mediaType='" + getMediaType() + "'" +
            ", mediacontent='" + getMediacontent() + "'" +
            ", mediacontentContentType='" + getMediacontentContentType() + "'" +
            "}";
    }
}
