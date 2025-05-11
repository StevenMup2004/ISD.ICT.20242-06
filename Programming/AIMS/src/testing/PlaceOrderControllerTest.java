// Pham Thanh Nam 20225989 - Place Order
package testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import controller.PlaceOrderController;
import model.*;
import service.DeliveryService;
import service.EmailService;

public class PlaceOrderControllerTest {
    private PlaceOrderController controller;
    private Cart cart;
    private DeliveryInfo deliveryInfo;
    private Product sampleProduct;
    
    @BeforeEach
    void setUp() {
        // Create controller with mock services for testing
        DeliveryService mockDeliveryService = new DeliveryService(); // In a real test, use a mock
        EmailService mockEmailService = new EmailService(); // In a real test, use a mock
        controller = new PlaceOrderController(mockDeliveryService, mockEmailService);
        
        // Setup test cart
        cart = new Cart(1);
        sampleProduct = new Product(1, "Test Product", 100.0f, 10); // id, name, price, stock
        cart.addCartItem(new CartItem(sampleProduct, 2)); // Product and quantity
        
        // Setup test delivery info
        deliveryInfo = new DeliveryInfo();
        deliveryInfo.setRecipientName("Test Customer");
        deliveryInfo.setEmail("test@example.com");
        deliveryInfo.setPhoneNumber("0987654321");
        deliveryInfo.setProvince("Hanoi");
        deliveryInfo.setAddress("123 Test Street");
    }
    
    @Test
    @DisplayName("Test successful order placement")
    void testPlaceOrderSuccess() {
        Order order = controller.placeOrder(cart, deliveryInfo);
        
        assertNotNull(order, "Order should be created successfully");
        assertEquals("Pending Processing", order.getStatus());
        assertEquals(1, order.getOrderItems().size());
        assertEquals(deliveryInfo, order.getDeliveryInfo());
    }
    
    @Test
    @DisplayName("Test order placement with empty cart")
    void testPlaceOrderEmptyCart() {
        Cart emptyCart = new Cart(2);
        Order order = controller.placeOrder(emptyCart, deliveryInfo);
        
        assertNull(order, "Order should not be created with empty cart");
    }
    
    @Test
    @DisplayName("Test order placement with invalid delivery info")
    void testPlaceOrderInvalidDeliveryInfo() {
        DeliveryInfo invalidInfo = new DeliveryInfo();
        // Missing required fields
        
        Order order = controller.placeOrder(cart, invalidInfo);
        assertNull(order, "Order should not be created with invalid delivery info");
    }
    
    @Test
    @DisplayName("Test invoice generation")
    void testInvoiceGeneration() {
        Order order = controller.placeOrder(cart, deliveryInfo);
        assertNotNull(order, "Order should be created successfully");
        
        Invoice invoice = controller.generateInvoice(order);
        assertNotNull(invoice, "Invoice should be generated");
        assertEquals(order.getOrderItems().size(), invoice.getProductList().size(), 
                    "Invoice should contain all order items");
        
        // Test if subtotal is calculated correctly
        float expectedSubtotal = 0;
        for (OrderItem item : order.getOrderItems()) {
            expectedSubtotal += item.getUnitPrice() * item.getQuantity();
        }
        
        invoice.calculateSubtotal();
        assertEquals(expectedSubtotal, invoice.getTotalAmount(), 
                    "Invoice subtotal should match order items total");
    }
}
