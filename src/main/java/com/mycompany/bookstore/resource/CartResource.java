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
import com.mycompany.bookstore.exception.OutOfStockException;
import com.mycompany.bookstore.model.Book;
import com.mycompany.bookstore.model.Cart;
import com.mycompany.bookstore.model.Customer;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers/{customerId}/cart")
public class CartResource {

    // In memory storage for carts
    public final static Map<Integer, Cart> carts = new HashMap<>();

    // In memory storage for customers and books
    private final static Map<Integer, Customer> customers = CustomerResource.customers;
    private final static Map<Integer, Book> books = BookResource.books;

    @POST
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addItemToCart(@PathParam("customerId") int customerId, Book book, @QueryParam("quantity") int quantity) {

        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        if (book == null || book.getId() == 0) {
            throw new InvalidInputException("Book ID must be provided.");
        }
        if (!books.containsKey(book.getId())) {
            throw new BookNotFoundException("Book not found with id: " + book.getId());
        }

        // Fetch the full Book object from storage
        Book fullBook = books.get(book.getId());
        
        if (quantity > fullBook.getStock()) {
            throw new OutOfStockException("Book Out of Stock");
        }
        
        int updatedStock = fullBook.getStock() - quantity;
        fullBook.setStock(updatedStock);
        
        Cart cart = carts.get(customerId);
        if (cart == null) {
            cart = new Cart(customerId);
            carts.put(customerId, cart);
        }

        cart.addBook(fullBook, quantity);

        return Response.status(Response.Status.OK).entity("{\"message\":\"Item added to cart.\"}").build();
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
    @Path("/items/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
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
        return Response.ok("{\"message\":\"Updated Book ID: " + bookId + " to quantity " + quantity + "\"}").build();
    }

    @DELETE
    @Path("/items/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
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
        return Response.ok("{\"message\":\"Item removed from cart.\"}").build();
    }
}

