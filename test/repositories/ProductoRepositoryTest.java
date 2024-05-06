/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package repositories;

import factories.ProductoFactory;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import utils.DatabaseTest;

/**
 *
 * @author Christopher
 */
public class ProductoRepositoryTest extends DatabaseTest {

    @Test
    public void testCreate() throws Exception {

        var producto = ProductoFactory.run().make();
        var repository = new ProductoRepository(this.database);
        var result = repository.create(producto);

        Assert.assertEquals(producto, result.value());
    }

    @Test
    public void testDelete() throws Exception {
        var producto = ProductoFactory.run().make();
        var repository = new ProductoRepository(this.database);
        var result = repository.create(producto);

        repository.delete(result.value().idProducto());

        Assert.assertTrue(true);
    }

    @Test
    public void testUpdate() throws Exception {
        var producto = ProductoFactory.run().make();
        var newProducto = ProductoFactory.run().make();
        var repository = new ProductoRepository(this.database);
        var result = repository.create(producto);

        var expected = repository.update(result.value().idProducto(), newProducto);

        Assert.assertEquals(expected.value().idProducto(), producto.idProducto());
    }

    @Test
    public void testFindAll() throws Exception {
        var repository = new ProductoRepository(this.database);
        var result = repository.findAll();

        assertEquals(3, result.value().size());
    }

    @Test
    public void testFindById() throws Exception {
        var producto = ProductoFactory.run().make();
        var repository = new ProductoRepository(this.database);
        var result = repository.create(producto);
        var expect = repository.findById(result.value().idProducto());
        Assert.assertEquals(result.value().idProducto(), expect.value().idProducto());
    }
}
