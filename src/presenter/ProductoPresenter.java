/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.util.ArrayList;
import java.util.List;
import models.Producto;
import net.sf.jasperreports.engine.JRException;
import repositories.ProductoRepository;
import utils.Result;

/**
 *
 * @author Christopher / Mayte Chavez Salazar
 */
public class ProductoPresenter {

    private final ProductoRepository repository;

    public ProductoPresenter(repositories.ProductoRepository repository) {
        this.repository = repository;
    }
    
        /*Obtiene todos los registros*/
    public List<Producto> FindAll() {
        var result = this.repository.findAll();
        return !result.isError() ? new ArrayList<>() : result.value();
    }
    
    /*
    Obtiene datos del producto por Id
    */
    
    public Result<Producto> FindProductById(int id){
        var result = this.repository.findById(id);
        return result.isError() ? new Result(result.error()) : new Result(result.value());
    }
    
    /* Crea un nuevo producto */
    public Result<String> CreateProduct(Producto nuevoProducto){
        var result = this.repository.findById(nuevoProducto.idProducto());
        
        if (result.isError() && result.error().code().equals("NOT_FOUND")) {
            return new Result(utils.Error.make("PRODUCT_EXISTS", "El producto ya existe"));
        }

        this.repository.create(nuevoProducto); 

        return new Result("El producto fue");
    }
    
    /*Elimina un producto por id*/
    public Result<String> DeleteProduct(int id){
        var result = this.repository.findById(id);
        
        if (result.isError() && result.error().code().equals("NOT_FOUND")) {
            return new Result(utils.Error.make("PRODUCT_NOT_EXISTS", "El producto no existe"));
        }
        return new Result("Producto elimiando correctamente");
    }
    
    /* Actualizar producto*/
    public Result<Producto> UpdateProduct(int id, Producto producto){
        var result = this.repository.findById(id);
        
        if(result.isError() && result.error().code().equals("NOT_FOUND")){
            return new Result(utils.Error.make("PRODUCT_NOT_EXISTS", "El producto no existe"));
        }
        var productoActualizado = this.repository.update(id, producto);
        return new Result(productoActualizado);
    }
    
   /*
    Crea un reporte
     */
    public void CreateReport() throws JRException, Exception {
        this.repository.generateReport();
    }

}
