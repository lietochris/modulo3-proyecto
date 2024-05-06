/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package utils;

/**
 *
 * @author Christopher
 */
public record Error(
        String code,
        String message) {

    public static Error make(String code, String message) {
        return new Error(code, message);
    }
}
