/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factories;

import com.github.javafaker.Faker;

import java.time.LocalDateTime;

import models.Pedido;

/**
 * @author Christopher
 */
public class PedidoFactory {

    private final Faker faker;

    public PedidoFactory() {
        this.faker = new Faker();
    }

    public static PedidoFactory run() {
        return new PedidoFactory();
    }

    public Pedido make() {
        return new Pedido(
                this.faker.number().numberBetween(9000, 9999),
                this.faker.number().numberBetween(0, 10),
                this.faker.lorem().word(),
                this.faker.bool().bool(),
                this.faker.number().numberBetween(9000, 9999),
                LocalDateTime.now()
        );
    }

}
