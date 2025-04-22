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
import com.mycompany.bookstore.exception.InvalidInputException;
import com.mycompany.bookstore.model.Book;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/books")
public class BookResource {

    // In memory storage for books, public to make it accessible to other classes
    public final static Map<Integer, Book> books = new HashMap<>();
    private static int nextBookId = 1;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBook(@PathParam("id") int id) {
        if (!books.containsKey(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        return books.get(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty() || book.getAuthorId() == null || book.getAuthorId().isEmpty()) {
            throw new InvalidInputException("Title and Author ID are required.");
        }
        book.setId(nextBookId++);
        books.put(book.getId(), book);
        return Response.status(Response.Status.CREATED)
                .entity(book)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book updateBook(@PathParam("id") int id, Book updatedBook) {
        if (!books.containsKey(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        if (updatedBook.getTitle() == null || updatedBook.getTitle().isEmpty() || updatedBook.getAuthorId() == null || updatedBook.getAuthorId().isEmpty()) {
            throw new InvalidInputException("Title and Author ID are required.");
        }
        updatedBook.setId(id);
        books.put(id, updatedBook);
        return updatedBook;
    }

    @DELETE
    @Path("/{id}")
    public void deleteBook(@PathParam("id") int id) {
        if (!books.containsKey(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        books.remove(id);
    }
}

