package controller;

import model.Cart;
import model.CartItem;
import model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller class for handling viewing the cart and retrieving its information.
 */
public class ViewCartController {
    // In-memory storage for carts (for testing purposes)
    private static Map<Integer, Cart> cartMap = new HashMap<>();
    
    /**
     * Handles the request to view a cart's contents.
     * 
     * @param cartID the ID of the cart to view
     * @return list of products in the cart, or null if the cart doesn't exist
     */
    public List<CartItem> requestToViewCart(int cartID) {
        if (cartID <= 0) {
            return null;
        }
        
        Cart cart = getCart(cartID);
        if (cart == null) {
            return null;
        }
        
        // Get cart info
        List<CartItem> cartItems = cart.getCartItemsList();
        
        // Verify product availability for each item
        checkProductsAvailability(cartItems);
        
        return cartItems;
    }
    
    /**
     * Gets the cart information including items, total price.
     * 
     * @param cartID the ID of the cart
     * @return a cart object containing all information, or null if the cart doesn't exist
     */
    public Cart getCartInfo(int cartID) {
        if (cartID <= 0) {
            return null;
        }
        
        return getCart(cartID);
    }
    
    /**
     * Calculates the total price of items in the cart.
     * 
     * @param cartID the ID of the cart
     * @return the total price, or -1 if the cart doesn't exist
     */
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
    
    /**
     * Checks the availability of products in the cart items.
     * If any item has insufficient stock, a notification is sent.
     * 
     * @param cartItems the list of cart items to check
     */
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
    
    /**
     * Notifies the user of insufficient stock.
     * In a real application, this would be connected to the UI.
     */
    private void notifyInsufficientStock() {
        // In a real application, this would show a notification in the UI
        System.err.println("Warning: Some items in your cart have insufficient stock.");
    }
    
    /**
     * Retrieves a product by ID.
     * In a real application, this would come from a database or service.
     * 
     * @param productID the ID of the product to retrieve
     * @return the Product object, or null if not found
     */
    private Product getProduct(int productID) {
        // This is a simplified implementation
        // In a real application, this would query a database or service
        if (productID <= 0) {
            return null;
        }
        
        // Create a dummy product for testing purposes
        return new Product(productID, "Product " + productID, 10.0f, 100);
    }
    
    /**
     * Retrieves a cart by ID.
     * In a real application, this would come from a session or service.
     * 
     * @param cartID the ID of the cart to retrieve
     * @return the Cart object, or null if not found
     */
    private Cart getCart(int cartID) {
        // This is a simplified implementation
        // In a real application, this would query a session or service
        if (cartID <= 0) {
            return null;
        }
        
        // Get cart from the map of carts for testing
        syncCarts();
        return cartMap.get(cartID);
    }
    
    /**
     * For testing: Syncs with the cart map from AddProductToCartController
     */
    public static void syncCarts() {
        // In a real application, this would not be needed
        // This is just for testing to ensure controllers share the same cart data
        cartMap = AddProductToCartController.getCartMap();
    }
}