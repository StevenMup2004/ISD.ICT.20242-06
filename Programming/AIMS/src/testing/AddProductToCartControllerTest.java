// Vu Hai Dang - 20225962 - UseCase Manage Cart
package testing;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import controller.AddProductToCartController;

public class AddProductToCartControllerTest {
    
    private AddProductToCartController controller;
    
    
    @Before
    public void setUp() {
        AddProductToCartController.clearCarts();
        controller = new AddProductToCartController();
    }
    
    //Test adding a valid product to the cart.
    @Test
    public void testAddValidProductToCart() {
        boolean result = controller.requestToAddProductToCart(1, 5, 1);
        
        assertTrue("Adding a valid product should return true", result);
    }
    
    // Test adding a product with invalid ID.
    @Test
    public void testAddProductWithInvalidID() {
        boolean result = controller.requestToAddProductToCart(-1, 5, 1);
        
        assertFalse("Adding a product with invalid ID should return false", result);
    }
    
    //Test adding a product with invalid quantity.
    @Test
    public void testAddProductWithInvalidQuantity() {
        boolean result = controller.requestToAddProductToCart(1, -5, 1);
        
        assertFalse("Adding a product with invalid quantity should return false", result);
    }
    
    //Test adding a product to an invalid cart.
    @Test
    public void testAddProductToInvalidCart() {
        boolean result = controller.requestToAddProductToCart(1, 5, -1);
        
        assertFalse("Adding a product to invalid cart should return false", result);
    }
    
    // Test checking if a product is in the cart.
    @Test
    public void testCheckProductInCart() {
        
        boolean addResult = controller.requestToAddProductToCart(1, 5, 1);
        assertTrue("Product should be added successfully", addResult);
  
        boolean checkResult = controller.checkProductInCart(1, 1); 
        assertTrue("Product should be in the cart after adding it", checkResult);
    }
    
    // Test checking if a product is in a non-existent cart.
    @Test
    public void testCheckProductInNonExistentCart() {
        boolean result = controller.checkProductInCart(1, -1);
        
        assertFalse("Checking in a non-existent cart should return false", result);
    }
}