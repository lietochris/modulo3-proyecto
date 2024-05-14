/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 *
 * @author Christopher
 */
public class Validator {

    public static void isNotEmpty(String text, String label) throws Exception {
        if (text.isBlank() || text.isEmpty()) {
            throw new Exception("El valor de " + label + " esta vacio");
        }
    }

    public static void IsNumeric(String text, String label) throws Exception {
        for (char c : text.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new Exception("El valor de " + label + " no es numerico");
            }
        }
    }

    public static void IsEmail(String text, String label) throws Exception {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        var pattern = Pattern.compile(regex);

        if (!pattern.matcher(text).matches()) {
            throw new Exception("El valor de " + label + " no es un email valido");
        }
    }

    public static void IsDate(String text, String label) throws Exception {
        var format = "yyyy-MM-dd";
        var dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(text);
        } catch (ParseException e) {
            throw new Exception("El valor de " + label + " no es una fecha valida, debe tener el siguiente formato: " + format);
        }

    }
}
