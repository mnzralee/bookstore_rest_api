/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bookstore.resource;

/**
 *
 * @author HP
 */

import com.mycompany.bookstore.exception.BookNotFoundException;
import com.mycompany.bookstore.exception.CartNotFoundException;
import com.mycompany.bookstore.exception.CustomerNotFoundException;
import com.mycompany.bookstore.exception.InvalidInputException;
import com.mycompany.bookstore.model.Book;
import com.mycompany.bookstore.model.Cart;
import com.mycompany.bookstore.model.Customer;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/customers/{customerId}/cart")
public class CartResource {

    // In memory storage for carts
    private final static Map<Integer, Cart> carts = new HashMap<>();

    // In memory storage for customers and books
    private final static Map<Integer, Customer> customers = CustomerResource.customers;
    private final static Map<Integer, Book> books = BookResource.books;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addItemToCart(@PathParam("customerId") int customerId, Book book, @QueryParam("quantity") int quantity) {

        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        if (!books.containsKey(book.getId())) {
            throw new BookNotFoundException("Book not found with id: " + book.getId());
        }

        Cart cart = carts.get(customerId);
        if (cart == null) {
            cart = new Cart(customerId);
            carts.put(customerId, cart);
        }
        cart.addBook(book, quantity);

        return Response.status(Response.Status.CREATED).entity(cart).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Cart getCart(@PathParam("customerId") int customerId) {
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        Cart cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer id: " + customerId);
        }
        return cart;
    }

    @PUT
    @Path("/{bookId}")
    public Response updateCartItemQuantity(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId, @QueryParam("quantity") int quantity) {
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        if (!books.containsKey(bookId)) {
            throw new BookNotFoundException("Book not found with id: " + bookId);
        }

        Cart cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer id: " + customerId);
        }
        cart.updateQuantity(bookId, quantity);
        return Response.ok(cart).build();
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response removeCartItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        if (!books.containsKey(bookId)) {
             throw new BookNotFoundException("Book not found with id: " + bookId);
        }

        Cart cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer id: " + customerId);
        }
        cart.removeBook(bookId);
        return Response.ok(cart).build();
    }
}

