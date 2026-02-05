package MCris112.Database;

public interface DatabaseEnv {

    public default String getDatabaseName()
    {
        return null;
    }

    public default String getDatabasePort()
    {
        return null;
    }

    public default String getDatabaseUserName()
    {
        return null;
    }

    public default String getDatabaseUserPassword()
    {
        return null;
    }

    public String getTableName();

}
