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
@Table(name = "departamentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Departamentos.findAll", query = "SELECT d FROM Departamentos d"),
    @NamedQuery(name = "Departamentos.findByIdDept", query = "SELECT d FROM Departamentos d WHERE d.idDept = :idDept"),
    @NamedQuery(name = "Departamentos.findByNomDept", query = "SELECT d FROM Departamentos d WHERE d.nomDept = :nomDept"),
    @NamedQuery(name = "Departamentos.findIdByNomDept", query = "SELECT d.idDept FROM Departamentos d WHERE d.nomDept = :nomDept"),
    @NamedQuery(name = "Departamentos.findByDirDept", query = "SELECT d FROM Departamentos d WHERE d.dirDept = :dirDept")})
public class Departamentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDept")
    private Integer idDept;
    @Basic(optional = false)
    @Column(name = "nom_Dept")
    private String nomDept;
    @Basic(optional = false)
    @Column(name = "dir_Dept")
    private String dirDept;
    @JoinColumn(name = "idBodega", referencedColumnName = "idBodega")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Bodegas idBodega;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDept", fetch = FetchType.LAZY)
    private Collection<Empleados> empleadosCollection;

    public Departamentos() {
    }

    public Departamentos(Integer idDept) {
        this.idDept = idDept;
    }

    public Departamentos(Integer idDept, String nomDept, String dirDept) {
        this.idDept = idDept;
        this.nomDept = nomDept;
        this.dirDept = dirDept;
    }

    public Integer getIdDept() {
        return idDept;
    }

    public void setIdDept(Integer idDept) {
        this.idDept = idDept;
    }

    public String getNomDept() {
        return nomDept;
    }

    public void setNomDept(String nomDept) {
        this.nomDept = nomDept;
    }

    public String getDirDept() {
        return dirDept;
    }

    public void setDirDept(String dirDept) {
        this.dirDept = dirDept;
    }

    public Bodegas getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(Bodegas idBodega) {
        this.idBodega = idBodega;
    }

    @XmlTransient
    public Collection<Empleados> getEmpleadosCollection() {
        return empleadosCollection;
    }

    public void setEmpleadosCollection(Collection<Empleados> empleadosCollection) {
        this.empleadosCollection = empleadosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDept != null ? idDept.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departamentos)) {
            return false;
        }
        Departamentos other = (Departamentos) object;
        if ((this.idDept == null && other.idDept != null) || (this.idDept != null && !this.idDept.equals(other.idDept))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[" + idDept + "] : " + nomDept + " , " + dirDept + " , " + idBodega;
    }

}
