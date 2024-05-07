/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package repositories;

import factories.PedidoFactory;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import utils.DatabaseTest;

/**
 *
 * @author Christopher
 */
public class PedidoRepositoryTest extends DatabaseTest {

    @Test
    public void testCreate() throws Exception {

        var pedido = PedidoFactory.run().make();
        var repository = new PedidoRepository(this.database);
        var result = repository.create(pedido);

        Assert.assertEquals(pedido, result.value());
    }

    @Test
    public void testDelete() throws Exception {
        var pedido = PedidoFactory.run().make();
        var repository = new PedidoRepository(this.database);
        var result = repository.create(pedido);

        repository.delete(result.value().idPedido());

        Assert.assertTrue(true);
    }

    @Test
    public void testUpdate() throws Exception {
        var pedido = PedidoFactory.run().make();
        var newPedido = PedidoFactory.run().make();
        var repository = new PedidoRepository(this.database);
        var result = repository.create(pedido);

        var expected = repository.update(result.value().idPedido(), newPedido);

        Assert.assertEquals(expected.value().idPedido(), pedido.idPedido());
    }

    @Test
    public void testFindAll() throws Exception {
        var repository = new PedidoRepository(this.database);
        var result = repository.findAll();
        assertTrue(!result.value().isEmpty());
    }

    @Test
    public void testFindById() throws Exception {
        var pedido = PedidoFactory.run().make();
        var repository = new PedidoRepository(this.database);
        var result = repository.create(pedido);
        var expect = repository.findById(result.value().idPedido());
        Assert.assertEquals(result.value().idPedido(), expect.value().idPedido());
    }
}
