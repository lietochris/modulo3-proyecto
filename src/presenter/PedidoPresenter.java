/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.util.ArrayList;
import java.util.List;
import models.Pedido;
import net.sf.jasperreports.engine.JRException;
import repositories.PedidoRepository;
import utils.Result;
import utils.Error;

/**
 *
 * @author Christopher
 */
public class PedidoPresenter {

    private final PedidoRepository repositorio;

     /*
    Constructor 
     */
    public PedidoPresenter(PedidoRepository pedidoRepository) {
        this.repositorio = pedidoRepository;
    }

    /*
    Obtiene todos los registros
     */
    public List<Pedido> FindAll() {
        var result = this.repositorio.findAll();
        if (!result.isError()) {
            return new ArrayList();
        }

        return result.value();
    }
     /*
    Obtiene el Pedido por Id 
     */
    public Result<Pedido> FindById(int id) throws Exception {
        var result = this.repositorio.findById(id);

        if (result.isError()) {
            return result;
        }
        return new Result(result.value());
    }
    
     /*
    Crea un nuevo pedido
     */
    public Result<String> CreatePedido(Pedido nuevoPedido) {
        var result = this.repositorio.findById(nuevoPedido.idPedido());

        if (result.isError()) {
            var a = this.repositorio.create(nuevoPedido);
            return new Result("Creado correctamente");
        }

        return new Result(Error.make("Pedido_EXISTS", result.error().message()));
    }
     /*
    Elimina a un pedido por medio del id que recibe del parametro
     */
    public Result<String> DeletePedido(int id) {
        var result = this.repositorio.findById(id);

        if (result.isError() && result.error().code().equals("NOT_FOUND")) {
            return new Result(Error.make("PEDIDO_NOT_EXISTS", "El pedido no existe"));
        }
        return new Result("Eliminado correctamente");
    }
     /*
    Actualiza los datos de un pedido
     */
    public Result<Pedido> UpdatePedido(int id, Pedido pedido) {
        var result = this.repositorio.findById(id);

        if (result.isError() && result.error().code().equals("NOT_FOUND")) {
            return new Result(Error.make("PEDIDO_NOT_EXISTS", "El pedido no existe"));
        }

        var pedidoActualizado = this.repositorio.update(id, pedido);
        return new Result(pedidoActualizado);
    }

    /*
    Crea un reporte
     */
    public void CreateReport() throws JRException, Exception {
        this.repositorio.generateReport();
    }

}