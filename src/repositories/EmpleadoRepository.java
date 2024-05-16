/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Empleado;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;
import utils.Database;
import utils.Result;
import utils.Error;

/**
 *
 * @author Christopher
 */
public class EmpleadoRepository implements Repository<Empleado> {

    private final Database database;

    public EmpleadoRepository(Database database) {
        this.database = database;
    }

    @Override
    public Result<Empleado> create(Empleado record) {
        try {
            this.database.context().connect();
            var query = "INSERT INTO Empleado (idEmpleado, Nombre, ApellidoPaterno, ApellidoMaterno, Correo, FechaInicio) VALUES (?, ?, ?, ?, ?, ?);";
            var statement = this.database.context().connection().prepareStatement(query);
            statement.setString(1, String.valueOf(record.idEmpleado()));
            statement.setString(2, record.nombre());
            statement.setString(3, record.apellidoPaterno());
            statement.setString(4, record.apellidoMaterno());
            statement.setString(5, record.correo());
            statement.setString(6, record.fechaInicio().toString());

            statement.execute();

            this.database.context().disconnect();
            return new Result(record);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

    @Override
    public Result<Boolean> delete(int id) {
        try {
            this.database.context().connect();
            var query = "DELETE FROM Empleado WHERE idEmpleado = ?";
            var statement = this.database.context().connection().prepareStatement(query);
            statement.setString(1, String.valueOf(id));
            statement.execute();
            this.database.context().disconnect();
            return new Result(true);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

    @Override
    public Result<Empleado> update(int id, Empleado record) {
        try {
            this.database.context().connect();
            var query = "UPDATE Empleado SET Nombre=?, ApellidoPaterno=?, ApellidoMaterno=?, Correo=?, FechaInicio=? WHERE idEmpleado=?";

            var statement = this.database.context().connection().prepareStatement(query);
            statement.setString(1, record.nombre());
            statement.setString(2, record.apellidoPaterno());
            statement.setString(3, record.apellidoMaterno());
            statement.setString(4, record.correo());
            statement.setString(5, record.fechaInicio().toString());
            statement.setString(6, String.valueOf(id));

            statement.execute();

            this.database.context().disconnect();
            return new Result(new Empleado(
                    id,
                    record.nombre(),
                    record.apellidoPaterno(),
                    record.apellidoMaterno(),
                    record.correo(),
                    record.fechaInicio()
            ));
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

    @Override
    public Result<List<Empleado>> findAll() {
        try {
            this.database.context().connect();

            var query = "SELECT * FROM Empleado";
            var statement = this.database.context().connection().prepareStatement(query);
            var result = statement.executeQuery();

            var listOfClientes = new ArrayList<Empleado>();
            while (result.next()) {
                listOfClientes.add(new Empleado(
                        result.getInt("idEmpleado"),
                        result.getString("Nombre"),
                        result.getString("ApellidoPaterno"),
                        result.getString("ApellidoMaterno"),
                        result.getString("Correo"),
                        LocalDate.parse(result.getString("FechaInicio"))
                ));

            }
            this.database.context().disconnect();
            return new Result(listOfClientes);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

    @Override
    public Result<Empleado> findById(int id) {
        try {
            this.database.context().connect();
            var query = "SELECT * FROM Empleado WHERE idEmpleado=?";
            var statement = this.database.context().connection().prepareStatement(query);
            statement.setString(1, String.valueOf(id));

            var result = statement.executeQuery();

            if (!result.next()) {
                this.database.context().disconnect();
                return new Result(Error.make("NOT_FOUND", "No se encontro el empleado"));
            }

            var cliente = new Empleado(
                    result.getInt("idEmpleado"),
                    result.getString("Nombre"),
                    result.getString("ApellidoPaterno"),
                    result.getString("ApellidoMaterno"),
                    result.getString("Correo"),
                    LocalDate.parse(result.getString("FechaInicio"))
            );

            this.database.context().disconnect();
            return new Result(cliente);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoRepository.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(Error.make("DATABASE_ERROR", ex.toString()));
        }
    }

    @Override
    public void generateReport() {
            try {
            this.database.context().connect();
            
            var report = JasperCompileManager.compileReport("src/reports/EmpleadoReport.jrxml");
            
            var jasperPrint = JasperFillManager.fillReport(report, new HashMap(), this.database.context().connection());
            var jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            
            this.database.context().disconnect();
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
