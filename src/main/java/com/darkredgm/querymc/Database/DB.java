package com.darkredgm.querymc.Database;

import com.darkredgm.querymc.Annotations.BelongsTo;
import com.darkredgm.querymc.Annotations.DBForeign;
import com.darkredgm.querymc.Conecction.BaseConnection;
import com.darkredgm.querymc.Database.Schema.Schema;
import com.darkredgm.querymc.QueryMC;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DB {

    public static ResultSet executeSelect(String sql) throws SQLException {
        DB db = new DB();

        BaseConnection conn = db.getConnection();

        Statement stmt = conn.asSqlConnection().createStatement();

        return stmt.executeQuery(sql);
    }

    public static void statement(String sql) throws SQLException {
        DB db = new DB();

        BaseConnection conn = db.getConnection();

        Statement stmt = conn.asSqlConnection().createStatement();

        stmt.executeUpdate(sql);
    }

    public static void createDatabaseIfNotExists(String name) throws SQLException {
        statement("CREATE DATABASE IF NOT EXISTS " + validateName(name));
    }

    /**
     * This can throw error cuz needs to be configured
     * "AllowUserDropDatabase = true"
     *
     * @param name
     * @throws SQLException
     */
    public static void deleteDatabaseIfNotExists(String name) throws SQLException {
        statement("DROP DATABASE IF NOT EXISTS " + validateName(name));
    }

    public static void createDatabase(String name) throws SQLException {
        statement("CREATE DATABASE " + validateName(name));
    }

    public static void deleteDatabase(String name) throws SQLException {
        statement("DROP DATABASE " + validateName(name));
    }

    public BaseConnection getConnection() {
        return QueryMC.getConnectionWithoutDB();
    }

    private static String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Database name required");
        }
        // Basic sanitization (more robust than raw concat)
        if (!name.matches("[a-zA-Z0-9_-]+")) {
            throw new IllegalArgumentException("Invalid DB name: " + name);
        }

        return "`" + name.replace("`", "``") + "`";
    }

    public static void verify(Class<? extends Model>... models) throws SQLException {
        for (Class<? extends Model> claszz : models) {
            try {
                Model model = (Model) claszz.getConstructor().newInstance();

                List<ModelAttribute> attributes = model.getFieldAttributes();

                Schema schema = new Schema(model.getConnection());

                schema.createIfNotExists(model.getTableName(), table -> {
                    for (ModelAttribute attribute : attributes) {

                        System.out.println(attribute.getName());
                        if (attribute.asField().isAnnotationPresent(BelongsTo.class)) {
                            BelongsTo belongsTo = attribute.asField().getAnnotation(BelongsTo.class);
                            try {
                                Model modelFK = belongsTo.model().getConstructor().newInstance();
                                ModelAttribute fk = modelFK.getFieldPrimaryKey();
                                table.foreignKey(attribute.getColumnName()).references(modelFK.getKeyName())
                                        .on(modelFK.getTableName());

                                String simpleName = fk.asField().getType().getSimpleName();
                                // Is a belong to as a primary key
                                if (Model.class.isAssignableFrom(fk.asField().getType())) {
                                    Model belongsToFK = (Model) fk.asField().getType().getConstructor().newInstance();
                                    simpleName = belongsToFK.getFieldPrimaryKey().asField().getType().getSimpleName();
                                }
                                System.out.println("[DEBUG] colname: %s -> [%s] -> field nme: %s".formatted(
                                        attribute.columnName,
                                        simpleName,
                                        fk.getName()));
                                switch (simpleName)

                                {
                                    case "int", "Integer":
                                        table.intCol(attribute.getColumnName());
                                        break;
                                    case "BigInteger", "long", "Long":
                                        table.bigInt(attribute.getColumnName());
                                        break;
                                    default:
                                        table.varchar(attribute.getColumnName(), 255);
                                }
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            } catch (InstantiationException e) {
                                throw new RuntimeException(e);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            table.addColumn(attribute.getColumnName(), attribute.asField());
                        }

                    }
                });

            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
