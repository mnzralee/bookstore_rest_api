/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bookstore.util;

/**
 *
 * @author HP
 */

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class AppConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        // Resource Classes
        classes.add(com.mycompany.bookstore.resource.BookResource.class);
        classes.add(com.mycompany.bookstore.resource.AuthorResource.class);
        classes.add(com.mycompany.bookstore.resource.CustomerResource.class);
        classes.add(com.mycompany.bookstore.resource.CartResource.class);
        classes.add(com.mycompany.bookstore.resource.OrderResource.class);
        
        // Exception Mapper Classes
        classes.add(com.mycompany.bookstore.exception.BookNotFoundExceptionMapper.class); 
        classes.add(com.mycompany.bookstore.exception.AuthorNotFoundExceptionMapper.class);
        classes.add(com.mycompany.bookstore.exception.CustomerNotFoundExceptionMapper.class);
        classes.add(com.mycompany.bookstore.exception.InvalidInputExceptionMapper.class);
        classes.add(com.mycompany.bookstore.exception.OutOfStockExceptionMapper.class);
        classes.add(com.mycompany.bookstore.exception.CartNotFoundExceptionMapper.class);
        return classes;
    }
}
