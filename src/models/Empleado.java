/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package models;

/**
 *
 * @author Christopher
 */
public record Empleado(
        int idEmpleado,
        String apellidoPaterno,
        String apellidoMaterno,
        String correo,
        String fechaInicio) {

}
