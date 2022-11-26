package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cargos;
import model.Departamentos;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-11-26T02:14:43")
@StaticMetamodel(Empleados.class)
public class Empleados_ { 

    public static volatile SingularAttribute<Empleados, String> telEmpl;
    public static volatile SingularAttribute<Empleados, String> prApellEmpl;
    public static volatile SingularAttribute<Empleados, String> prNomEmpl;
    public static volatile SingularAttribute<Empleados, Date> fechaNaciEmpl;
    public static volatile SingularAttribute<Empleados, String> sdApellEmpl;
    public static volatile SingularAttribute<Empleados, Departamentos> idDept;
    public static volatile SingularAttribute<Empleados, String> correoEmpl;
    public static volatile SingularAttribute<Empleados, Cargos> idCargo;
    public static volatile SingularAttribute<Empleados, String> dirEmpl;
    public static volatile SingularAttribute<Empleados, String> ciudEmpl;
    public static volatile SingularAttribute<Empleados, Integer> idEmpleado;
    public static volatile SingularAttribute<Empleados, String> sdNomEmpl;
    public static volatile SingularAttribute<Empleados, String> paisEmpl;

}