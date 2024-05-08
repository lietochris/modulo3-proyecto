/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.util.Properties;
import utils.Database;

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

}
