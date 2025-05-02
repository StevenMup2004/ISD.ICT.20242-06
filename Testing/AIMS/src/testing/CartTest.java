package testing;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.Cart;

/**
 * JUnit test class for the Cart class.
 * Tests the functionality of the Cart class methods to ensure they work correctly.
 */
public class CartTest {
    
    private Cart cart;
    
    /**
     * Set up a new cart before each test.
     */
    @Before
    public void setUp() {
        cart = new Cart(1);
    }
    
    /**
     * Test that a new cart is empty.
     */
    @Test
    public void testNewCartIsEmpty() {
        assertTrue("New cart should be empty", cart.isEmpty());
        assertEquals("New cart should have 0 items", 0, cart.getCartItemsList().size());
    }
    
    /**
     * Test adding a product to the cart.
     */
    @Test
    public void testAddProductToCart() {
        boolean result = cart.addProductToCart(1, 2, 10.0f);
        
        assertTrue("Adding a valid product should return true", result);
        assertFalse("Cart should not be empty after adding a product", cart.isEmpty());
        assertEquals("Cart should have 1 item", 1, cart.getCartItemsList().size());
        assertEquals("Cart item should have correct product ID", 1, cart.getCartItemsList().get(0).getProductID());
        assertEquals("Cart item should have correct quantity", 2, cart.getCartItemsList().get(0).getQuantity());
    }
    
    /**
     * Test adding a product with an invalid quantity.
     */
    @Test
    public void testAddProductWithInvalidQuantity() {
        boolean result = cart.addProductToCart(1, -1, 10.0f);
        
        assertFalse("Adding a product with negative quantity should return false", result);
        assertTrue("Cart should still be empty", cart.isEmpty());
    }
    
    /**
     * Test adding the same product twice should update quantity.
     */
    @Test
    public void testAddSameProductTwice() {
        cart.addProductToCart(1, 2, 10.0f);
        boolean result = cart.addProductToCart(1, 3, 10.0f);
        
        assertTrue("Adding the same product again should return true", result);
        assertEquals("Cart should still have 1 item", 1, cart.getCartItemsList().size());
        assertEquals("Cart item should have updated quantity", 5, cart.getCartItemsList().get(0).getQuantity());
    }
    
    /**
     * Test calculating the total price of the cart.
     */
    @Test
    public void testCalculateTotalPrice() {
        cart.addProductToCart(1, 2, 10.0f);
        cart.addProductToCart(2, 1, 20.0f);
        
        float expectedTotal = (2 * 10.0f) + (1 * 20.0f);
        float actualTotal = cart.calculateTotalPrice();
        
        assertEquals("Total price should be calculated correctly", expectedTotal, actualTotal, 0.01);
    }
    
    /**
     * Test updating the quantity of a product.
     */
    @Test
    public void testUpdateQuantity() {
        cart.addProductToCart(1, 2, 10.0f);
        boolean result = cart.updateQuantity(1, 5);
        
        assertTrue("Updating quantity of existing product should return true", result);
        assertEquals("Cart item should have updated quantity", 5, cart.getCartItemsList().get(0).getQuantity());
    }
    
    // Test updating quantity of a non-existent product.
    @Test
    public void testUpdateQuantityOfNonExistentProduct() {
        boolean result = cart.updateQuantity(999, 5);
        
        assertFalse("Updating quantity of non-existent product should return false", result);
    }
    
    // Test updating quantity to zero should remove the product.
    @Test
    public void testUpdateQuantityToZero() {
        cart.addProductToCart(1, 2, 10.0f);
        boolean result = cart.updateQuantity(1, 0);
        
        assertTrue("Setting quantity to zero should remove product and return true", result);
        assertTrue("Cart should be empty after removing the only item", cart.isEmpty());
    }
    
    //Test removing a product from the cart.
    @Test
    public void testRemoveProductFromCart() {
        cart.addProductToCart(1, 2, 10.0f);
        cart.addProductToCart(2, 1, 20.0f);
        
        boolean result = cart.removeProductFromCart(1);
        
        assertTrue("Removing existing product should return true", result);
        assertEquals("Cart should have 1 item left", 1, cart.getCartItemsList().size());
        assertEquals("Remaining item should have correct product ID", 2, cart.getCartItemsList().get(0).getProductID());
    }
    
    //Test removing a non-existent product.
    @Test
    public void testRemoveNonExistentProduct() {
        boolean result = cart.removeProductFromCart(999);
        
        assertFalse("Removing non-existent product should return false", result);
    }
    
    /**
     * Test checking if a product is in the cart.
     */
    @Test
    public void testCheckProductInCart() {
        cart.addProductToCart(1, 2, 10.0f);
        
        assertTrue("Should return true for product in cart", cart.checkProductInCart(1));
        assertFalse("Should return false for product not in cart", cart.checkProductInCart(2));
    }
    
    /**
     * Test checking availability of products.
     */
    @Test
    public void testCheckAvailability() {
        // Testing valid cases
        assertTrue("Valid product and quantity should be available", 
                  cart.checkAvailabilityOfChosenItems(1, 10));
        
        // Testing invalid cases
        assertFalse("Invalid product ID should not be available", 
                   cart.checkAvailabilityOfChosenItems(-1, 10));
        assertFalse("Zero quantity should not be available", 
                   cart.checkAvailabilityOfChosenItems(1, 0));
        assertFalse("Negative quantity should not be available", 
                   cart.checkAvailabilityOfChosenItems(1, -5));
        assertFalse("Extremely large quantity should not be available", 
                   cart.checkAvailabilityOfChosenItems(1, 200));
    }
    
    /**
     * Test emptying the cart.
     */
    @Test
    public void testEmptyCart() {
        cart.addProductToCart(1, 2, 10.0f);
        cart.addProductToCart(2, 1, 20.0f);
        
        assertFalse("Cart should not be empty before emptying", cart.isEmpty());
        
        cart.empty();
        
        assertTrue("Cart should be empty after emptying", cart.isEmpty());
        assertEquals("Cart should have 0 items", 0, cart.getCartItemsList().size());
    }
}