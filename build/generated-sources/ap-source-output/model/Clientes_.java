package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Recibos;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-11-26T02:14:43")
@StaticMetamodel(Clientes.class)
public class Clientes_ { 

    public static volatile SingularAttribute<Clientes, String> prNomClien;
    public static volatile SingularAttribute<Clientes, String> dirClien;
    public static volatile SingularAttribute<Clientes, Integer> idCliente;
    public static volatile SingularAttribute<Clientes, String> sdNomClien;
    public static volatile SingularAttribute<Clientes, String> telClien;
    public static volatile SingularAttribute<Clientes, String> prApellClien;
    public static volatile SingularAttribute<Clientes, String> correoClien;
    public static volatile SingularAttribute<Clientes, String> sdApellClien;
    public static volatile CollectionAttribute<Clientes, Recibos> recibosCollection;

}