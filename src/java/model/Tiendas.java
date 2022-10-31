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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tiendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tiendas.findAll", query = "SELECT t FROM Tiendas t"),
    @NamedQuery(name = "Tiendas.findByIdTienda", query = "SELECT t FROM Tiendas t WHERE t.idTienda = :idTienda"),
    @NamedQuery(name = "Tiendas.findByNomTienda", query = "SELECT t FROM Tiendas t WHERE t.nomTienda = :nomTienda"),
    @NamedQuery(name = "Tiendas.findByDirTienda", query = "SELECT t FROM Tiendas t WHERE t.dirTienda = :dirTienda"),
    @NamedQuery(name = "Tiendas.findByTelTienda", query = "SELECT t FROM Tiendas t WHERE t.telTienda = :telTienda")})
public class Tiendas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTienda")
    private Integer idTienda;
    @Basic(optional = false)
    @Column(name = "nom_Tienda")
    private String nomTienda;
    @Basic(optional = false)
    @Column(name = "dir_Tienda")
    private String dirTienda;
    @Basic(optional = false)
    @Column(name = "tel_Tienda")
    private String telTienda;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTienda", fetch = FetchType.LAZY)
    private Collection<Recibos> recibosCollection;
    @JoinColumn(name = "idBodega", referencedColumnName = "idBodega")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Bodegas idBodega;

    public Tiendas() {
    }

    public Tiendas(Integer idTienda) {
        this.idTienda = idTienda;
    }

    public Tiendas(Integer idTienda, String nomTienda, String dirTienda, String telTienda) {
        this.idTienda = idTienda;
        this.nomTienda = nomTienda;
        this.dirTienda = dirTienda;
        this.telTienda = telTienda;
    }

    public Integer getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(Integer idTienda) {
        this.idTienda = idTienda;
    }

    public String getNomTienda() {
        return nomTienda;
    }

    public void setNomTienda(String nomTienda) {
        this.nomTienda = nomTienda;
    }

    public String getDirTienda() {
        return dirTienda;
    }

    public void setDirTienda(String dirTienda) {
        this.dirTienda = dirTienda;
    }

    public String getTelTienda() {
        return telTienda;
    }

    public void setTelTienda(String telTienda) {
        this.telTienda = telTienda;
    }

    @XmlTransient
    public Collection<Recibos> getRecibosCollection() {
        return recibosCollection;
    }

    public void setRecibosCollection(Collection<Recibos> recibosCollection) {
        this.recibosCollection = recibosCollection;
    }

    public Bodegas getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(Bodegas idBodega) {
        this.idBodega = idBodega;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTienda != null ? idTienda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tiendas)) {
            return false;
        }
        Tiendas other = (Tiendas) object;
        if ((this.idTienda == null && other.idTienda != null) || (this.idTienda != null && !this.idTienda.equals(other.idTienda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "["+idTienda+"] : "+nomTienda+" , "+dirTienda+" , "+telTienda+" , "+idBodega;
    }
    
}
