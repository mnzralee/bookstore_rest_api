/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bookstore.exception;

/**
 *
 * @author HP
 */
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;


public class OutOfStockException extends ClientErrorException {
    public OutOfStockException(String message) {
        super(message, Response.Status.CONFLICT);
    }
}
