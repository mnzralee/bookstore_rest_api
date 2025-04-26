/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bookstore.resource;

/**
 *
 * @author HP
 */

import com.mycompany.bookstore.exception.AuthorNotFoundException;
import com.mycompany.bookstore.exception.InvalidInputException;
import com.mycompany.bookstore.model.Author;
import com.mycompany.bookstore.model.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authors")
public class AuthorResource {

    // In memory storage for authors
    public final static Map<Integer, Author> authors = new HashMap<>();
    private static int nextAuthorId = 1;

    // In memory storage for books from BookResource
    private final static Map<Integer, Book> books = BookResource.books;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAuthor(Author author) {
        if (author.getFirstName() == null || author.getFirstName().isEmpty() || author.getLastName() == null || author.getLastName().isEmpty()) {
            throw new InvalidInputException("First name and Last name are required.");
        }
        author.setId(nextAuthorId++);
        authors.put(author.getId(), author);
        return Response.status(Response.Status.CREATED)
                .entity(author)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> getAllAuthors() {
        return new ArrayList<>(authors.values());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Author getAuthor(@PathParam("id") int id) {
        if (!authors.containsKey(id)) {
            throw new AuthorNotFoundException("Author not found with id: " + id);
        }
        return authors.get(id);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Author updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        if (!authors.containsKey(id)) {
            throw new AuthorNotFoundException("Author not found with id: " + id);
        }
         if (updatedAuthor.getFirstName() == null || updatedAuthor.getFirstName().isEmpty() || updatedAuthor.getLastName() == null || updatedAuthor.getLastName().isEmpty()) {
            throw new InvalidInputException("First name and Last name are required.");
        }
        updatedAuthor.setId(id);
        authors.put(id, updatedAuthor);
        return updatedAuthor;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteAuthor(@PathParam("id") int id) {
        if (!authors.containsKey(id)) {
            throw new AuthorNotFoundException("Author not found with id: " + id);
        }
        authors.remove(id);
    }

    @GET
    @Path("/{id}/books")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooksByAuthor(@PathParam("id") int authorId) {
        if (!authors.containsKey(authorId)) {
            throw new AuthorNotFoundException("Author not found with id: " + authorId);
        }
        return books.values().stream()
                .filter(book -> book.getAuthorId().equals(String.valueOf(authorId)))
                .collect(Collectors.toList());
    }
}

