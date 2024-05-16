/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.util.ArrayList;
import java.util.List;
import models.Empleado;
import repositories.EmpleadoRepository;
import utils.Result;
import utils.Error;
/**
 *
 * @author Mayte Chavez Salazar
 */
public class EmpleadoPresenter {

    private final EmpleadoRepository repository;

    public EmpleadoPresenter(repositories.EmpleadoRepository repository) {
        this.repository = repository;
    }
    
    /*Obtiene todos los registros*/
    public List<Empleado> FindAll() {
        var result = this.repository.findAll();
        return !result.isError() ? new ArrayList<>() : result.value();
    }
    
    /*
    Obtiene datos del empleado por Id
    */
    
    public Result<Empleado> FindEmpleadoById(int id){
        var result = this.repository.findById(id);
        return result.isError() ? new Result(result.error()) : new Result(result.value());
    }
    
    /* Crea un nuevo empleado */
    public Result<String> CrearEmpleado(Empleado nuevoEmpleado){
        var result = this.repository.findById(nuevoEmpleado.idEmpleado());
        
        if (result.isError() && result.error().code().equals("NOT_FOUND")) {
            return new Result(Error.make("EMPLOYEE_EXISTS", "El empleado ya existe"));
        }

        this.repository.create(nuevoEmpleado); 

        return new Result("El empleado fue creado correctamente");
    }
    
    /*Eliminar un empleado por id*/
    public Result<String> DeleteEmpleoyee(int id){
        var result = this.repository.findById(id);
        
        if (result.isError() && result.error().code().equals("NOT_FOUND")) {
            return new Result(Error.make("EMPLOYEE_NOT_EXISTS", "El empleado no existe"));
        }
        return new Result("Empleado elimiando correctamente");
    }
    
    /* Actualizar empleado*/
    public Result<Empleado> UpdateEmployee(int id, Empleado empleado){
        var result = this.repository.findById(id);
        
        if(result.isError() && result.error().code().equals("NOT_FOUND")){
            return new Result(Error.make("EMPLOTEE_NOT_EXISTS", "El empleado no existe"));
        }
        var empleadoActualizado = this.repository.update(id, empleado);
        return new Result(empleadoActualizado);
    }
    
    /*
    Crea un reporte
     */
    public void CreateReport() {

    }

}
