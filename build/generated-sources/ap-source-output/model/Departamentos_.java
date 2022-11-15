package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Bodegas;
import model.Empleados;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-11-14T20:12:41")
@StaticMetamodel(Departamentos.class)
public class Departamentos_ { 

    public static volatile SingularAttribute<Departamentos, String> dirDept;
    public static volatile CollectionAttribute<Departamentos, Empleados> empleadosCollection;
    public static volatile SingularAttribute<Departamentos, String> nomDept;
    public static volatile SingularAttribute<Departamentos, Bodegas> idBodega;
    public static volatile SingularAttribute<Departamentos, Integer> idDept;

}