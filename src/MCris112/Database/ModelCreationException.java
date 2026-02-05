package MCris112.Database;

public class ModelCreationException extends RuntimeException {
    public ModelCreationException() {
        super("No se pudo crear el modelo");
    }
}
