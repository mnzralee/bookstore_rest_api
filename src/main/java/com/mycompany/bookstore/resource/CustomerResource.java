/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bookstore.resource;

/**
 *
 * @author HP
 */

import com.mycompany.bookstore.exception.CustomerNotFoundException;
import com.mycompany.bookstore.exception.InvalidInputException;
import com.mycompany.bookstore.model.Customer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers")
public class CustomerResource {

    // IN memory storage for customers
    public final static Map<Integer, Customer> customers = new HashMap<>();
    private static int nextCustomerId = 1;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer customer) {
        if (customer.getFirstName() == null || customer.getFirstName().isEmpty() ||
            customer.getLastName() == null || customer.getLastName().isEmpty() ||
            customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new InvalidInputException("First name, Last name, and Email are required.");
        }
        customer.setId(nextCustomerId++);
        customers.put(customer.getId(), customer);
        return Response.status(Response.Status.CREATED)
                .entity(customer)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getCustomer(@PathParam("id") int id) {
        if (!customers.containsKey(id)) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
        return customers.get(id);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Customer updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        if (!customers.containsKey(id)) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
        if (updatedCustomer.getFirstName() == null || updatedCustomer.getFirstName().isEmpty() ||
            updatedCustomer.getLastName() == null || updatedCustomer.getLastName().isEmpty() ||
            updatedCustomer.getEmail() == null || updatedCustomer.getEmail().isEmpty()) {
             throw new InvalidInputException("First name, Last name, and Email are required.");
        }
        updatedCustomer.setId(id);
        customers.put(id, updatedCustomer);
        return updatedCustomer;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteCustomer(@PathParam("id") int id) {
        if (!customers.containsKey(id)) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
        customers.remove(id);
    }
}

