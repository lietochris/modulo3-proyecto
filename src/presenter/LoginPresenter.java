/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import contexts.ManualMysqlContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import utils.Database;
import utils.Error;
import utils.Result;

/**
 *
 * @author Christopher
 */
public class LoginPresenter {

    private final Database database;
    private final Properties configuration;

    public LoginPresenter(Properties configuration, Database database) {
        this.database = database;
        this.configuration = configuration;
    }

    public Result<String> signIn(String username, String password) {

        var context = new ManualMysqlContext(this.configuration);

        if (!context.attempt(username, password)) {
            return new Result(Error.make("INVALID_CREDENTIALS", "Usuario y contraseña invalidos"));
        }

        this.database.setContext(context);
        return new Result("OK");

    }

    public boolean validarLogin(String usuario, String contrasena) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            this.database.context().connect();
            connection = database.context().connection();
            String query = "SELECT user, password  FROM mysql.user WHERE usuario = ? AND contrasena = ?";

            statement = connection.prepareStatement(query);
            statement.setString(1, usuario);
            statement.setString(2, contrasena);

            resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
