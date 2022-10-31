/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c"),
    @NamedQuery(name = "Clientes.findByIdCliente", query = "SELECT c FROM Clientes c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Clientes.findByPrNomClien", query = "SELECT c FROM Clientes c WHERE c.prNomClien = :prNomClien"),
    @NamedQuery(name = "Clientes.findBySdNomClien", query = "SELECT c FROM Clientes c WHERE c.sdNomClien = :sdNomClien"),
    @NamedQuery(name = "Clientes.findByPrApellClien", query = "SELECT c FROM Clientes c WHERE c.prApellClien = :prApellClien"),
    @NamedQuery(name = "Clientes.findBySdApellClien", query = "SELECT c FROM Clientes c WHERE c.sdApellClien = :sdApellClien"),
    @NamedQuery(name = "Clientes.findByCorreoClien", query = "SELECT c FROM Clientes c WHERE c.correoClien = :correoClien"),
    @NamedQuery(name = "Clientes.findByTelClien", query = "SELECT c FROM Clientes c WHERE c.telClien = :telClien"),
    @NamedQuery(name = "Clientes.findByDirClien", query = "SELECT c FROM Clientes c WHERE c.dirClien = :dirClien")})
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCliente")
    private Integer idCliente;
    @Basic(optional = false)
    @Column(name = "pr_Nom_Clien")
    private String prNomClien;
    @Column(name = "sd_Nom_Clien")
    private String sdNomClien;
    @Basic(optional = false)
    @Column(name = "pr_Apell_Clien")
    private String prApellClien;
    @Basic(optional = false)
    @Column(name = "sd_Apell_Clien")
    private String sdApellClien;
    @Basic(optional = false)
    @Column(name = "correo_Clien")
    private String correoClien;
    @Basic(optional = false)
    @Column(name = "tel_Clien")
    private String telClien;
    @Basic(optional = false)
    @Column(name = "dir_Clien")
    private String dirClien;
    @OneToMany(mappedBy = "idCliente", fetch = FetchType.LAZY)
    private Collection<Recibos> recibosCollection;

    public Clientes() {
    }

    public Clientes(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Clientes(Integer idCliente, String prNomClien, String prApellClien, String sdApellClien, String correoClien, String telClien, String dirClien) {
        this.idCliente = idCliente;
        this.prNomClien = prNomClien;
        this.prApellClien = prApellClien;
        this.sdApellClien = sdApellClien;
        this.correoClien = correoClien;
        this.telClien = telClien;
        this.dirClien = dirClien;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getPrNomClien() {
        return prNomClien;
    }

    public void setPrNomClien(String prNomClien) {
        this.prNomClien = prNomClien;
    }

    public String getSdNomClien() {
        return sdNomClien;
    }

    public void setSdNomClien(String sdNomClien) {
        this.sdNomClien = sdNomClien;
    }

    public String getPrApellClien() {
        return prApellClien;
    }

    public void setPrApellClien(String prApellClien) {
        this.prApellClien = prApellClien;
    }

    public String getSdApellClien() {
        return sdApellClien;
    }

    public void setSdApellClien(String sdApellClien) {
        this.sdApellClien = sdApellClien;
    }

    public String getCorreoClien() {
        return correoClien;
    }

    public void setCorreoClien(String correoClien) {
        this.correoClien = correoClien;
    }

    public String getTelClien() {
        return telClien;
    }

    public void setTelClien(String telClien) {
        this.telClien = telClien;
    }

    public String getDirClien() {
        return dirClien;
    }

    public void setDirClien(String dirClien) {
        this.dirClien = dirClien;
    }

    @XmlTransient
    public Collection<Recibos> getRecibosCollection() {
        return recibosCollection;
    }

    public void setRecibosCollection(Collection<Recibos> recibosCollection) {
        this.recibosCollection = recibosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "["+idCliente+"] : "+prNomClien+" , "+sdNomClien+" , "+prApellClien+" , "+sdApellClien+" , "+correoClien+" , "+telClien+" , "+dirClien;
    }
    
}
