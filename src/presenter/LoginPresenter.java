/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import contexts.ManualMysqlContext;
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

}
