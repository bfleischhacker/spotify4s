package org.spotify4j.generation;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {
    public static String snakeToCamelCase(String string) {
        String buf = "";
        for (String str : string.split("_")) {
            if (str.length() > 0) {
                buf += Character.toUpperCase(str.charAt(0));
                buf += str.substring(1);
            }
        }
        return buf;
    }

    public static String snakeToLowerCamelCase(String string) {
        final String result = snakeToCamelCase(string);
        if (!result.isEmpty()) {
            return result.substring(0, 1).toLowerCase() + result.substring(1);
        }
        return result;
    }

    public static String camelCaseToSnake(String string) {
        return Stream.of(string.replaceAll("([A-Z])", "_$1").split("_")).map(str ->
                str.length() > 1 ? str.substring(0, 1).toLowerCase() + str.substring(1) : str.toLowerCase()
        ).collect(Collectors.joining("_"));
    }
}
