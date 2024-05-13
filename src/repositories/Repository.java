/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repositories;

import java.util.List;
import utils.Result;

/**
 *
 * @author Christopher
 * @param <T>
 */
public interface Repository<T> {

    public Result<T> create(T record);

    public Result<Boolean> delete(int id);

    public Result<T> update(int id, T record);

    public Result<List<T>> findAll();

    public Result<T> findById(int id);
    
    public void generateReport();
}
