/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nova
 */
@Entity
@Table(name = "bodegas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bodegas.findAll", query = "SELECT b FROM Bodegas b"),
    @NamedQuery(name = "Bodegas.findByIdBodega", query = "SELECT b FROM Bodegas b WHERE b.idBodega = :idBodega"),
    @NamedQuery(name = "Bodegas.findByNomBodega", query = "SELECT b FROM Bodegas b WHERE b.nomBodega = :nomBodega"),
    @NamedQuery(name = "Bodegas.findIdByNomBodega", query = "SELECT b.idBodega FROM Bodegas b WHERE b.nomBodega = :nomBodega"),
    @NamedQuery(name = "Bodegas.findByDirBodega", query = "SELECT b FROM Bodegas b WHERE b.dirBodega = :dirBodega"),
    @NamedQuery(name = "Bodegas.findByTelBodega", query = "SELECT b FROM Bodegas b WHERE b.telBodega = :telBodega"),
    @NamedQuery(name = "Bodegas.findIdByTelBodega", query = "SELECT b.idBodega FROM Bodegas b WHERE b.telBodega = :telBodega"),
    @NamedQuery(name = "Bodegas.findIdByNomTelBodega", query = "SELECT b.idBodega FROM Bodegas b WHERE b.nomBodega = :nomBodega AND b.telBodega = :telBodega")})
public class Bodegas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBodega")
    private Integer idBodega;
    @Basic(optional = false)
    @Column(name = "nom_Bodega")
    private String nomBodega;
    @Basic(optional = false)
    @Column(name = "dir_Bodega")
    private String dirBodega;
    @Basic(optional = false)
    @Column(name = "tel_Bodega")
    private String telBodega;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBodega", fetch = FetchType.LAZY)
    private Collection<Departamentos> departamentosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBodega", fetch = FetchType.LAZY)
    private Collection<Tiendas> tiendasCollection;

    public Bodegas() {
    }

    public Bodegas(Integer idBodega) {
        this.idBodega = idBodega;
    }

    public Bodegas(Integer idBodega, String nomBodega, String dirBodega, String telBodega) {
        this.idBodega = idBodega;
        this.nomBodega = nomBodega;
        this.dirBodega = dirBodega;
        this.telBodega = telBodega;
    }

    public Integer getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(Integer idBodega) {
        this.idBodega = idBodega;
    }

    public String getNomBodega() {
        return nomBodega;
    }

    public void setNomBodega(String nomBodega) {
        this.nomBodega = nomBodega;
    }

    public String getDirBodega() {
        return dirBodega;
    }

    public void setDirBodega(String dirBodega) {
        this.dirBodega = dirBodega;
    }

    public String getTelBodega() {
        return telBodega;
    }

    public void setTelBodega(String telBodega) {
        this.telBodega = telBodega;
    }

    @XmlTransient
    public Collection<Departamentos> getDepartamentosCollection() {
        return departamentosCollection;
    }

    public void setDepartamentosCollection(Collection<Departamentos> departamentosCollection) {
        this.departamentosCollection = departamentosCollection;
    }

    @XmlTransient
    public Collection<Tiendas> getTiendasCollection() {
        return tiendasCollection;
    }

    public void setTiendasCollection(Collection<Tiendas> tiendasCollection) {
        this.tiendasCollection = tiendasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBodega != null ? idBodega.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bodegas)) {
            return false;
        }
        Bodegas other = (Bodegas) object;
        if ((this.idBodega == null && other.idBodega != null) || (this.idBodega != null && !this.idBodega.equals(other.idBodega))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[" + idBodega + "] : " + nomBodega + " , " + dirBodega + " , " + telBodega;
    }

}
