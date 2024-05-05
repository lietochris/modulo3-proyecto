/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package contexts;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author Christopher
 */
public class MysqlContext implements Context {

    private final Properties configuration;
    private Connection connection;

    public MysqlContext(Properties configuration) {
        this.configuration = configuration;
    }

    @Override
    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        var host = this.configuration.getProperty("DB_HOST");
        var port = this.configuration.getProperty("DB_PORT");
        var name = this.configuration.getProperty("DB_NAME");
        var username = this.configuration.getProperty("DB_USER");
        var password = this.configuration.getProperty("DB_PASSWORD");

        var url = "jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC";//mysql 8
        this.connection = DriverManager.getConnection(url, username, password);
        System.out.println("MySQL: OK");
    }

    @Override
    public void disconnect() throws SQLException {
        this.connection.close();
    }

    @Override
    public Connection connection() {
        return this.connection;
    }

}
