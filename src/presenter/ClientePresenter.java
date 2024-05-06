/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.util.ArrayList;
import java.util.List;
import models.Cliente;
import repositories.ClienteRepository;

/**
 *
 * @author Mayte Chavez Salazar
 */
public class ClientePresenter {
    
        private ClienteRepository repositorio;
    
    /*
    Constructor 
    */
    public ClientePresenter(ClienteRepository clienteRepository) {
        this.repositorio = clienteRepository;
    }
    
    /*
    Obtiene todos los registros
    */

    public List<Cliente> FindAll(){
            List<Cliente> clientes = new ArrayList<>();
    try {
        clientes = this.repositorio.findAll();
    } catch (Exception e) {

    }
    return clientes;
    }
    
    /*
    Obtiene el cliente por Id 
    */
    public Cliente FindById(int id){
        Cliente cliente = this.FindById(id);     
        return cliente;
    }
    
    
    /*
    Crea un nuevo cliente
    */
    public String CreateClient(Cliente nuevoCliente){
        var message = "";
        try{
        var cliente = this.repositorio.findById(nuevoCliente.idCliente());
        
        if(cliente != null){
            message = "El cliente ya existe";
            
            return message;
        }else{
            this.repositorio.create(cliente);
            message = "El cliente fue registrado con exito";
            
            return message;
        }
        
        } catch (Exception ex){
            message = ex.toString(); 
            return message;
        }
    }
    
    /*
    Elimina a un cliente por medio del id que recibe del parametro
    */
    public void DeleteClient(int id){
        this.DeleteClient(id);
    }
    
    /*
    Actualiza los datos de un cliente
    */
    public Cliente UpdateClient(int id, Cliente cliente){
        Cliente clienteActualizado = this.UpdateClient(id, cliente);
        return clienteActualizado;
    }
    
    /*
    Crea un reporte
    */
    public void CreateReport(){ 
        
    }
    
    
}
