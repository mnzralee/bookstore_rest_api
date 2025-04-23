/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bookstore.exception;

/**
 *
 * @author HP
 */
import javax.ws.rs.NotFoundException;

public class CartNotFoundException extends NotFoundException {
    public CartNotFoundException(String message) {
        super(message);
    }
}