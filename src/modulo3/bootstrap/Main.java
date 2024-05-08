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
import presenter.ClientePresenter;
import presenter.EmpleadoPresenter;
import presenter.LoginPresenter;
import presenter.PedidoPresenter;
import presenter.ProductoPresenter;
import presenter.ProveedorPresenter;
import repositories.ClienteRepository;
import repositories.EmpleadoRepository;
import repositories.PedidoRepository;
import repositories.ProductoRepository;
import repositories.ProveedorRepository;
import utils.Database;
import utils.Router;
import views.ClienteView;
import views.EmpleadoView;
import views.HomeView;
import views.LoginView;
import views.PedidoView;
import views.ProductoView;
import views.ProveedorView;

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
        var database = new Database();

        var loginView = new LoginView();
        loginView.setRouter(router);
        loginView.setPresenter(new LoginPresenter(configuration, database));

        var homeView = new HomeView();
        homeView.setRouter(router);

        var clienteView = new ClienteView();
        clienteView.setRouter(router);
        clienteView.setPresenter(new ClientePresenter(new ClienteRepository(database)));

        var pedidoView = new PedidoView();
        pedidoView.setRouter(router);
        pedidoView.setPresenter(new PedidoPresenter(new PedidoRepository(database)));

        var productoView = new ProductoView();
        productoView.setRouter(router);
        productoView.setPresenter(new ProductoPresenter(new ProductoRepository(database)));

        var empleadoView = new EmpleadoView();
        empleadoView.setRouter(router);
        empleadoView.setPresenter(new EmpleadoPresenter(new EmpleadoRepository(database)));

        var proveedorView = new ProveedorView();
        proveedorView.setRouter(router);
        proveedorView.setPresenter(new ProveedorPresenter(new ProveedorRepository(database)));

        router.setPages(new ArrayList(Arrays.asList(loginView, homeView, clienteView, pedidoView, productoView, empleadoView, proveedorView)));
        router.moveToLoginView();
    }

}
