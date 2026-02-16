package com.darkredgm.demo;

import com.darkredgm.demo.Models.*;
import com.darkredgm.querymc.Database.DB;
import com.darkredgm.querymc.Database.Model;
import com.darkredgm.querymc.Database.ModelAttribute;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Scanner;

public class Demo {

    static void main() throws SQLException {
//        DB.deleteDatabase("cristopher_matriculas");
//        DB.createDatabaseIfNotExists("cristopher_matriculas");
//
//        DB.verify(Alumno.class, Asignatura.class, CursoEscolar.class, Profesor.class, Matricula.class);
//

        fillModel(Alumno.class);
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

}
