package MCris112.Helpers;

public class Str {

    public static String toCamelCase(String columnName) {
        String[] parts = columnName.toLowerCase().split("_");
        StringBuilder camel = new StringBuilder(parts[0]);  // First lowercase

        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            if (!part.isEmpty()) {
                camel.append(part.substring(0, 1).toUpperCase())
                        .append(part.substring(1));
            }
        }
        return camel.toString();
    }

    public static String toSnakeCase(String camelCase) {
        StringBuilder snake = new StringBuilder(camelCase.length() + 10);

        for (int i = 0; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) snake.append('_');  // _ before Upper (except first)
                snake.append(Character.toLowerCase(c));
            } else {
                snake.append(c);
            }
        }
        return snake.toString();
    }
}
