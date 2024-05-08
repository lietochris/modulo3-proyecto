/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.List;

/**
 *
 * @author Christopher
 */
public class Router {

    private List<Page> pages;

    public void addPage(Page page) {
        this.pages.add(page);
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public void redirect(String pageName) {
        for (var page : this.pages) {
            if (pageName.equals(page.pageName())) {
                page.setVisible(true);
            } else {
                page.setVisible(false);
            }
        }
    }

    public void moveToProductoView() {
        this.redirect("ProductoView");
    }

    public void moveToPedidoView() {
        this.redirect("PedidoView");
    }

    public void moveToClienteView() {
        this.redirect("ClienteView");
    }

    public void moveToLoginView() {
        this.redirect("LoginView");
    }

    public void moveToHomeView() {
        this.redirect("HomeView");
    }

    public void moveToEmpleadoView() {
        this.redirect("EmpleadoView");
    }

    public void moveToProveedorView() {
        this.redirect("ProveedorView");
    }
}
