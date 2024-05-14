/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.util.ArrayList;
import java.util.List;
import models.Proveedor;
import repositories.ProveedorRepository;
import utils.Result;

/**
 *
 * @author Christopher / Mayte Chavez Salazar
 */
public class ProveedorPresenter {

    private final ProveedorRepository repository;

    public ProveedorPresenter(repositories.ProveedorRepository repository) {
        this.repository = repository;
    }

    /*Obtiene todos los registros*/
    public List<Proveedor> FindAll() {
        var result = this.repository.findAll();
        return result.value();
    }

    /*
    Obtiene datos del proveedor por Id
     */
    public Result<Proveedor> FindSupplierById(int id) {
        var result = this.repository.findById(id);
        return result.isError() ? new Result(result.error()) : new Result(result.value());
    }

    /* Crea un nuevo proovedor */
    public Result<String> CreateSupplier(Proveedor nuevoProveedor) {
        var result = this.repository.findById(nuevoProveedor.idProveedor());

        if (result.isError()) {
            this.repository.create(nuevoProveedor);
            return new Result("El proveedor fue creado");
        }

        return new Result(utils.Error.make("SUPPLIER_EXISTS", "El proveedor ya existe"));
    }

    /*Elimina un proveedor por id*/
    public Result<String> DeleteSupplier(int id) {
        var result = this.repository.findById(id);

        if (result.isError() && result.error().code().equals("NOT_FOUND")) {
            return new Result(utils.Error.make("SUPPLIER_NOT_EXISTS", "El proveedor no existe"));
        }
        this.repository.delete(id);
        return new Result("Proveedor elimiando correctamente");
    }

    /* Actualizar proveedor*/
    public Result<Proveedor> UpdateSupplier(int id, Proveedor proveedor) {
        var result = this.repository.findById(id);

        if (result.isError() && result.error().code().equals("NOT_FOUND")) {
            return new Result(utils.Error.make("SUPPLIER_NOT_EXISTS", "El proveedor no existe"));
        }
        var proveedorActualizado = this.repository.update(id, proveedor);
        return new Result(proveedorActualizado);
    }

    /*
    Crea un reporte
     */
    public void CreateReport() {
        this.repository.generateReport();
    }

}
