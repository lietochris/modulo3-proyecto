/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package modulo3.bootstrap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import utils.Database;
import utils.Router;
import views.ClienteView;
import views.HomeView;
import views.LoginView;
import views.PedidoView;
import views.ProductoView;

/**
 *
 * @author Christopher
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        var propsInput = new FileInputStream("config.properties");
        var configuration = new Properties();
        configuration.load(propsInput);

        var router = new Router();

        var loginView = new LoginView();
        loginView.setRouter(router);

        var homeView = new HomeView();
        homeView.setRouter(router);

        var clienteView = new ClienteView();
        clienteView.setRouter(router);

        var pedidoView = new PedidoView();
        pedidoView.setRouter(router);

        var productoView = new ProductoView();
        productoView.setRouter(router);

        router.setPages(new ArrayList(Arrays.asList(loginView, homeView, clienteView, pedidoView, productoView)));
        router.moveToLoginView();
    }

}
