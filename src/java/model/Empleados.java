/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nova
 */
@Entity
@Table(name = "empleados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e"),
    @NamedQuery(name = "Empleados.findByIdEmpleado", query = "SELECT e FROM Empleados e WHERE e.idEmpleado = :idEmpleado"),
    @NamedQuery(name = "Empleados.findByPrNomEmpl", query = "SELECT e FROM Empleados e WHERE e.prNomEmpl = :prNomEmpl"),
    @NamedQuery(name = "Empleados.findBySdNomEmpl", query = "SELECT e FROM Empleados e WHERE e.sdNomEmpl = :sdNomEmpl"),
    @NamedQuery(name = "Empleados.findByPrApellEmpl", query = "SELECT e FROM Empleados e WHERE e.prApellEmpl = :prApellEmpl"),
    @NamedQuery(name = "Empleados.findBySdApellEmpl", query = "SELECT e FROM Empleados e WHERE e.sdApellEmpl = :sdApellEmpl"),
    @NamedQuery(name = "Empleados.findByDirEmpl", query = "SELECT e FROM Empleados e WHERE e.dirEmpl = :dirEmpl"),
    @NamedQuery(name = "Empleados.findByTelEmpl", query = "SELECT e FROM Empleados e WHERE e.telEmpl = :telEmpl"),
    @NamedQuery(name = "Empleados.findByCorreoEmpl", query = "SELECT e FROM Empleados e WHERE e.correoEmpl = :correoEmpl"),
    @NamedQuery(name = "Empleados.findIdByCorreoEmpl", query = "SELECT e.idEmpleado FROM Empleados e WHERE e.correoEmpl = :correoEmpl"),
    @NamedQuery(name = "Empleados.findIdByTelEmpl", query = "SELECT e.idEmpleado FROM Empleados e WHERE e.telEmpl = :telEmpl"),
    @NamedQuery(name = "Empleados.findByFechaNaciEmpl", query = "SELECT e FROM Empleados e WHERE e.fechaNaciEmpl = :fechaNaciEmpl"),
    @NamedQuery(name = "Empleados.findByCiudEmpl", query = "SELECT e FROM Empleados e WHERE e.ciudEmpl = :ciudEmpl"),
    @NamedQuery(name = "Empleados.findByPaisEmpl", query = "SELECT e FROM Empleados e WHERE e.paisEmpl = :paisEmpl"),
    @NamedQuery(name = "Empleados.findIdByCorreoTelEmpl", query = "SELECT e.idEmpleado FROM Empleados e WHERE e.correoEmpl = :correoEmpl AND e.telEmpl = :telEmpl")})
public class Empleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEmpleado")
    private Integer idEmpleado;
    @Basic(optional = false)
    @Column(name = "pr_Nom_Empl")
    private String prNomEmpl;
    @Column(name = "sd_Nom_Empl")
    private String sdNomEmpl;
    @Basic(optional = false)
    @Column(name = "pr_Apell_Empl")
    private String prApellEmpl;
    @Basic(optional = false)
    @Column(name = "sd_Apell_Empl")
    private String sdApellEmpl;
    @Basic(optional = false)
    @Column(name = "dir_Empl")
    private String dirEmpl;
    @Basic(optional = false)
    @Column(name = "tel_Empl")
    private String telEmpl;
    @Basic(optional = false)
    @Column(name = "correo_Empl")
    private String correoEmpl;
    @Basic(optional = false)
    @Column(name = "fecha_Naci_Empl")
    @Temporal(TemporalType.DATE)
    private Date fechaNaciEmpl;
    @Basic(optional = false)
    @Column(name = "ciud_Empl")
    private String ciudEmpl;
    @Basic(optional = false)
    @Column(name = "pais_Empl")
    private String paisEmpl;
    @JoinColumn(name = "idCargo", referencedColumnName = "idCargo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cargos idCargo;
    @JoinColumn(name = "idDept", referencedColumnName = "idDept")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Departamentos idDept;

    public Empleados() {
    }

    public Empleados(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Empleados(Integer idEmpleado, String prNomEmpl, String prApellEmpl, String sdApellEmpl, String dirEmpl, String telEmpl, String correoEmpl, Date fechaNaciEmpl, String ciudEmpl, String paisEmpl) {
        this.idEmpleado = idEmpleado;
        this.prNomEmpl = prNomEmpl;
        this.prApellEmpl = prApellEmpl;
        this.sdApellEmpl = sdApellEmpl;
        this.dirEmpl = dirEmpl;
        this.telEmpl = telEmpl;
        this.correoEmpl = correoEmpl;
        this.fechaNaciEmpl = fechaNaciEmpl;
        this.ciudEmpl = ciudEmpl;
        this.paisEmpl = paisEmpl;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getPrNomEmpl() {
        return prNomEmpl;
    }

    public void setPrNomEmpl(String prNomEmpl) {
        this.prNomEmpl = prNomEmpl;
    }

    public String getSdNomEmpl() {
        return sdNomEmpl;
    }

    public void setSdNomEmpl(String sdNomEmpl) {
        this.sdNomEmpl = sdNomEmpl;
    }

    public String getPrApellEmpl() {
        return prApellEmpl;
    }

    public void setPrApellEmpl(String prApellEmpl) {
        this.prApellEmpl = prApellEmpl;
    }

    public String getSdApellEmpl() {
        return sdApellEmpl;
    }

    public void setSdApellEmpl(String sdApellEmpl) {
        this.sdApellEmpl = sdApellEmpl;
    }

    public String getDirEmpl() {
        return dirEmpl;
    }

    public void setDirEmpl(String dirEmpl) {
        this.dirEmpl = dirEmpl;
    }

    public String getTelEmpl() {
        return telEmpl;
    }

    public void setTelEmpl(String telEmpl) {
        this.telEmpl = telEmpl;
    }

    public String getCorreoEmpl() {
        return correoEmpl;
    }

    public void setCorreoEmpl(String correoEmpl) {
        this.correoEmpl = correoEmpl;
    }

    public Date getFechaNaciEmpl() {
        return fechaNaciEmpl;
    }

    public void setFechaNaciEmpl(Date fechaNaciEmpl) {
        this.fechaNaciEmpl = fechaNaciEmpl;
    }

    public String getCiudEmpl() {
        return ciudEmpl;
    }

    public void setCiudEmpl(String ciudEmpl) {
        this.ciudEmpl = ciudEmpl;
    }

    public String getPaisEmpl() {
        return paisEmpl;
    }

    public void setPaisEmpl(String paisEmpl) {
        this.paisEmpl = paisEmpl;
    }

    public Cargos getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Cargos idCargo) {
        this.idCargo = idCargo;
    }

    public Departamentos getIdDept() {
        return idDept;
    }

    public void setIdDept(Departamentos idDept) {
        this.idDept = idDept;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleado != null ? idEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[" + idEmpleado + "] : " + prNomEmpl + " , " + sdNomEmpl + " , " + prApellEmpl + " , " + sdApellEmpl + " , " + dirEmpl + " , " + telEmpl + " , " + correoEmpl + " , " + fechaNaciEmpl + " , " + ciudEmpl + " , " + paisEmpl + " , " + idCargo + " , " + idDept;
    }

}
