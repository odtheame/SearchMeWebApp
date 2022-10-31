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
@Table(name = "recibos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recibos.findAll", query = "SELECT r FROM Recibos r"),
    @NamedQuery(name = "Recibos.findByIdRecibo", query = "SELECT r FROM Recibos r WHERE r.idRecibo = :idRecibo"),
    @NamedQuery(name = "Recibos.findByNumRecibo", query = "SELECT r FROM Recibos r WHERE r.numRecibo = :numRecibo"),
    @NamedQuery(name = "Recibos.findByFechaCompra", query = "SELECT r FROM Recibos r WHERE r.fechaCompra = :fechaCompra"),
    @NamedQuery(name = "Recibos.findByValorTotal", query = "SELECT r FROM Recibos r WHERE r.valorTotal = :valorTotal")})
public class Recibos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRecibo")
    private Integer idRecibo;
    @Basic(optional = false)
    @Column(name = "num_Recibo")
    private int numRecibo;
    @Basic(optional = false)
    @Column(name = "fecha_Compra")
    @Temporal(TemporalType.DATE)
    private Date fechaCompra;
    @Basic(optional = false)
    @Column(name = "valor_Total")
    private double valorTotal;
    @JoinColumn(name = "idTienda", referencedColumnName = "idTienda")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tiendas idTienda;
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Clientes idCliente;

    public Recibos() {
    }

    public Recibos(Integer idRecibo) {
        this.idRecibo = idRecibo;
    }

    public Recibos(Integer idRecibo, int numRecibo, Date fechaCompra, double valorTotal) {
        this.idRecibo = idRecibo;
        this.numRecibo = numRecibo;
        this.fechaCompra = fechaCompra;
        this.valorTotal = valorTotal;
    }

    public Integer getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(Integer idRecibo) {
        this.idRecibo = idRecibo;
    }

    public int getNumRecibo() {
        return numRecibo;
    }

    public void setNumRecibo(int numRecibo) {
        this.numRecibo = numRecibo;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Tiendas getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(Tiendas idTienda) {
        this.idTienda = idTienda;
    }

    public Clientes getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Clientes idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRecibo != null ? idRecibo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recibos)) {
            return false;
        }
        Recibos other = (Recibos) object;
        if ((this.idRecibo == null && other.idRecibo != null) || (this.idRecibo != null && !this.idRecibo.equals(other.idRecibo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "["+idRecibo+"] : "+numRecibo+" , "+fechaCompra+" , "+valorTotal+" , "+idTienda+" , "+idCliente;
    }
    
}
