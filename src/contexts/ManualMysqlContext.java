/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package contexts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christopher
 */
public class ManualMysqlContext implements Context {

    private final Properties configuration;
    private Connection connection;
    private String username;
    private String password;

    public boolean attempt(String username, String password) {
        try {
            this.username = username;
            this.password = password;
            this.connect();
            this.disconnect();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(ManualMysqlContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ManualMysqlContext(Properties configuration) {
        this.configuration = configuration;
    }

    @Override
    public void connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        var host = this.configuration.getProperty("DB_HOST");
        var port = this.configuration.getProperty("DB_PORT");
        var name = this.configuration.getProperty("DB_NAME");

        var url = "jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC";//mysql 8
        this.connection = DriverManager.getConnection(url, username, password);
        System.out.println("Manual MySQL: OK");
    }

    @Override
    public void disconnect() throws Exception {
        this.connection.close();
    }

    @Override
    public Connection connection() {
        return this.connection;
    }

}
