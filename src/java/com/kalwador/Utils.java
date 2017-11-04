package com.kalwador;

/**
 *
 * @author Kalwador
 */
public class Utils {

    /**
     * URL transmission of files paths needs a special treatment,
     * There can not be a spacebar or backslesh characters.
     * All of them are replaced by '!=!' character of slash.
     * @param string
     * @return 
     */
    public static String replaceOut(String string) {
        return string.replaceAll(" ", "!=!").replaceAll("\\\\", "/");
    }

    public static String replaceIn(String string) {
        return string.replaceAll("!=!", " ").replaceAll("/", "\\\\");
    }
}
