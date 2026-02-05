package MCris112.Database;

public class IllegalAccessWithoutKey extends RuntimeException {
    public IllegalAccessWithoutKey() {
        super("Cannot use the primary key because has no primary key");
    }
}
