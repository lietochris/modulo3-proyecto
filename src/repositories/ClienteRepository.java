/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.util.ArrayList;
import java.util.List;
import models.Cliente;

/**
 *
 * @author Christopher
 */
public class ClienteRepository {

    public List<Cliente> findAll() {
        var cliente = new Cliente(1, "Test");
        var list = new ArrayList<Cliente>();
        list.add(cliente);
        return list;
    }
}
