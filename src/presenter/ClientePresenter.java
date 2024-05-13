/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.util.ArrayList;
import java.util.List;
import models.Cliente;
import repositories.ClienteRepository;
import utils.Result;
import utils.Error;

/**
 *
 * @author Mayte Chavez Salazar
 */
public class ClientePresenter {

    private final ClienteRepository repositorio;

    /*
    Constructor 
     */
    public ClientePresenter(ClienteRepository clienteRepository) {
        this.repositorio = clienteRepository;
    }

    /*
    Obtiene todos los registros
     */
    public List<Cliente> FindAll() {
        var result = this.repositorio.findAll();
        if (!result.isError()) {
            return new ArrayList();
        }

        return result.value();
    }

    /*
    Obtiene el cliente por Id 
     */
    public Result<Cliente> FindById(int id) throws Exception {
        var result = this.repositorio.findById(id);

        if (result.isError()) {
            return result;
        }
        return new Result(result.value());
    }

    /*
    Crea un nuevo cliente
     */
    public Result<String> CreateClient(Cliente nuevoCliente) {
        var result = this.repositorio.findById(nuevoCliente.idCliente());

        if (result.isError()) {
            var a = this.repositorio.create(nuevoCliente);
            return new Result("Creado correctamente");
        }

        return new Result(Error.make("CLIENT_EXISTS", result.error().message()));
    }

    /*
    Elimina a un cliente por medio del id que recibe del parametro
     */
    public Result<String> DeleteClient(int id) {
        var result = this.repositorio.findById(id);

        if (result.isError() && result.error().code().equals("NOT_FOUND")) {
            return new Result(Error.make("CLIENT_NOT_EXISTS", "El cliente no existe"));
        }
        return new Result("Elimiando correctamente");
    }

    /*
    Actualiza los datos de un cliente
     */
    public Result<Cliente> UpdateClient(int id, Cliente cliente) {
        var result = this.repositorio.findById(id);

        if (result.isError() && result.error().code().equals("NOT_FOUND")) {
            return new Result(Error.make("CLIENT_NOT_EXISTS", "El cliente no existe"));
        }

        var clienteActualizado = this.repositorio.update(id, cliente);
        return new Result(clienteActualizado);
    }

    /*
    Crea un reporte
     */
    public void CreateReport() {

    }

}
