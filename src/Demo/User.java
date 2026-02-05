package Demo;

import MCris112.Database.DbColumn;
import MCris112.Database.Model;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

public class User extends Model {

    protected BigInteger id;

    @DbColumn("name")
    protected String name;

    protected String lastname;

    protected String email;

//    protected Date createdAt;
    protected Timestamp createdAt;

    protected int personaId;

    public BigInteger getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public int getPersonaId() {
        return personaId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nombre='" + name + '\'' +
                ", latname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", personaId=" + personaId +
                '}';
    }
}
