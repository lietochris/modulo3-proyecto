/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import models.Producto;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;
import utils.Database;
import utils.Error;
import utils.Result;

/**
 *
 * @author Christopher
 */
public class ProductoRepository implements Repository<Producto> {

    private Database database;

    public ProductoRepository(Database database) {
        this.database = database;
    }

    @Override
    public Result<Producto> create(Producto record) {
        try {
            this.database.context().connect();
            var query = "INSERT INTO Producto (idProducto, Nombre, Stock, Precio, Descripcion, FechaCreacion) VALUES (?, ?, ?, ?, ?, ?);";
            var statement = this.database.context().connection().prepareStatement(query);
            statement.setInt(1, record.idProducto());
            statement.setString(2, record.nombre());
            statement.setInt(3, record.stock());
            statement.setDouble(4, record.precio());
            statement.setString(5, record.descripcion());
            statement.setString(6, String.valueOf(record.fechaCreacion()));

            statement.execute();

            this.database.context().disconnect();
            return new Result<>(record);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result<>(Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

    @Override
    public Result<Boolean> delete(int id) {
        try {
            this.database.context().connect();
            var query = "DELETE FROM Producto WHERE idProducto=?";
            var statement = this.database.context().connection().prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();
            this.database.context().disconnect();
            return new Result(true);
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

    @Override
    public Result<Producto> update(int id, Producto record) {
        try {
            this.database.context().connect();
            var query = "UPDATE Producto SET Nombre=?, Stock=?, Precio=?, Descripcion=?, FechaCreacion=? WHERE idProducto=?;";

            var statement = this.database.context().connection().prepareStatement(query);
            statement.setString(1, record.nombre());
            statement.setInt(2, record.stock());
            statement.setDouble(3, record.precio());
            statement.setString(4, record.descripcion());
            statement.setString(5, String.valueOf(record.fechaCreacion()));
            statement.setInt(6, id);

            statement.execute();

            this.database.context().disconnect();
            return new Result(new Producto(
                    id,
                    record.nombre(),
                    record.stock(),
                    record.precio(),
                    record.descripcion(),
                    record.fechaCreacion()
            ));
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

    @Override
    public Result<List<Producto>> findAll() {
        try {
            this.database.context().connect();

            var query = "SELECT * FROM Producto";
            var statement = this.database.context().connection().prepareStatement(query);
            var result = statement.executeQuery();

            var listOfClientes = new ArrayList<Producto>();
            while (result.next()) {
                listOfClientes.add(new Producto(
                        result.getInt("idProducto"),
                        result.getString("Nombre"),
                        result.getInt("Stock"),
                        result.getDouble("Precio"),
                        result.getString("Descripcion"),
                        LocalDateTime.parse(result.getString("FechaCreacion"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ));

            }
            this.database.context().disconnect();
            return new Result(listOfClientes);
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

    @Override
    public Result<Producto> findById(int id) {
        try {
            this.database.context().connect();
            var query = "SELECT * FROM Producto WHERE idProducto=?";
            var statement = this.database.context().connection().prepareStatement(query);
            statement.setInt(1, id);

            var result = statement.executeQuery();

            if (!result.next()) {
                this.database.context().disconnect();
                return new Result(Error.make("NOT_FOUND", "No se encontro el pedido"));
            }

            var cliente = new Producto(
                    result.getInt("idProducto"),
                    result.getString("Nombre"),
                    result.getInt("Stock"),
                    result.getDouble("Precio"),
                    result.getString("Descripcion"),
                    LocalDateTime.parse(result.getString("FechaCreacion"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            );

            this.database.context().disconnect();
            return new Result(cliente);
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

    @Override
    public void generateReport() {
            try {
            this.database.context().connect();
            
            var report = JasperCompileManager.compileReport("src/reports/ProductoReport.jrxml");
            
            var jasperPrint = JasperFillManager.fillReport(report, new HashMap(), this.database.context().connection());
            var jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            
            this.database.context().disconnect();
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
