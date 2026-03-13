# QueryMC 

> A modern, elegant, and lightweight Object-Relational Mapper (ORM) for Java. 
> **Created by Cristopher (Darkredgm)**

QueryMC simplifies database interactions by mapping Java classes to database tables and handling the underlying JDBC operations seamlessly. Say goodbye to writing verbose and repetitive SQL statements! 

With a fluent QueryBuilder, automatic schema generation, elegant model relationship parsing, and native Java data-type mappings, QueryMC makes querying databases in Java intuitive and fast.

## High-Level Features

- **Automatic Schema Generation:** Verify and create tables directly from your Model structures.
- **Smart Relationships:** Easily interlock your tables via the nested `@BelongsTo` annotation.
- **Data Coercion:** Handles bridging gaps between JDBC outputs and Java field variables natively behind the scenes.
- **Zero-Boilerplate Inserts:** Use `model.save()` and everything else maps perfectly.

## Examples at a Glance

### Designing a Object Model

```java
import com.darkredgm.querymc.Database.Model;
import com.darkredgm.querymc.Annotations.*;
import java.util.Date;

@Table("users")
public class User extends Model {

    @Primary(autoincrement = true)
    @Column
    private Integer id;

    @Column
    private String name;

    @DBColDefault("CURRENT_DATE()")
    @Column
    private Date joinedAt;
    
    // Getters and Setters...
}
```

### Querying

```java
// Fetch the first matching user
User user = QueryBuilder.use(User.class)
            .where("name", "Cristopher")
            .first();

// Updates
QueryBuilder.use(User.class)
            .where("id", 1)
            .update(builder -> builder.set("name", "Darkredgm"));
```

---

## Full Documentation

QueryMC comes with an extensive wiki to cover installation, connection setups, models, querying, and relationships! 

👉 **[Dive into the Official QueryMC Wiki here!](./WIKI.md)**

---
*Created and Maintained with ❤️ by Cristopher - Darkredgm*
