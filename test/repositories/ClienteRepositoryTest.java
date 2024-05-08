/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package repositories;

import factories.ClienteFactory;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import utils.DatabaseTest;

/**
 *
 * @author Christopher
 */
public class ClienteRepositoryTest extends DatabaseTest {

    @Test
    public void testCreate() throws Exception {

        var cliente = ClienteFactory.run().make();
        var repository = new ClienteRepository(this.database);
        var result = repository.create(cliente);

        Assert.assertEquals(cliente, result.value());
    }

    @Test
    public void testDelete() throws Exception {
        var cliente = ClienteFactory.run().make();
        var repository = new ClienteRepository(this.database);
        var result = repository.create(cliente);

        repository.delete(result.value().idCliente());

        Assert.assertTrue(true);
    }

    @Test
    public void testUpdate() throws Exception {
        var cliente = ClienteFactory.run().make();
        var newCliente = ClienteFactory.run().make();
        var repository = new ClienteRepository(this.database);
        var result = repository.create(cliente);

        var expected = repository.update(result.value().idCliente(), newCliente);

        Assert.assertEquals(expected.value().idCliente(), cliente.idCliente());
    }

    @Test
    public void testFindAll() throws Exception {
        var repository = new ClienteRepository(this.database);
        var result = repository.findAll();

        assertEquals(3, result.value().size());
    }

    @Test
    public void testFindById() throws Exception {
        var cliente = ClienteFactory.run().make();
        var repository = new ClienteRepository(this.database);
        var result = repository.create(cliente);
        var expect = repository.findById(result.value().idCliente());
        Assert.assertEquals(result.value().idCliente(), expect.value().idCliente());
    }
}
