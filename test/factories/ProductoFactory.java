/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factories;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import models.Producto;

/**
 *
 * @author Christopher
 */
public class ProductoFactory {

    private final Faker faker;

    public ProductoFactory() {
        this.faker = new Faker();
    }

    public static ProductoFactory run() {
        return new ProductoFactory();
    }

    public Producto make() {
        return new Producto(
                this.faker.number().numberBetween(9000, 9999),
                this.faker.lorem().word(),
                this.faker.number().numberBetween(0, 10),
                this.faker.number().numberBetween(100, 500),
                this.faker.lorem().paragraph(),
                LocalDate.now()
        );
    }

}
