/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package models;

/**
 *
 * @author Christopher
 */
public record Producto(
        int idProducto,
        String nombre,
        int stock,
        double precio,
        String descripcion,
        String fechaCreacion) {

}
