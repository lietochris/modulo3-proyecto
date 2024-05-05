/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package contexts;

import java.sql.Connection;

/**
 *
 * @author Christopher
 */
public interface Context {
    
    public void connect() throws Exception;
    public void disconnect() throws Exception;
    public Connection connection();
}
