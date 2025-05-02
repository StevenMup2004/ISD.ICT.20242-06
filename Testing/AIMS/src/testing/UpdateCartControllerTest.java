package testing;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import controller.AddProductToCartController;
import controller.UpdateCartController;

/**
 * JUnit test class for the UpdateCartController class.
 */
public class UpdateCartControllerTest {
    
    private UpdateCartController controller;
    private AddProductToCartController addController;
    
    /**
     * Set up a new controller before each test and add a product to the cart.
     */
    @Before
    public void setUp() {
        // Clear any existing carts to ensure test isolation
        AddProductToCartController.clearCarts();
        
        controller = new UpdateCartController();
        addController = new AddProductToCartController();
        
        // Sync carts between controllers
        UpdateCartController.syncCarts();
        
        // Add a product to the cart for testing
        boolean added = addController.requestToAddProductToCart(1, 5, 1);
        assertTrue("Product should be added successfully", added);
    }
    
    /**
     * Test updating the quantity of a product in the cart.
     */
    @Test
    public void testUpdateQuantity() {
        boolean result = controller.updateQuantity(1, 10, 1);
        
        assertTrue("Updating quantity of existing product should return true", result);
    }
    
    /**
     * Test updating the quantity of a non-existent product.
     */
    @Test
    public void testUpdateQuantityOfNonExistentProduct() {
        boolean result = controller.updateQuantity(999, 10, 1);
        
        assertFalse("Updating quantity of non-existent product should return false", result);
    }
    
    /**
     * Test updating to an invalid quantity.
     */
    @Test
    public void testUpdateToInvalidQuantity() {
        boolean result = controller.updateQuantity(1, -5, 1);
        
        assertFalse("Updating to negative quantity should return false", result);
    }
    
    /**
     * Test updating a product in an invalid cart.
     */
    @Test
    public void testUpdateProductInInvalidCart() {
        boolean result = controller.updateQuantity(1, 10, -1);
        
        assertFalse("Updating product in invalid cart should return false", result);
    }
    
    /**
     * Test removing a product from the cart.
     */
    @Test
    public void testRemoveProductFromCart() {
        boolean result = controller.removeProductFromCart(1, 1);
        
        assertTrue("Removing existing product should return true", result);
    }
    
    /**
     * Test removing a non-existent product.
     */
    @Test
    public void testRemoveNonExistentProduct() {
        boolean result = controller.removeProductFromCart(999, 1);
        
        assertFalse("Removing non-existent product should return false", result);
    }
    
    /**
     * Test removing a product from an invalid cart.
     */
    @Test
    public void testRemoveProductFromInvalidCart() {
        boolean result = controller.removeProductFromCart(1, -1);
        
        assertFalse("Removing product from invalid cart should return false", result);
    }
    
    /**
     * Test the full update cart request method.
     */
    @Test
    public void testRequestToUpdateCart() {
        boolean result = controller.requestToUpdateCart(1, 10, 1);
        
        assertTrue("Updating existing product should return true", result);
    }
    
    /**
     * Test requesting update with zero quantity should remove the product.
     */
    @Test
    public void testRequestToUpdateCartWithZeroQuantity() {
        boolean result = controller.requestToUpdateCart(1, 0, 1);
        
        assertTrue("Updating with zero quantity should remove product and return true", result);
    }
}