/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factories;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.LocalDateTime;
import models.Cliente;

/**
 *
 * @author Christopher
 */
public class ClienteFactory {

    private final Faker faker;

    public ClienteFactory() {
        this.faker = new Faker();
    }

    public static ClienteFactory run() {
        return new ClienteFactory();
    }

    public Cliente make() {

        return new Cliente(
                this.faker.number().numberBetween(9000, 9999),
                this.faker.name().firstName(),
                this.faker.name().lastName(),
                this.faker.name().lastName(),
                LocalDateTime.now(),
                LocalDate.now()
        );

    }

}
