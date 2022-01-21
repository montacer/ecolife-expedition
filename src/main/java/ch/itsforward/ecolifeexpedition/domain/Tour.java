package ch.itsforward.ecolifeexpedition.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import ch.itsforward.ecolifeexpedition.domain.enumeration.Saison;

import ch.itsforward.ecolifeexpedition.domain.enumeration.TourStatus;

/**
 * A Tour.
 */
@Entity
@Table(name = "tour")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

//    @Lob
//    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "image_content")
    private byte[] imageContent;

    @Column(name = "image_content_content_type")
    private String imageContentContentType;


    @Column(name = "video_content")
    private byte[] videoContent;

    @Column(name = "video_content_content_type")
    private String videoContentContentType;

    @Column(name = "conseil")
    private String conseil;

    @Column(name = "prix_ttc")
    private Float prixTTC;

    @Column(name = "description")
    private String description;

    @Column(name = "remise")
    private Boolean remise;

    @Column(name = "prix_remise")
    private Float prixRemise;

    @Column(name = "star_score")
    private Float starScore;

    @Column(name = "duree")
    private Duration duree;

    @Enumerated(EnumType.STRING)
    @Column(name = "saison")
    private Saison saison;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TourStatus status;

    @OneToMany(mappedBy = "tour")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TourMedia> tourMedias = new HashSet<>();

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

    public byte[] getImageContent() {
        return imageContent;
    }

    public Tour imageContent(byte[] imageContent) {
        this.imageContent = imageContent;
        return this;
    }

    public void setImageContent(byte[] imageContent) {
        this.imageContent = imageContent;
    }

    public String getImageContentContentType() {
        return imageContentContentType;
    }

    public Tour imageContentContentType(String imageContentContentType) {
        this.imageContentContentType = imageContentContentType;
        return this;
    }

    public void setImageContentContentType(String imageContentContentType) {
        this.imageContentContentType = imageContentContentType;
    }

    public byte[] getVideoContent() {
        return videoContent;
    }

    public Tour videoContent(byte[] videoContent) {
        this.videoContent = videoContent;
        return this;
    }

    public void setVideoContent(byte[] videoContent) {
        this.videoContent = videoContent;
    }

    public String getVideoContentContentType() {
        return videoContentContentType;
    }

    public Tour videoContentContentType(String videoContentContentType) {
        this.videoContentContentType = videoContentContentType;
        return this;
    }

    public void setVideoContentContentType(String videoContentContentType) {
        this.videoContentContentType = videoContentContentType;
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

    public String getDescription() {
        return description;
    }

    public Tour description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isRemise() {
        return remise;
    }

    public Tour remise(Boolean remise) {
        this.remise = remise;
        return this;
    }

    public void setRemise(Boolean remise) {
        this.remise = remise;
    }

    public Float getPrixRemise() {
        return prixRemise;
    }

    public Tour prixRemise(Float prixRemise) {
        this.prixRemise = prixRemise;
        return this;
    }

    public void setPrixRemise(Float prixRemise) {
        this.prixRemise = prixRemise;
    }

    public Float getStarScore() {
        return starScore;
    }

    public Tour starScore(Float starScore) {
        this.starScore = starScore;
        return this;
    }

    public void setStarScore(Float starScore) {
        this.starScore = starScore;
    }

    public Duration getDuree() {
        return duree;
    }

    public Tour duree(Duration duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(Duration duree) {
        this.duree = duree;
    }

    public Saison getSaison() {
        return saison;
    }

    public Tour saison(Saison saison) {
        this.saison = saison;
        return this;
    }

    public void setSaison(Saison saison) {
        this.saison = saison;
    }

    public TourStatus getStatus() {
        return status;
    }

    public Tour status(TourStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TourStatus status) {
        this.status = status;
    }

    public Set<TourMedia> getTourMedias() {
        return tourMedias;
    }

    public Tour tourMedias(Set<TourMedia> tourMedias) {
        this.tourMedias = tourMedias;
        return this;
    }

    public Tour addTourMedia(TourMedia tourMedia) {
        this.tourMedias.add(tourMedia);
        tourMedia.setTour(this);
        return this;
    }

    public Tour removeTourMedia(TourMedia tourMedia) {
        this.tourMedias.remove(tourMedia);
        tourMedia.setTour(null);
        return this;
    }

    public void setTourMedias(Set<TourMedia> tourMedias) {
        this.tourMedias = tourMedias;
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
            ", imageContent='" + getImageContent() + "'" +
            ", imageContentContentType='" + getImageContentContentType() + "'" +
            ", videoContent='" + getVideoContent() + "'" +
            ", videoContentContentType='" + getVideoContentContentType() + "'" +
            ", conseil='" + getConseil() + "'" +
            ", prixTTC=" + getPrixTTC() +
            ", description='" + getDescription() + "'" +
            ", remise='" + isRemise() + "'" +
            ", prixRemise=" + getPrixRemise() +
            ", starScore=" + getStarScore() +
            ", duree='" + getDuree() + "'" +
            ", saison='" + getSaison() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
