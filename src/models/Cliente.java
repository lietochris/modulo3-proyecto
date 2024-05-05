/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Christopher
 */
public record Cliente(
        int idCliente,
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        LocalDateTime fechaCreacion,
        LocalDate fechaNacimiento) {

}
