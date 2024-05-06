/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package models;

import java.time.LocalDateTime;

/**
 *
 * @author Christopher
 */
public record Proveedor(
        int idProveedor,
        String nombre,
        String telefono,
        String correo,
        Estatus estatus,
        LocalDateTime fechaCreacion) {

}
