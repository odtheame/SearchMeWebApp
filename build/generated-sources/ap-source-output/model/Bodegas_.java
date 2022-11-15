package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Departamentos;
import model.Tiendas;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-11-14T20:12:41")
@StaticMetamodel(Bodegas.class)
public class Bodegas_ { 

    public static volatile CollectionAttribute<Bodegas, Departamentos> departamentosCollection;
    public static volatile SingularAttribute<Bodegas, Integer> idBodega;
    public static volatile SingularAttribute<Bodegas, String> telBodega;
    public static volatile SingularAttribute<Bodegas, String> nomBodega;
    public static volatile CollectionAttribute<Bodegas, Tiendas> tiendasCollection;
    public static volatile SingularAttribute<Bodegas, String> dirBodega;

}