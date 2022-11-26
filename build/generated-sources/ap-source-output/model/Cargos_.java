package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Empleados;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-11-26T02:14:43")
@StaticMetamodel(Cargos.class)
public class Cargos_ { 

    public static volatile SingularAttribute<Cargos, Integer> idCargo;
    public static volatile SingularAttribute<Cargos, String> nomCargo;
    public static volatile CollectionAttribute<Cargos, Empleados> empleadosCollection;
    public static volatile SingularAttribute<Cargos, String> descCargo;
    public static volatile SingularAttribute<Cargos, Double> salarioCargo;

}