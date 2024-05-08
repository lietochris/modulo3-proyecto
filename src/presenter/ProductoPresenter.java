/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import repositories.ProductoRepository;

/**
 *
 * @author Christopher
 */
public class ProductoPresenter {

    private final ProductoRepository repository;

    public ProductoPresenter(repositories.ProductoRepository repository) {
        this.repository = repository;
    }

}
