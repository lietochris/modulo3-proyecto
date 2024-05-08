/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package repositories;

import factories.EmpleadoFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.DatabaseTest;

/**
 *
 * @author Christopher
 */
public class EmpleadoRepositoryTest extends DatabaseTest {

    @Test
    public void testCreate() {
        var record = EmpleadoFactory.run().make();
        var repository = new EmpleadoRepository(this.database);
        var result = repository.create(record);
        assertEquals(record, result.value());
    }

    @Test
    public void testDelete() {
        var record = EmpleadoFactory.run().make();
        var repository = new EmpleadoRepository(this.database);
        repository.create(record);
        var result = repository.delete(record.idEmpleado());
        assertTrue(result.value());
    }

    @Test
    public void testUpdate() {
        var record = EmpleadoFactory.run().make();
        var repository = new EmpleadoRepository(this.database);
        repository.create(record);
        var newEmpleado = EmpleadoFactory.run().make();
        var result = repository.update(record.idEmpleado(), newEmpleado);
        assertEquals(result.value().idEmpleado(), record.idEmpleado());
    }

    @Test
    public void testFindAll() {
        var repository = new EmpleadoRepository(this.database);
        var result = repository.findAll();
        assertTrue(!result.value().isEmpty());
    }

    @Test
    public void testFindById() {
        var record = EmpleadoFactory.run().make();
        var repository = new EmpleadoRepository(this.database);
        repository.create(record);
        var result = repository.findById(record.idEmpleado());
        assertEquals(result.value().idEmpleado(), record.idEmpleado());
    }

}
