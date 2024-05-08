/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Estatus;
import models.Pedido;
import models.Proveedor;
import utils.Database;
import utils.Result;

/**
 *
 * @author Christopher
 */
public class ProveedorRepository implements Repository<Proveedor> {

    private Database database;

    public ProveedorRepository(Database database) {
        this.database = database;
    }

    @Override
    public Result<Proveedor> create(Proveedor record) {
        try {
            this.database.context().connect();
            var query = "INSERT INTO Proveedor (idProveedor, NombreProveedor, Telefono, FechaCreacion, Correo, Estatus) VALUES (?, ?, ?, ?, ?, ?);";
            var statement = this.database.context().connection().prepareStatement(query);
            statement.setInt(1, record.idProveedor());
            statement.setString(2, record.nombre());
            statement.setString(3, record.telefono());
            statement.setString(4, String.valueOf(record.fechaCreacion()));
            statement.setString(5, record.correo());
            statement.setString(6, String.valueOf(record.estatus()));

            statement.execute();

            this.database.context().disconnect();
            return new Result<>(record);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result<>(utils.Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

    @Override
    public Result<Boolean> delete(int id) {
        try {
            this.database.context().connect();
            var query = "DELETE FROM Proveedor WHERE idProveedor=?";
            var statement = this.database.context().connection().prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();
            this.database.context().disconnect();
            return new Result(true);
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(utils.Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

    @Override
    public Result<Proveedor> update(int id, Proveedor record) {
        try {
            this.database.context().connect();
            var query = "UPDATE Proveedor SET NombreProveedor=?, Telefono=?, FechaCreacion=?, Correo=?, Estatus=? WHERE idProveedor=?;";

            var statement = this.database.context().connection().prepareStatement(query);
            statement.setString(1, record.nombre());
            statement.setString(2, record.telefono());
            statement.setString(3, String.valueOf(record.fechaCreacion()));
            statement.setString(4, record.correo());
            statement.setString(5, String.valueOf(record.estatus()));
            statement.setInt(6, id);

            statement.execute();

            this.database.context().disconnect();
            return new Result(new Proveedor(
                    id,
                    record.nombre(),
                    record.telefono(),
                    record.correo(),
                    record.estatus(),
                    record.fechaCreacion()
            ));
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(utils.Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

    @Override
    public Result<List<Proveedor>> findAll() {
        try {
            this.database.context().connect();

            var query = "SELECT * FROM Proveedor";
            var statement = this.database.context().connection().prepareStatement(query);
            var result = statement.executeQuery();

            var listOfClientes = new ArrayList<Proveedor>();
            while (result.next()) {
                listOfClientes.add(new Proveedor(
                        result.getInt("idProveedor"),
                        result.getString("NombreProveedor"),
                        result.getString("Telefono"),
                        result.getString("Correo"),
                        "Activo".equals(result.getString("Estatus")) ? Estatus.Activo : Estatus.Inactivo,
                        LocalDate.parse(result.getString("FechaCreacion"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                ));

            }
            this.database.context().disconnect();
            return new Result(listOfClientes);
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(utils.Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

    @Override
    public Result<Proveedor> findById(int id) {
        try {
            this.database.context().connect();
            var query = "SELECT * FROM Proveedor WHERE idProveedor=?";
            var statement = this.database.context().connection().prepareStatement(query);
            statement.setInt(1, id);

            var result = statement.executeQuery();

            if (!result.next()) {
                this.database.context().disconnect();
                return new Result(utils.Error.make("NOT_FOUND", "No se encontro el proveedor"));
            }

            var cliente = new Proveedor(
                    result.getInt("idProveedor"),
                    result.getString("NombreProveedor"),
                    result.getString("Telefono"),
                    result.getString("Correo"),
                    "Activo".equals(result.getString("Estatus")) ? Estatus.Activo : Estatus.Inactivo,
                    LocalDate.parse(result.getString("FechaCreacion"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            );

            this.database.context().disconnect();
            return new Result(cliente);
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(utils.Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

}
