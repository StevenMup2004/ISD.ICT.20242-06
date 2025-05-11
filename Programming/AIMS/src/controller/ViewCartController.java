// Vu Hai Dang - 20225962 - UseCase Manage Cart

/*
 * Cohesion Level: Communicational Cohesion
 * 
 * SRP Violation: Yes
 * This controller:
 * - Displays cart information
 * - Checks product availability
 * 
 * Improvement:
 * 1. Create CartService to handle cart operations and data management
 * 2. Create InventoryService to handle product availability checks
 * 3. Focus solely on view-related operations
 * 
 */

package controller;

import model.Cart;
import model.CartItem;
import model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ViewCartController {
    private static Map<Integer, Cart> cartMap = new HashMap<>();
    
    
    public List<CartItem> requestToViewCart(int cartID) {
        if (cartID <= 0) {
            return null;
        }
        
        Cart cart = getCart(cartID);
        if (cart == null) {
            return null;
        }
        
        List<CartItem> cartItems = cart.getCartItemsList();
        
        checkProductsAvailability(cartItems);
        
        return cartItems;
    }
    
	// PROCEDURAL COHESION: This cart retrieval logic should be in a CartService

    public Cart getCartInfo(int cartID) {
        if (cartID <= 0) {
            return null;
        }
        
        return getCart(cartID);
    }
    
    // COMMUNICATIONAL COHESION: Price calculation mixed with cart retrieval

    public float calculateTotalPrice(int cartID) {
        if (cartID <= 0) {
            return -1;
        }
        
        Cart cart = getCart(cartID);
        if (cart == null) {
            return -1;
        }
        
        return cart.calculateTotalPrice();
    }
    
    // PROCEDURAL COHESION: This inventory checking logic belongs in an InventoryService
    private void checkProductsAvailability(List<CartItem> cartItems) {
        boolean insufficientStock = false;
        
        for (CartItem item : cartItems) {
            Product product = getProduct(item.getProductID());
            if (product != null && !product.checkProductAvailability(item.getQuantity())) {
                insufficientStock = true;
                break;
            }
        }
        
        if (insufficientStock) {
            notifyInsufficientStock();
        }
    }
    
    
    private void notifyInsufficientStock() {
        System.err.println("Warning: Some items in your cart have insufficient stock.");
    }
    
    
    private Product getProduct(int productID) {
        
        if (productID <= 0) {
            return null;
        }
        
        return new Product(productID, "Product " + productID, 10.0f, 100);
    }
    
	// PROCEDURAL COHESION: This cart retrieval logic should be in a CartService

    private Cart getCart(int cartID) {
        if (cartID <= 0) {
            return null;
        }
        
        syncCarts();
        return cartMap.get(cartID);
    }
    
   
    public static void syncCarts() {
        cartMap = AddProductToCartController.getCartMap();
    }
}