package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Bodegas;
import model.Recibos;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-11-14T20:12:41")
@StaticMetamodel(Tiendas.class)
public class Tiendas_ { 

    public static volatile SingularAttribute<Tiendas, String> dirTienda;
    public static volatile SingularAttribute<Tiendas, Bodegas> idBodega;
    public static volatile SingularAttribute<Tiendas, String> nomTienda;
    public static volatile CollectionAttribute<Tiendas, Recibos> recibosCollection;
    public static volatile SingularAttribute<Tiendas, Integer> idTienda;
    public static volatile SingularAttribute<Tiendas, String> telTienda;

}