package controller;

import model.Cart;
import model.Product;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for handling updates to the cart, including
 * changing quantities and removing products.
 */
public class UpdateCartController {
    // In-memory storage for carts (for testing purposes)
    private static Map<Integer, Cart> cartMap = new HashMap<>();
    
    /**
     * Handles the request to update a product's quantity in the cart.
     * 
     * @param productID the ID of the product to update
     * @param quantity the new quantity
     * @param cartID the ID of the cart to update
     * @return true if update was successful, false otherwise
     */
    public boolean requestToUpdateCart(int productID, int quantity, int cartID) {
        // Check input validity
        if (productID <= 0 || cartID <= 0) {
            return false;
        }
        
        // Get the cart (in a real application, this would come from a session or service)
        Cart cart = getCart(cartID);
        if (cart == null) {
            return false;
        }
        
        // If quantity is 0 or negative, remove the product
        if (quantity <= 0) {
            return cart.removeProductFromCart(productID);
        }
        
        // Get the product (in a real application, this would come from a database or service)
        Product product = getProduct(productID);
        if (product == null) {
            return false;
        }
        
        // Check if quantity is available
        if (!product.checkProductAvailability(quantity)) {
            notifyError("The requested quantity is not available.");
            return false;
        }
        
        // Update the cart
        return cart.updateQuantity(productID, quantity);
    }
    
    /**
     * Removes a product from the cart.
     * 
     * @param productID the ID of the product to remove
     * @param cartID the ID of the cart to remove from
     * @return true if removal was successful, false otherwise
     */
    public boolean removeProductFromCart(int productID, int cartID) {
        if (productID <= 0 || cartID <= 0) {
            return false;
        }
        
        Cart cart = getCart(cartID);
        if (cart == null) {
            return false;
        }
        
        return cart.removeProductFromCart(productID);
    }
    
    /**
     * Changes the quantity of a product in the cart.
     * 
     * @param productID the ID of the product to update
     * @param quantity the new quantity
     * @param cartID the ID of the cart
     * @return true if update was successful, false otherwise
     */
    public boolean updateQuantity(int productID, int quantity, int cartID) {
        if (productID <= 0 || quantity < 0 || cartID <= 0) {
            return false;
        }
        
        Cart cart = getCart(cartID);
        if (cart == null) {
            return false;
        }
        
        Product product = getProduct(productID);
        if (product == null) {
            return false;
        }
        
        if (quantity > 0 && !product.checkProductAvailability(quantity)) {
            notifyError("The requested quantity is not available.");
            return false;
        }
        
        return cart.updateQuantity(productID, quantity);
    }
    
    /**
     * Notifies the user of an error.
     * In a real application, this would be connected to the UI.
     * 
     * @param message the error message to display
     */
    private void notifyError(String message) {
        // In a real application, this would show an error dialog or update the UI
        System.err.println("Error: " + message);
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
        
        // Get or create cart for testing purposes
        if (!cartMap.containsKey(cartID)) {
            cartMap.put(cartID, new Cart(cartID));
        }
        
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