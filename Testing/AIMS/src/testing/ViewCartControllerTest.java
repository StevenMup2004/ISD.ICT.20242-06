package testing;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import controller.AddProductToCartController;
import controller.ViewCartController;
import model.Cart;
import model.CartItem;

import java.util.List;

public class ViewCartControllerTest {
    
    private ViewCartController controller;
    private AddProductToCartController addController;
    
    @Before
    public void setUp() {
        AddProductToCartController.clearCarts();
        
        controller = new ViewCartController();
        addController = new AddProductToCartController();
        
        ViewCartController.syncCarts();
        
        boolean added1 = addController.requestToAddProductToCart(1, 5, 1);
        boolean added2 = addController.requestToAddProductToCart(2, 3, 1);
        
        assertTrue("First product should be added successfully", added1);
        assertTrue("Second product should be added successfully", added2);
    }
    
    //Test requesting to view a valid cart.

    @Test
    public void testRequestToViewCart() {
        List<CartItem> cartItems = controller.requestToViewCart(1);
        
        assertNotNull("Should return a list of cart items", cartItems);
        assertEquals("Cart should have 2 items", 2, cartItems.size());
    }
    
    // Test requesting to view an invalid cart.

    @Test
    public void testRequestToViewInvalidCart() {
        List<CartItem> cartItems = controller.requestToViewCart(-1);
        
        assertNull("Should return null for invalid cart ID", cartItems);
    }
    
    // Test getting cart information.
    @Test
    public void testGetCartInfo() {
        Cart cart = controller.getCartInfo(1);
        
        assertNotNull("Should return a cart object", cart);
        assertEquals("Cart should have correct ID", 1, cart.getCartID());
    }
    
    // Test getting information for an invalid cart.
  
    @Test
    public void testGetInvalidCartInfo() {
        Cart cart = controller.getCartInfo(-1);
        
        assertNull("Should return null for invalid cart ID", cart);
    }
    
    // Test calculating the total price of a cart.
   
    @Test
    public void testCalculateTotalPrice() {
        float totalPrice = controller.calculateTotalPrice(1);
        
        // Expected: 5 * 10.0 (Product 1) + 3 * 10.0 (Product 2) = 80.0
        assertEquals("Total price should be calculated correctly", 80.0f, totalPrice, 0.01);
    }
    
    //Test calculating the total price of an invalid cart.
    @Test
    public void testCalculateInvalidCartTotalPrice() {
        float totalPrice = controller.calculateTotalPrice(-1);
        
        assertEquals("Should return -1 for invalid cart ID", -1, totalPrice, 0.01);
    }
}