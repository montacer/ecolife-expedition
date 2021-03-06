package ch.itsforward.ecolifeexpedition.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Tour.
 */
@Entity
@Table(name = "tour")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tour implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lib_titre")
    private String libTitre;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "video_url")
    private String videoUrl;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;
    
    
    private byte[] video;

    @Column(name = "video_content_type")
    private String videoContentType;

    @Column(name = "conseil")
    private String conseil;

    @Column(name = "prix_ttc")
    private Float prixTTC;

    @OneToOne
    @JoinColumn(unique = true)
    private Reservation reservation;

    @OneToMany(mappedBy = "tour")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ServiceSupplementaire> serviceSupplementaires = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "tours", allowSetters = true)
    private Region region;

    @ManyToOne
    @JsonIgnoreProperties(value = "tours", allowSetters = true)
    private TypeCircuit typeCircuit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibTitre() {
        return libTitre;
    }

    public Tour libTitre(String libTitre) {
        this.libTitre = libTitre;
        return this;
    }

    public void setLibTitre(String libTitre) {
        this.libTitre = libTitre;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Tour imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Tour videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public byte[] getImage() {
        return image;
    }

    public Tour image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Tour imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getVideo() {
        return video;
    }

    public Tour video(byte[] video) {
        this.video = video;
        return this;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }

    public String getVideoContentType() {
        return videoContentType;
    }

    public Tour videoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
        return this;
    }

    public void setVideoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
    }

    public String getConseil() {
        return conseil;
    }

    public Tour conseil(String conseil) {
        this.conseil = conseil;
        return this;
    }

    public void setConseil(String conseil) {
        this.conseil = conseil;
    }

    public Float getPrixTTC() {
        return prixTTC;
    }

    public Tour prixTTC(Float prixTTC) {
        this.prixTTC = prixTTC;
        return this;
    }

    public void setPrixTTC(Float prixTTC) {
        this.prixTTC = prixTTC;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Tour reservation(Reservation reservation) {
        this.reservation = reservation;
        return this;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Set<ServiceSupplementaire> getServiceSupplementaires() {
        return serviceSupplementaires;
    }

    public Tour serviceSupplementaires(Set<ServiceSupplementaire> serviceSupplementaires) {
        this.serviceSupplementaires = serviceSupplementaires;
        return this;
    }

    public Tour addServiceSupplementaire(ServiceSupplementaire serviceSupplementaire) {
        this.serviceSupplementaires.add(serviceSupplementaire);
        serviceSupplementaire.setTour(this);
        return this;
    }

    public Tour removeServiceSupplementaire(ServiceSupplementaire serviceSupplementaire) {
        this.serviceSupplementaires.remove(serviceSupplementaire);
        serviceSupplementaire.setTour(null);
        return this;
    }

    public void setServiceSupplementaires(Set<ServiceSupplementaire> serviceSupplementaires) {
        this.serviceSupplementaires = serviceSupplementaires;
    }

    public Region getRegion() {
        return region;
    }

    public Tour region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public TypeCircuit getTypeCircuit() {
        return typeCircuit;
    }

    public Tour typeCircuit(TypeCircuit typeCircuit) {
        this.typeCircuit = typeCircuit;
        return this;
    }

    public void setTypeCircuit(TypeCircuit typeCircuit) {
        this.typeCircuit = typeCircuit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tour)) {
            return false;
        }
        return id != null && id.equals(((Tour) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tour{" +
            "id=" + getId() +
            ", libTitre='" + getLibTitre() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", videoUrl='" + getVideoUrl() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", video='" + getVideo() + "'" +
            ", videoContentType='" + getVideoContentType() + "'" +
            ", conseil='" + getConseil() + "'" +
            ", prixTTC=" + getPrixTTC() +
            "}";
    }
}
