package com.darkredgm.querymc.Env;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Env {

    public Map<String,String> raw = new HashMap<String,String>();

    static String ENV_PATH = ".env";

    public EnvDatabase database;

    public Env() {
        try{
            this.load();
        }catch(FileNotFoundException e){
            this.database = new EnvDatabase(
                    "default",
                    "localhost",
                    3306,
                    "root",
                    ""
            );
            System.out.println("\u001B[31mNo se encontró el archivo .env\u001B[0m");
        }
    }

    public String whereAmI()
    {
        return new File(ENV_PATH).getAbsolutePath();
    }
    public void load() throws FileNotFoundException {

        Scanner sc = new Scanner( new FileReader(ENV_PATH) );

        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            // OMITIR COMENTARIOS
            if ( line.startsWith("#") || line.isEmpty() )
                continue;

            String[] data = line.split("=");

            // Para poder manejar valores nulos
            String value = "";
            if (data.length > 1){
                value =  data[1];
            }

            this.raw.put(data[0], value );
        }

        this.database = new EnvDatabase(
                this.raw.getOrDefault("DB_DATABASE", "default"),
                this.raw.getOrDefault("DB_HOST", "localhost"),
                Integer.parseInt(this.raw.getOrDefault("DB_PORT", "3306")),
                this.raw.getOrDefault("DB_USERNAME", "root"),
                this.raw.getOrDefault("DB_PASSWORD", "")
        );
    }

    public void print()
    {
        System.out.println(this.database);
    }

}
