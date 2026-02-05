import Demo.Jugador;
import MCris112.Database.QueryBuilder;
import MCris112.Env.Env;
import MCris112.MCList;

import java.sql.SQLException;

public class Main {
    static void main() throws SQLException, IllegalAccessException {
        new Env().print();

//        Jugador nicolas = new Jugador();
//
//        nicolas.fillAttributes( setBuilder -> {
//            setBuilder.set("codigo", 9999)
//                    .set("nombre", "Nicolas")
//                    .set("procedencia", "Perú")
//                    .set("peso", 90)
//                    .set("nombreEquipo", "Lakers")
//                    .set("altura", "5-9");
//        });
//
//        nicolas.save();
//        System.out.println(nicolas);


//        DB.on("jugadores").insert(builder -> builder
//                .set("codigo", 668)
//                .set("nombre", "Cristopher")
//                .set("Nombre_equipo", "Lakers")
//        );

        MCList<Jugador> jugadores = QueryBuilder.use(Jugador.class)
                .where("Nombre_equipo", "Lakers")
                .limit(1)
                .get();
//
//        if (QueryBuilder.use(Jugador.class)
//                .where("Nombre_equipo", "Lakers")
//                .where("codigo", 666)
//                .update(builder -> builder.set("Procedencia", "Spain")
//                ) >= 0 ) {
//            System.out.println("Se actualizo correctamente");
//        }
//
        for (Jugador user : jugadores) {
            user.setAttribute("nombre", "Awawa psdfsdf");
            user.save();
            System.out.println(user);
        }
    }
}