/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repositories;

import java.util.List;

/**
 *
 * @author Christopher
 * @param <T>
 */
public interface Repository<T> {

    public T create(T record);

    public void delete(int id);

    public T update(int id, T record);

    public List<T> findAll();

    public T findById(int id);
}
