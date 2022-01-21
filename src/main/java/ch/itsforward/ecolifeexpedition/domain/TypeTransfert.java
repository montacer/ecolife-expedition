package ch.itsforward.ecolifeexpedition.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeTransfert.
 */
@Entity
@Table(name = "type_transfert")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TypeTransfert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lib_type_transfert")
    private String libTypeTransfert;

    @OneToMany(mappedBy = "typeTransfert")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Transfert> transferts = new HashSet<>();

    @OneToMany(mappedBy = "typeTransfert")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TarifTransfert> tarifTransferts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibTypeTransfert() {
        return libTypeTransfert;
    }

    public TypeTransfert libTypeTransfert(String libTypeTransfert) {
        this.libTypeTransfert = libTypeTransfert;
        return this;
    }

    public void setLibTypeTransfert(String libTypeTransfert) {
        this.libTypeTransfert = libTypeTransfert;
    }

    public Set<Transfert> getTransferts() {
        return transferts;
    }

    public TypeTransfert transferts(Set<Transfert> transferts) {
        this.transferts = transferts;
        return this;
    }

    public TypeTransfert addTransfert(Transfert transfert) {
        this.transferts.add(transfert);
        transfert.setTypeTransfert(this);
        return this;
    }

    public TypeTransfert removeTransfert(Transfert transfert) {
        this.transferts.remove(transfert);
        transfert.setTypeTransfert(null);
        return this;
    }

    public void setTransferts(Set<Transfert> transferts) {
        this.transferts = transferts;
    }

    public Set<TarifTransfert> getTarifTransferts() {
        return tarifTransferts;
    }

    public TypeTransfert tarifTransferts(Set<TarifTransfert> tarifTransferts) {
        this.tarifTransferts = tarifTransferts;
        return this;
    }

    public TypeTransfert addTarifTransfert(TarifTransfert tarifTransfert) {
        this.tarifTransferts.add(tarifTransfert);
        tarifTransfert.setTypeTransfert(this);
        return this;
    }

    public TypeTransfert removeTarifTransfert(TarifTransfert tarifTransfert) {
        this.tarifTransferts.remove(tarifTransfert);
        tarifTransfert.setTypeTransfert(null);
        return this;
    }

    public void setTarifTransferts(Set<TarifTransfert> tarifTransferts) {
        this.tarifTransferts = tarifTransferts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeTransfert)) {
            return false;
        }
        return id != null && id.equals(((TypeTransfert) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeTransfert{" +
            "id=" + getId() +
            ", libTypeTransfert='" + getLibTypeTransfert() + "'" +
            "}";
    }
}
