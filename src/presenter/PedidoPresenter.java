/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.util.ArrayList;
import java.util.List;
import models.Pedido;
import repositories.PedidoRepository;
import utils.Result;

/**
 *
 * @author Christopher
 */
public class PedidoPresenter {

    private final PedidoRepository repository;

    public PedidoPresenter(repositories.PedidoRepository repository) {
        this.repository = repository;
    }
    
    /*Obtiene todos los registros*/
    public List<Pedido> FindAll() {
        var result = this.repository.findAll();
        return !result.isError() ? new ArrayList<>() : result.value();
    }
    
    /*
    Obtiene datos de la orden por Id
    */
    
    public Result<Pedido> FindOrderById(int id){
        var result = this.repository.findById(id);
        return result.isError() ? new Result(result.error()) : new Result(result.value());
    }
    
    /* Crea una nueva orden */
    public Result<String> CreateOrder(Pedido nuevoPedido){
        var result = this.repository.findById(nuevoPedido.idPedido());
        
        if (result.isError() && result.error().code().equals("NOT_FOUND")) {
            return new Result(utils.Error.make("ORDER_EXISTS", "El pedido ya existe"));
        }

        this.repository.create(nuevoPedido); 

        return new Result("El pedido fue realizado");
    }
    
    /*Elimina un pedido por id*/
    public Result<String> DeleteOrder(int id){
        var result = this.repository.findById(id);
        
        if (result.isError() && result.error().code().equals("NOT_FOUND")) {
            return new Result(utils.Error.make("ORDER_NOT_EXISTS", "La orden no existe"));
        }
        return new Result("Pedido elimiando correctamente");
    }
    
    /* Actualizar pedido*/
    public Result<Pedido> UpdateOrder(int id, Pedido pedido){
        var result = this.repository.findById(id);
        
        if(result.isError() && result.error().code().equals("NOT_FOUND")){
            return new Result(utils.Error.make("ORDER_NOT_EXISTS", "La orden no existe"));
        }
        var pedidoActualizado = this.repository.update(id, pedido);
        return new Result(pedidoActualizado);
    }
    
    /*
    Crea un reporte
     */
    public void CreateReport() {

    }
}
