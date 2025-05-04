package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int cartID;
    private List<CartItem> cartItemsList;
    
    public Cart(int cartID) {
        this.cartID = cartID;
        this.cartItemsList = new ArrayList<>();
    }
    
   
    public int getCartID() {
        return cartID;
    }
    
    
    public List<CartItem> getCartItemsList() {
        return cartItemsList;
    }
    
    
    public float calculateTotalPrice() {
        float totalPrice = 0.0f;
        for (CartItem item : cartItemsList) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }
    
    public boolean checkProductInCart(int productID) {
        for (CartItem item : cartItemsList) {
            if (item.getProductID() == productID) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkAvailabilityOfChosenItems(int productID, int requestedQuantity) {
        // In a real implementation, this would check against the actual product inventory
        // For our testing purposes, we'll assume products with ID > 0 have sufficient inventory
        // if the requested quantity is reasonable (< 100)
        return productID > 0 && requestedQuantity > 0 && requestedQuantity < 100;
    }
    

    public boolean addProductToCart(int productID, int quantity, float price) {
        if (!checkAvailabilityOfChosenItems(productID, quantity)) {
            return false;
        }
        
        for (CartItem item : cartItemsList) {
            if (item.getProductID() == productID) {
                // Product already exists in cart, update quantity
                item.changeQuantityOfProduct(productID, item.getQuantity() + quantity);
                return true;
            }
        }
        
        // Product doesn't exist in cart, add new item
        CartItem newItem = new CartItem(productID, quantity, price, cartID);
        cartItemsList.add(newItem);
        return true;
    }
   
    public boolean updateQuantity(int productID, int newQuantity) {
        if (newQuantity <= 0) {
            // If new quantity is 0 or negative, remove the product
            return removeProductFromCart(productID);
        }
        
        if (!checkAvailabilityOfChosenItems(productID, newQuantity)) {
            return false;
        }
        
        for (CartItem item : cartItemsList) {
            if (item.getProductID() == productID) {
                item.changeQuantityOfProduct(productID, newQuantity);
                return true;
            }
        }
        
        // Product not found in cart
        return false;
    }
    
    public boolean removeProductFromCart(int productID) {
        for (int i = 0; i < cartItemsList.size(); i++) {
            if (cartItemsList.get(i).getProductID() == productID) {
                cartItemsList.remove(i);
                return true;
            }
        }
        return false;
    }
    
   
    public boolean isEmpty() {
        return cartItemsList.isEmpty();
    }
    
    public void empty() {
        cartItemsList.clear();
    }
}