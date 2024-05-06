/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factories;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import models.Proveedor;

/**
 *
 * @author Christopher
 */
public class ProveedorFactory {

    private final Faker faker;

    public ProveedorFactory() {
        this.faker = new Faker();
    }

    public static ProveedorFactory run() {
        return new ProveedorFactory();
    }

    public Proveedor make() {
        return new Proveedor(
                this.faker.number().numberBetween(9000, 9999),
                this.faker.lorem().word(),
                this.faker.phoneNumber().cellPhone(),
                this.faker.internet().emailAddress(),
                "Activo",
                LocalDate.now()
        );
    }

}
