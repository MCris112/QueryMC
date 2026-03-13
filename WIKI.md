# QueryMC – Official Wiki 

**An Elegant SQL-ORM written by Cristopher (Darkredgm).** 

QueryMC leverages Java Reflection to elegantly parse Java Models into strict SQL queries and vice-versa, saving you an overwhelming amount of boilerplate code.

Below, you'll learn how to establish databases, build models, insert variables natively via mapping annotations, and run SQL transactions safely through the ORM object layer.

---

### Table of Contents

1. [Configuration](#1-configuration-and-connection)
2. [Building Your First Model](#2-building-your-first-model)
3. [Creating Tables Intentionally (Schema Tooling)](#3-schema-tooling-and-dbverify)
4. [Using Data Modifiers (BelongsTo Native Conversion)](#4-using-datamodifiers)
5. [QueryBuilder Methods (First, Where, Selects)](#5-querybuilder-getting-fetching-matching)
6. [Inserting & Deleting Instances](#6-inserting-deleting-a-model-instance)
7. [Updates & Fast Builders](#7-updates)

---

## 1. Configuration and Connection

To get the ORM up and running, you first configure its central connection using the basic `DatabaseEnv` protocol or standard credentials to allow `Model` elements to instantiate accurately via JDBC reflection logic.

```java
import com.darkredgm.querymc.QueryMC;

// Connect internally to your database properties
QueryMC.setConnectionWithoutDB(new MCConnection(
    "databaseName", 
    "tableName", 
    port, 
    "username", 
    "password"
));
```

## 2. Building Your First Model

To interact with a database schema, extend your class with the central `Model`. Your class will parse any parameter carrying `@Column` straight into the generated MySQL table.

```java
package com.darkredgm.demo.Models;

import com.darkredgm.querymc.Annotations.*;
import com.darkredgm.querymc.Database.Model;

import java.util.Date;

@Table("libros")
public class Libro extends Model {

    @Primary(autoincrement = true)
    @Column(nullable = false)
    private Integer id_libro;

    @Column
    private String titulo;

    @Column
    private double precio;

    @DBColDefault("CURRENT_DATE()") 
    @Column
    private Date createdAt;

    // Be sure to include getters/setters!
    // They are mandatory to properly handle property scoping parameters.
}
```

#### Important Annotations:

- `@Table("TableName")` designates explicit target labels. Without it, the parser will guess based on your Class.
- `@Primary(autoincrement = true)` Flags this field natively in database builders to assign standard `PRIMARY KEY(field_name)` bindings.
- `@DBColDefault(value)` Appends SQL Expressions defaults, allowing properties like `"CURRENT_TIMESTAMP"` natively!

## 3. Schema Tooling and `DB.verify()`

The easiest way to initialize all the fields inside a system and mirror SQL states securely inside QueryMC is by running the `verify` command once.

```java
import com.darkredgm.querymc.Database.DB;

// If "libros" does not exist in the DB, it creates all appropriate 
// properties INT, DOUBLE(10,2), DATE DEFAULT CURRENT_DATE(), VARCHAR(255) 
// natively bridging out of the class definitions.
DB.verify(Libro.class);
```

Underneath the hood, QueryMC maps `short`, `byte`, `Integer`, `String`, `Date`, `java.sql.Time`, `BigDecimal`, and `BigInteger` properties intrinsically across table parsers to exact optimal column limits!

## 4. Using DataModifiers

One of QueryMC’s biggest features is its explicit object mapping annotations. Native classes mapped together with the `@BelongsTo` annotation function to serialize objects explicitly onto outer table properties without rewriting classes.

```java
    @BelongsTo(model = Usuario.class)
    @Column
    private Usuario usuario;
```

This dynamically checks the ID assigned across a targeted object, loads foreign keys, constructs `user_id` variables dynamically inside arrays, and properly populates and cascades during queries bridging!

## 5. QueryBuilder: Getting, Fetching, Matching

The `QueryBuilder` executes dynamic filtering onto your queries to bind values directly across parameters explicitly preventing SQL Injection under the hood via standard PreparedStatement assignments.

```java
import com.darkredgm.querymc.Database.ORM.QueryBuilder;
import com.darkredgm.querymc.Collections.MCList;

public class Main {
    public static void main(String[] args) throws Exception {

        // Grabs an overarching list of models bound out of the Database!
        MCList<Libro> todosLibros = QueryBuilder.use(Libro.class).get();

        // Fetches a singular book (first matched record) using standard constraints 
        Libro target = QueryBuilder.use(Libro.class)
                            .where("titulo", "El Alquimista")
                            .first();

        // You can layer multi checks and get specific primary target keys
        Libro recent = QueryBuilder.use(Libro.class)
                            .whereKey(5)
                            .first();
    }
}
```

## 6. Inserting & Deleting A Model Instance

When you assign values onto an internal Model explicitly (using setters or constructors), the `Model.save()` interface intuitively resolves whether the file was grabbed out of a `QueryBuilder` or generated independently on your end.
If new, it calls `.insertGetId()` and attaches IDs back intelligently to your PKs!. 
If editing, it executes `UPDATE`.

### Inserting a Field
```java
Libro nuevoLibro = new Libro();
nuevoLibro.setTitulo("Percy Jackson");
nuevoLibro.setPrecio(14.99);
nuevoLibro.setBestseller(true);

// Because ID isn't set natively yet, this natively issues an INSERT!
// Since `createdAt` is null, the query intelligently omits it allowing DB defaults!
nuevoLibro.save(); 

System.out.println("New ID is " + nuevoLibro.getId()); 
```

### Deleting
```java
// Grabs ID 5 natively and runs `DELETE` on the bound structure
QueryBuilder.use(Libro.class).whereKey(5).first().delete(); 
```

## 7. Updates 

For explicit fast updates against target constraints instead of grabbing full model datasets, you leverage inline Lambda constraints on the QueryBuilder! 

```java
// Updates all "Accion" books turning the Bestseller target properties on automatically! 
QueryBuilder.use(Libro.class)
            .where("genero", "Accion")
            .update(builder -> {
                builder.set("bestseller", true);
            });
```

---
*Created and Architected by Cristopher - Darkredgm.*
