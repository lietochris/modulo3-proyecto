/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package repositories;

import factories.ProveedorFactory;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import utils.DatabaseTest;

/**
 *
 * @author Christopher
 */
public class ProveedorRepositoryTest extends DatabaseTest {

    @Test
    public void testCreate() throws Exception {

        var proveedor = ProveedorFactory.run().make();
        var repository = new ProveedorRepository(this.database);
        var result = repository.create(proveedor);

        Assert.assertEquals(proveedor, result.value());
    }

    @Test
    public void testDelete() throws Exception {
        var proveedor = ProveedorFactory.run().make();
        var repository = new ProveedorRepository(this.database);
        var result = repository.create(proveedor);

        repository.delete(result.value().idProveedor());

        Assert.assertTrue(true);
    }

    @Test
    public void testUpdate() throws Exception {
        var proveedor = ProveedorFactory.run().make();
        var newProveedor = ProveedorFactory.run().make();
        var repository = new ProveedorRepository(this.database);
        var result = repository.create(proveedor);

        var expected = repository.update(result.value().idProveedor(), newProveedor);

        Assert.assertEquals(expected.value().idProveedor(), proveedor.idProveedor());
    }

    @Test
    public void testFindAll() throws Exception {
        var repository = new ProveedorRepository(this.database);
        var result = repository.findAll();

        assertEquals(3, result.value().size());
    }

    @Test
    public void testFindById() throws Exception {
        var proveedor = ProveedorFactory.run().make();
        var repository = new ProveedorRepository(this.database);
        var result = repository.create(proveedor);
        var expect = repository.findById(result.value().idProveedor());
        Assert.assertEquals(result.value().idProveedor(), expect.value().idProveedor());
    }
}
