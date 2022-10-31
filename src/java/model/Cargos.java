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
@Table(name = "cargos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cargos.findAll", query = "SELECT c FROM Cargos c"),
    @NamedQuery(name = "Cargos.findByIdCargo", query = "SELECT c FROM Cargos c WHERE c.idCargo = :idCargo"),
    @NamedQuery(name = "Cargos.findByNomCargo", query = "SELECT c FROM Cargos c WHERE c.nomCargo = :nomCargo"),
    @NamedQuery(name = "Cargos.findByDescCargo", query = "SELECT c FROM Cargos c WHERE c.descCargo = :descCargo"),
    @NamedQuery(name = "Cargos.findBySalarioCargo", query = "SELECT c FROM Cargos c WHERE c.salarioCargo = :salarioCargo")})
public class Cargos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCargo")
    private Integer idCargo;
    @Basic(optional = false)
    @Column(name = "nom_Cargo")
    private String nomCargo;
    @Basic(optional = false)
    @Column(name = "desc_Cargo")
    private String descCargo;
    @Basic(optional = false)
    @Column(name = "salario_Cargo")
    private double salarioCargo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCargo", fetch = FetchType.LAZY)
    private Collection<Empleados> empleadosCollection;

    public Cargos() {
    }

    public Cargos(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public Cargos(Integer idCargo, String nomCargo, String descCargo, double salarioCargo) {
        this.idCargo = idCargo;
        this.nomCargo = nomCargo;
        this.descCargo = descCargo;
        this.salarioCargo = salarioCargo;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNomCargo() {
        return nomCargo;
    }

    public void setNomCargo(String nomCargo) {
        this.nomCargo = nomCargo;
    }

    public String getDescCargo() {
        return descCargo;
    }

    public void setDescCargo(String descCargo) {
        this.descCargo = descCargo;
    }

    public double getSalarioCargo() {
        return salarioCargo;
    }

    public void setSalarioCargo(double salarioCargo) {
        this.salarioCargo = salarioCargo;
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
        hash += (idCargo != null ? idCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cargos)) {
            return false;
        }
        Cargos other = (Cargos) object;
        if ((this.idCargo == null && other.idCargo != null) || (this.idCargo != null && !this.idCargo.equals(other.idCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "["+idCargo+"] : "+nomCargo+" , "+descCargo+" , "+salarioCargo;
    }
    
}
