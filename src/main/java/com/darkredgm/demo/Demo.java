package com.darkredgm.demo;

import com.darkredgm.demo.Models.*;
import com.darkredgm.querymc.Collections.MCList;
import com.darkredgm.querymc.Database.DB;
import com.darkredgm.querymc.Database.Model;
import com.darkredgm.querymc.Database.ModelAttribute;
import com.darkredgm.querymc.Database.ORM.QueryBuilder;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Demo {

    static void main() throws SQLException {
        DB.deleteDatabase("test_database_test");
        DB.createDatabaseIfNotExists("test_database_test");

        DB.verify(Libro.class, Usuario.class, Prestamo.class);

// --- 1. Usuarios ---
        Usuario u1 = new Usuario("12345678A", "Cristopher", "Rodriguez", "Calle Mayor 1", "600111222");
        u1.save();
//        Usuario u2 = new Usuario("87654321B", "Antonio", "García", "Avenida Libertad 5", "600333444");
//        u2.save();
//        Usuario u3 = new Usuario("11223344C", "Admin", "General", "Biblioteca Central", "900000000");
//        u3.save();

        // --- 2. Libros (con URLs de portadas reales) ---
        Libro l1 = new Libro(null, "Planeta", 400, "Don Quijote de la Mancha", "Miguel de Cervantes", "Aventura", 25, true,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/08/Don_Quijote_1.jpg/220px-Don_Quijote_1.jpg");
        l1.save();

        Libro lb = QueryBuilder.use(Libro.class).first();

        System.out.println(lb.getKeyValue());
        System.out.println(lb);
        System.out.println(lb.getPrecio());
//
//        Libro l2 = new Libro(null, "Salamandra", 300, "Harry Potter y la Piedra Filosofal", "J.K. Rowling", "Fantasía", 22, true,
//                "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1546910265i/3.jpg");
//        l2.save();
//
//        Libro l3 = new Libro(null, "Debolsillo", 1200, "El Señor de los Anillos", "J.R.R. Tolkien", "Fantasía", 45, true,
//                "https://m.media-amazon.com/images/I/91b0C2YNSrL._AC_UF1000,1000_QL80_.jpg");
//        l3.save();
//
//        Libro l4 = new Libro(null, "Alfaguara", 500, "Cien Años de Soledad", "Gabriel García Márquez", "Realismo Mágico", 18, true,
//                "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1327813613i/15544.jpg");
//        l4.save();
//
//
//        // --- 3. Préstamos Iniciales ---
//        // Prestamo(Integer id, int id_libro, String nif, String fecha_inicio, String fecha_fin, boolean devuelto)
        Prestamo p1 = new Prestamo(null, l1.getId_libro(), "12345678A", "01/03/2026", "31/03/2026", false);
        p1.save();
//
//
//        Prestamo p2 = new Prestamo(null, l2.getId_libro(), "87654321B", "10/03/2026", "10/04/2026", false);
//        p2.save();

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
