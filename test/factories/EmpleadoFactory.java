/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factories;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import models.Empleado;

/**
 *
 * @author Christopher
 */
public class EmpleadoFactory {

    private final Faker faker;

    public EmpleadoFactory() {
        this.faker = new Faker();
    }

    public static EmpleadoFactory run() {
        return new EmpleadoFactory();
    }

    public Empleado make() {

        return new Empleado(
                this.faker.number().numberBetween(9000, 9999),
                this.faker.name().firstName(),
                this.faker.name().lastName(),
                this.faker.name().lastName(),
                this.faker.internet().emailAddress(),
                LocalDate.now()
        );

    }

}
