package Demo;

import MCris112.Database.DbColumn;
import MCris112.Database.Model;

public class Jugador extends Model {

    @DbColumn
    protected int codigo;

    @Override
    public String getKeyName() {
        return "codigo";
    }

    @DbColumn
    protected String nombre;

    @DbColumn
    protected String procedencia;

    @DbColumn
    protected String altura;

    @DbColumn
    protected int peso;

    @DbColumn
    protected String posicion;

    @DbColumn("Nombre_equipo")
    protected String nombreEquipo;

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public String getAltura() {
        return altura;
    }

    public int getPeso() {
        return peso;
    }

    public String getPosicion() {
        return posicion;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", procedencia='" + procedencia + '\'' +
                ", altura='" + altura + '\'' +
                ", peso=" + peso +
                ", posicion='" + posicion + '\'' +
                ", nombreEquipo='" + nombreEquipo + '\'' +
                '}';
    }
}
