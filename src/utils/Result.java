/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Christopher
 * @param <T>
 */
public class Result<T> {

    private Error error;
    private T value;

    private boolean isError;

    public Result(Error error) {
        this.error = error;
        this.isError = true;
    }

    public Result(T value) {
        this.value = value;
        this.isError = false;
    }

    public boolean isError() {
        return this.isError;
    }

    public T value() {
        return this.value;
    }

    public Error error() {
        return this.error;
    }
}
