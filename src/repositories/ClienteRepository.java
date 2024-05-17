/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Cliente;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import utils.Database;
import utils.Result;
import utils.Error;

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
    public Result<Cliente> create(Cliente record) {
        try {
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
            return new Result(record);
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Error.make("EXCEPTION", ex.toString()));
        }
    }

    @Override
    public Result<Boolean> delete(int id) {
        try {
            this.database.context().connect();
            var query = "DELETE FROM Cliente WHERE idCliente = ?";
            var statement = this.database.context().connection().prepareStatement(query);
            statement.setString(1, String.valueOf(id));
            statement.execute();
            this.database.context().disconnect();
            return new Result(true);
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Error.make("EXCEPTION", ex.toString()));
        }

    }

    @Override
    public Result<Cliente> update(int id, Cliente record) {
        try {
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
            return new Result(new Cliente(
                    id,
                    record.nombre(),
                    record.apellidoPaterno(),
                    record.apellidoMaterno(),
                    record.fechaCreacion(),
                    record.fechaNacimiento()
            ));
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Error.make("EXCEPTION", ex.toString()));
        }
    }

    @Override
    public Result<List<Cliente>> findAll() {
        try {
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
            return new Result(listOfClientes);
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Error.make("EXCEPTION", ex.toString()));
        }
    }

    @Override
    public Result<Cliente> findById(int id) {
        try {
            this.database.context().connect();
            var query = "SELECT * FROM Cliente WHERE idCliente=?";
            var statement = this.database.context().connection().prepareStatement(query);
            statement.setString(1, String.valueOf(id));

            var result = statement.executeQuery();

            if (!result.next()) {
                this.database.context().disconnect();
                return new Result(Error.make("NOT_FOUND", "No se encontro el cliente"));
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
            return new Result(cliente);
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Error.make("EXCEPTION", ex.toString()));
        }
    }

    @Override
    public void generateReport() {
        try {
            this.database.context().connect();

            var inputStream = getClass().getResourceAsStream("/reports/ClienteReport.jrxml");
            var design = JRXmlLoader.load(inputStream);
            var report = JasperCompileManager.compileReport(design);

            var jasperPrint = JasperFillManager.fillReport(report, new HashMap(), this.database.context().connection());
            var jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);

            this.database.context().disconnect();
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
