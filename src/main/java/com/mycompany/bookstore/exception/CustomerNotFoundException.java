/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bookstore.exception;

/**
 *
 * @author HP
 */
import jakarta.ws.rs.NotFoundException;

public class CustomerNotFoundException extends NotFoundException{
    public CustomerNotFoundException(String message) {
        super(message);
    }
}