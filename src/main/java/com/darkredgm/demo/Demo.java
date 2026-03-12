package com.darkredgm.demo;

import com.darkredgm.User;
import com.darkredgm.demo.Models.*;
import com.darkredgm.querymc.Collections.MCList;
import com.darkredgm.querymc.Database.DB;
import com.darkredgm.querymc.Database.Model;
import com.darkredgm.querymc.Database.ModelAttribute;
import com.darkredgm.querymc.Database.ORM.QueryBuilder;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Scanner;

public class Demo {

    static void main() throws SQLException {
        DB.deleteDatabase("cristopher_matriculas");
        DB.createDatabaseIfNotExists("cristopher_matriculas");

        DB.verify(Alumno.class, CursoEscolar.class, Profesor.class,Asignatura.class,  Matricula.class);
//

//        MCList<Asignatura> list = QueryBuilder.use(Asignatura.class).get();


//        System.out.println(list.getFirst().getKeyName());

//        QueryBuilder<User> query = QueryBuilder.use(User.class).distinct();
//
//        System.out.println( query );
//
//        System.out.println(
//                query.where("email", ">", "100").where("puntos", "19")
//        );
////        fillModel(Alumno.class);
    }


    public static  <M extends Model> void fillModel(Class<M> modelClass )
    {
        Scanner scanner = new Scanner( System.in );

        try{
            M model = (M)  modelClass.getConstructor().newInstance();

            for ( ModelAttribute attribute: model.getFieldAttributes() )
            {
                System.out.println("Inserte el valor ["+attribute.getName()+"]:");

                String line = scanner.nextLine();

                System.out.println(line);
                System.out.println(line.length());

                if ( !line.isEmpty() )
                    model.setAttribute( attribute.getName(), line );
            }

            model.save();

            System.out.println("Se guardo correctamente");
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | InstantiationException e) {
            System.out.println("ERROR DE CREACION DE MODELO");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("SQL ERROR: "+ e.getMessage() );
            throw new RuntimeException(e);
        }
    }


//
//    static DawDB dawDB = new DawDB();
//
//    static void main() {
//
//        // crear las tablas
//        createTables();
//
//
//    }
//
//    /**
//     * Toda la migración de crear tablas
//     */
//    public static void createTables()
//    {
//        try{
//            dawDB.schema.dropIfExists("proveedores");
//            dawDB.schema.createIfNotExists("proveedores", table -> {
//                table.intCol("codigo").setPrimaryKey(true).autoIncrement();
//                table.varchar("direccion", 100);
//                table.varchar("ciudad", 100);
//                table.varchar("provincia", 100);
//            });
//
//            dawDB.schema.dropIfExists("categorias");
//            dawDB.schema.createIfNotExists("categorias", table -> {
//                table.intCol("codigo").setPrimaryKey(true).autoIncrement();
//                table.varchar("direccion", 100);
//            });
//
//            dawDB.schema.dropIfExists("piezas");
//            dawDB.schema.createIfNotExists("piezas", table -> {
//                table.intCol("codigo").setPrimaryKey(true).autoIncrement();
//                table.varchar("nombre", 100);
//                table.varchar("color", 100);
//                table.decimal("precio", 10,2);
//                table.intCol("codigo_categoria");
//                table.foreignKey("codigo_categoria").references("caregorias").on("codigo");
//            });
//
//            dawDB.schema.dropIfExists("suministros");
//            dawDB.schema.createIfNotExists("suministros", table -> {
//                table.intCol("codigo_proovedor").setPrimaryKey(true);
//                table.intCol("codigo_pieza").setPrimaryKey(true);
//                table.datetime("fecha_hora").setPrimaryKey(true);
//                table.intCol("cantidad");
//
//                table.foreignKey("codigo_proovedor").references("proveedores").on("codigo");
//                table.foreignKey("codigo_pieza").references("piezas").on("codigo");
//            });
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
