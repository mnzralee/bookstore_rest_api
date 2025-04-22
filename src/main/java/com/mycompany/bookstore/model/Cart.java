/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bookstore.model;

/**
 *
 * @author HP
 */
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int customerId;
    private List<CartItem> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public Cart(int customerId) {
        this.customerId = customerId;
        this.cartItems = new ArrayList<>();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void addBook(Book book, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getBook().getId() == book.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cartItems.add(new CartItem(book, quantity));
    }


    public void updateQuantity(int bookId, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getBook().getId() == bookId) {
                item.setQuantity(quantity);
                return;
            }
        }
        // Book not found in cart.  You might want to throw an exception here.
    }

    public void removeBook(int bookId) {
        cartItems.removeIf(item -> item.getBook().getId() == bookId);
    }
}

