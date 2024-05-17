/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.Pedido;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import utils.Database;
import utils.Error;
import utils.Result;

/**
 * @author Christopher
 */
public class PedidoRepository implements Repository<Pedido> {

    private final Database database;

    public PedidoRepository(Database database) {
        this.database = database;
    }

    @Override
    public Result<Pedido> create(Pedido record) {
        try {
            this.database.context().connect();
            var query = "INSERT INTO Pedido (idPedido, Cantidad, FechaCreacion, Observaciones, Entregado, TotalPago) VALUES (?, ?, ?, ?, ?, ?)";
            var statement = this.database.context().connection().prepareStatement(query);
            statement.setString(1, String.valueOf(record.idPedido()));
            statement.setString(2, String.valueOf(record.cantidad()));
            statement.setString(3, String.valueOf(record.fechaCreacion()));
            statement.setString(4, record.observaciones());
            statement.setBoolean(5, record.entregado());
            statement.setDouble(6, record.totalPago());

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
            var query = "DELETE FROM Pedido WHERE idPedido=?";
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
    public Result<Pedido> update(int id, Pedido record) {
        try {
            this.database.context().connect();
            var query = "UPDATE Pedido SET Cantidad=?, FechaCreacion=?, Observaciones=?, Entregado=?, TotalPago=? WHERE idPedido=?";

            var statement = this.database.context().connection().prepareStatement(query);
            statement.setInt(1, record.cantidad());
            statement.setString(2, record.fechaCreacion().toString());
            statement.setString(3, record.observaciones());
            statement.setBoolean(4, record.entregado());
            statement.setDouble(5, record.totalPago());
            statement.setInt(6, id);

            statement.execute();

            this.database.context().disconnect();
            return new Result(new Pedido(
                    id,
                    record.cantidad(),
                    record.observaciones(),
                    record.entregado(),
                    record.totalPago(),
                    record.fechaCreacion()
            ));
        } catch (Exception ex) {
            Logger.getLogger(ClienteRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

    @Override
    public Result<List<Pedido>> findAll() {
        try {
            this.database.context().connect();

            var query = "SELECT * FROM Pedido";
            var statement = this.database.context().connection().prepareStatement(query);
            var result = statement.executeQuery();

            var listOfClientes = new ArrayList<Pedido>();
            while (result.next()) {
                listOfClientes.add(new Pedido(
                        result.getInt("idPedido"),
                        result.getInt("Cantidad"),
                        result.getString("Observaciones"),
                        result.getBoolean("Entregado"),
                        result.getDouble("TotalPago"),
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
    public Result<Pedido> findById(int id) {
        try {
            this.database.context().connect();
            var query = "SELECT * FROM Pedido WHERE idPedido=?";
            var statement = this.database.context().connection().prepareStatement(query);
            statement.setInt(1, id);

            var result = statement.executeQuery();

            if (!result.next()) {
                this.database.context().disconnect();
                return new Result(Error.make("NOT_FOUND", "No se encontro el pedido"));
            }

            var cliente = new Pedido(
                    result.getInt("idPedido"),
                    result.getInt("Cantidad"),
                    result.getString("Observaciones"),
                    result.getBoolean("Entregado"),
                    result.getDouble("TotalPago"),
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

            var inputStream = getClass().getResourceAsStream("/reports/PedidoReport.jrxml");
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
