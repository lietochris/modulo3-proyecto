/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package utils;

import contexts.MysqlContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.junit.Before;

/**
 *
 * @author Christopher
 */
public abstract class DatabaseTest {

    protected Database database;

    @Before
    public void setUp() throws FileNotFoundException, IOException {
        var propsInput = new FileInputStream("config.properties");
        var configuration = new Properties();
        configuration.load(propsInput);
        var context = new MysqlContext(configuration);
        this.database = new Database();
        this.database.setContext(context);
        System.out.println("Database setup: OK");
    }

}
