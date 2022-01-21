package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import ch.itsforward.ecolifeexpedition.domain.enumeration.MediaType;

/**
 * A HotelMedia.
 */
@Entity
@Table(name = "hotel_media")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HotelMedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "media_url")
    private String mediaUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type")
    private MediaType mediaType;

    
    @Column(name = "mediacontent")
    private byte[] mediacontent;

    @Column(name = "mediacontent_content_type")
    private String mediacontentContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "hotelMedias", allowSetters = true)
    private Hotel hotel;

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

    public HotelMedia mediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
        return this;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public HotelMedia mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getMediacontent() {
        return mediacontent;
    }

    public HotelMedia mediacontent(byte[] mediacontent) {
        this.mediacontent = mediacontent;
        return this;
    }

    public void setMediacontent(byte[] mediacontent) {
        this.mediacontent = mediacontent;
    }

    public String getMediacontentContentType() {
        return mediacontentContentType;
    }

    public HotelMedia mediacontentContentType(String mediacontentContentType) {
        this.mediacontentContentType = mediacontentContentType;
        return this;
    }

    public void setMediacontentContentType(String mediacontentContentType) {
        this.mediacontentContentType = mediacontentContentType;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public HotelMedia hotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelMedia)) {
            return false;
        }
        return id != null && id.equals(((HotelMedia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HotelMedia{" +
            "id=" + getId() +
            ", mediaUrl='" + getMediaUrl() + "'" +
            ", mediaType='" + getMediaType() + "'" +
            ", mediacontent='" + getMediacontent() + "'" +
            ", mediacontentContentType='" + getMediacontentContentType() + "'" +
            "}";
    }
}
