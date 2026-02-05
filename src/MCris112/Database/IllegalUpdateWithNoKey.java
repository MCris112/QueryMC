package MCris112.Database;

public class IllegalUpdateWithNoKey extends RuntimeException {
    public IllegalUpdateWithNoKey() {
        super("Cannot update model without primary key");
    }
}
