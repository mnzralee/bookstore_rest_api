/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bookstore.resource;

/**
 *
 * @author HP
 */

import com.mycompany.bookstore.exception.CartNotFoundException;
import com.mycompany.bookstore.exception.CustomerNotFoundException;
import com.mycompany.bookstore.exception.InvalidInputException;
import com.mycompany.bookstore.model.Cart;
import com.mycompany.bookstore.model.CartItem;
import com.mycompany.bookstore.model.Customer;
import com.mycompany.bookstore.model.Order;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers/{customerId}/orders")
public class OrderResource {

    // In memory storage for orders
    private final static Map<Integer, Order> orders = new HashMap<>();
    private static int nextOrderId = 1;

    // In memory storage for customers and carts
    private final static Map<Integer, Customer> customers = CustomerResource.customers;
    private final static Map<Integer, Cart> carts = CartResource.carts;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(@PathParam("customerId") int customerId) {
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        Cart cart = carts.get(customerId);
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new CartNotFoundException("Cart not found or is empty for customer id: " + customerId);
        }

        // Create the order
        Order order = new Order();
        order.setId(nextOrderId++);
        order.setCustomerId(customerId);
        order.setOrderItems(cart.getCartItems()); //copy cart items
        order.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        // Calculate total price
        double totalPrice = 0;
        for (CartItem item : cart.getCartItems()) {
            totalPrice += item.getBook().getPrice() * item.getQuantity();
        }
        order.setTotalPrice(totalPrice);

        orders.put(order.getId(), order);

        // Clear the cart after the order is placed
        carts.remove(customerId);

        return Response.status(Response.Status.CREATED)
                .entity(order)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrdersByCustomer(@PathParam("customerId") int customerId) {
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getCustomerId() == customerId) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrderByOrderId(@PathParam("customerId") int customerId, @PathParam("orderId") int orderId) {
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        if (!orders.containsKey(orderId)) {
            throw new InvalidInputException("Order not found with id: " + orderId); //400
        }
        Order order = orders.get(orderId);
        if (order.getCustomerId() != customerId) {
            throw new NotFoundException("Order not found for this customer");  //404
        }
        return order;
    }
}

