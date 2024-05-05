/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package models;

/**
 *
 * @author Christopher
 */
public record Pedido(
        int idPedido,
        int cantidad,
        String fechaCreacion,
        String observaciones,
        boolean entregado,
        double totalPago) {

}
