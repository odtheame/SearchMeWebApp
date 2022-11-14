package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Clientes;
import model.Tiendas;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-11-10T19:40:04")
@StaticMetamodel(Recibos.class)
public class Recibos_ { 

    public static volatile SingularAttribute<Recibos, Date> fechaCompra;
    public static volatile SingularAttribute<Recibos, Integer> idRecibo;
    public static volatile SingularAttribute<Recibos, Clientes> idCliente;
    public static volatile SingularAttribute<Recibos, Double> valorTotal;
    public static volatile SingularAttribute<Recibos, Tiendas> idTienda;
    public static volatile SingularAttribute<Recibos, Integer> numRecibo;

}