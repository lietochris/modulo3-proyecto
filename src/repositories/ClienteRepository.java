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
import models.Cliente;
import utils.Database;

/**
 *
 * @author Christopher
 */
public class ClienteRepository implements Repository<Cliente> {

    private final Database database;

    public ClienteRepository(Database database) {
        this.database = database;
    }

    @Override
    public Cliente create(Cliente record) throws Exception {
        this.database.context().connect();
        var query = "INSERT INTO Cliente (idCliente, NombreCliente, ApellidoP, ApellidoM, FechaCreacion, FechaNacimiento) VALUES (?, ?, ?, ?, ?, ?)";
        var statement = this.database.context().connection().prepareStatement(query);
        statement.setString(1, String.valueOf(record.idCliente()));
        statement.setString(2, record.nombre());
        statement.setString(3, record.apellidoPaterno());
        statement.setString(4, record.apellidoMaterno());
        statement.setString(5, record.fechaCreacion().toString());
        statement.setString(6, record.fechaNacimiento().toString());

        statement.execute();

        this.database.context().disconnect();
        return record;
    }

    @Override
    public void delete(int id) throws Exception {
        this.database.context().connect();
        var query = "DELETE FROM Cliente WHERE idCliente = ?";
        var statement = this.database.context().connection().prepareStatement(query);
        statement.setString(1, String.valueOf(id));
        statement.execute();
        this.database.context().disconnect();

    }

    @Override
    public Cliente update(int id, Cliente record) throws Exception {
        this.database.context().connect();
        var query = "UPDATE Cliente SET NombreCliente=?, ApellidoP=?, ApellidoM=?, FechaCreacion=?, FechaNacimiento=? WHERE idCliente=?";

        var statement = this.database.context().connection().prepareStatement(query);
        statement.setString(1, record.nombre());
        statement.setString(2, record.apellidoPaterno());
        statement.setString(3, record.apellidoMaterno());
        statement.setString(4, record.fechaCreacion().toString());
        statement.setString(5, record.fechaNacimiento().toString());
        statement.setString(6, String.valueOf(id));

        statement.execute();

        this.database.context().disconnect();
        return new Cliente(
                id,
                record.nombre(),
                record.apellidoPaterno(),
                record.apellidoMaterno(),
                record.fechaCreacion(),
                record.fechaNacimiento()
        );
    }

    @Override
    public List<Cliente> findAll() throws Exception {
        this.database.context().connect();

        var query = "SELECT * FROM Cliente";
        var statement = this.database.context().connection().prepareStatement(query);
        var result = statement.executeQuery();

        var listOfClientes = new ArrayList<Cliente>();
        while (result.next()) {
            listOfClientes.add(new Cliente(
                    result.getInt("idCliente"),
                    result.getString("NombreCliente"),
                    result.getString("ApellidoP"),
                    result.getString("ApellidoM"),
                    LocalDateTime.parse(result.getString("FechaCreacion"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    LocalDate.parse(result.getString("FechaNacimiento"))
            ));

        }
        this.database.context().disconnect();
        return listOfClientes;
    }

    @Override
    public Cliente findById(int id) throws Exception {
        this.database.context().connect();
        var query = "SELECT * FROM Cliente WHERE idCliente=?";
        var statement = this.database.context().connection().prepareStatement(query);
        statement.setString(1, String.valueOf(id));

        var result = statement.executeQuery();

        if (!result.next()) {
            this.database.context().disconnect();
            return null;
        }

        var cliente = new Cliente(
                result.getInt("idCliente"),
                result.getString("NombreCliente"),
                result.getString("ApellidoP"),
                result.getString("ApellidoM"),
                LocalDateTime.parse(result.getString("FechaCreacion"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                LocalDate.parse(result.getString("FechaNacimiento"))
        );

        this.database.context().disconnect();
        return cliente;
    }

}
