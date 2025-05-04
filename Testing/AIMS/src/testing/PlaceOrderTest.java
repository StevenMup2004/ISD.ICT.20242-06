// Pham Thanh Nam - 20225989 - Place Order
package testing;

import controller.OrderController;
import model.*;
import org.junit.Before;
import org.junit.Test;
import service.DeliveryService;
import service.EmailService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Comprehensive tests for OrderController
 * Covers all aspects of the Place Order functionality
 */
public class PlaceOrderTest {

    private OrderController orderController;
    private Cart cart;
    private DeliveryInfo deliveryInfo;
    private MockDeliveryService mockDeliveryService;
    private MockEmailService mockEmailService;
    private MockProduct product1, product2;

    // Test product class for controlled inventory
    private class MockProduct extends Product {
        private boolean hasStock;

        public MockProduct(int id, String title, float price, boolean hasStock) {
            super(id, title, "", price, 0.5f);
            this.hasStock = hasStock;
        }

        @Override
        public boolean checkProductAvailability(int quantity) {
            return hasStock;
        }

        @Override
        public void updateStock(int productId, int quantityChange) {
            // Mock implementation for testing
            System.out.println("Updating stock for product " + productId +
                " by " + quantityChange);
        }
    }

    // Mock DeliveryService for testing
    private class MockDeliveryService extends DeliveryService {
        private float feeToReturn;
        private boolean wasCalculateFeeMethodCalled = false;

        public MockDeliveryService(float feeToReturn) {
            this.feeToReturn = feeToReturn;
        }

        @Override
        public float calculateFee(double weight, String location, double orderTotal) {
            wasCalculateFeeMethodCalled = true;
            return feeToReturn;
        }

        public boolean wasCalculateFeeMethodCalled() {
            return wasCalculateFeeMethodCalled;
        }
    }

    // Mock EmailService for testing
    private class MockEmailService extends EmailService {
        private boolean wasEmailSent = false;
        private String lastEmailAddress;
        private Order lastOrder;

        @Override
        public boolean sendOrderConfirmation(String email, Order order) {
            wasEmailSent = true;
            lastEmailAddress = email;
            lastOrder = order;
            return true;
        }

        public boolean wasEmailSent() {
            return wasEmailSent;
        }

        public String getLastEmailAddress() {
            return lastEmailAddress;
        }

        public Order getLastOrder() {
            return lastOrder;
        }
    }

    @Before
    public void setUp() {
        // Set up mock services
        mockDeliveryService = new MockDeliveryService(22000f);
        mockEmailService = new MockEmailService();

        // Create controller with mock services
        orderController = new OrderController(mockDeliveryService, mockEmailService);

        // Set up test products
        product1 = new MockProduct(1, "Test Book", 50000f, true);
        product2 = new MockProduct(2, "Test CD", 30000f, true);

        // Set up cart
        cart = new Cart(1);
        CartItem cartItem1 = new CartItem(1, 2, 50000f, 1) {
            @Override
            public Product getProduct() {
                return product1;
            }
        };
        CartItem cartItem2 = new CartItem(2, 1, 30000f, 1) {
            @Override
            public Product getProduct() {
                return product2;
            }
        };
        cart.getCartItemsList().add(cartItem1);
        cart.getCartItemsList().add(cartItem2);

        // Set up delivery info
        deliveryInfo = new DeliveryInfo(
            1,
            "Test Customer",
            "test@example.com",
            "0123456789",
            "Hanoi",
            "Cau Giay"
        );
    }

    /**
     * UT051: Test successful order placement with valid cart and delivery info
     */
    @Test
    public void testPlaceOrderSuccess() {
        Order order = orderController.placeOrder(cart, deliveryInfo);

        // Verify order is created
        assertNotNull("Order should be created successfully", order);
        assertEquals("Order status should be 'Pending Processing'",
                    "Pending Processing", order.getStatus());

        // Verify order items
        assertEquals("Order should contain 2 items", 2, order.getOrderItems().size());

        // Verify calculations
        assertEquals("Subtotal should be 130000", 130000f, order.getSubtotal(), 0.01);
        assertEquals("VAT should be 13000", 13000f, order.getVatAmount(), 0.01);

        // Verify delivery service was called
        assertTrue("Delivery service should calculate fee",
                  mockDeliveryService.wasCalculateFeeMethodCalled());

        // Verify email was sent
        assertTrue("Confirmation email should be sent", mockEmailService.wasEmailSent());
        assertEquals("Email should be sent to correct address",
                    "test@example.com", mockEmailService.getLastEmailAddress());
        assertEquals("Email should reference the correct order",
                    order, mockEmailService.getLastOrder());
    }

    /**
     * UT052: Test order placement with empty cart
     */
    @Test
    public void testPlaceOrderEmptyCart() {
        // Create empty cart
        Cart emptyCart = new Cart(2);

        Order order = orderController.placeOrder(emptyCart, deliveryInfo);

        // Verify order is not created
        assertNull("Order should not be created with empty cart", order);
    }

    /**
     * UT053: Test order placement with insufficient inventory
     */
    @Test
    public void testPlaceOrderInsufficientInventory() {
        // Modify product to have no stock
        product1 = new MockProduct(1, "Out of Stock Book", 50000f, false);
        CartItem noStockItem = new CartItem(1, 2, 50000f, 1) {
            @Override
            public Product getProduct() {
                return product1;
            }
        };

        // Create new cart with the out of stock item
        Cart noStockCart = new Cart(3);
        noStockCart.getCartItemsList().add(noStockItem);

        Order order = orderController.placeOrder(noStockCart, deliveryInfo);

        // Verify order is not created
        assertNull("Order should not be created with insufficient inventory", order);
    }

    /**
     * UT054: Test order placement with invalid delivery info
     */
    @Test
    public void testPlaceOrderInvalidDeliveryInfo() {
        // Create invalid delivery info (missing email)
        DeliveryInfo invalidInfo = new DeliveryInfo(
            2,
            "Test Customer",
            "", // Empty email
            "0123456789",
            "Hanoi",
            "Cau Giay"
        );

        Order order = orderController.placeOrder(cart, invalidInfo);

        // Verify order is not created
        assertNull("Order should not be created with invalid delivery info", order);
    }

    /**
     * UT055: Test delivery fee calculation for Hanoi
     */
    @Test
    public void testCalculateDeliveryFeeHanoi() {
        // Create a specific delivery info for Hanoi
        DeliveryInfo hanoiInfo = new DeliveryInfo(
            3,
            "Hanoi Customer",
            "hanoi@example.com",
            "0123456789",
            "Hanoi",
            "Ba Dinh"
        );

        // Set mock delivery service to return Hanoi rate
        mockDeliveryService = new MockDeliveryService(22000f);
        orderController = new OrderController(mockDeliveryService, mockEmailService);

        Order order = orderController.placeOrder(cart, hanoiInfo);

        // Verify delivery fee calculation
        assertNotNull("Order should be created successfully", order);
        assertEquals("Delivery fee for Hanoi should be 22000",
                    22000f, order.getRegularDeliveryFee(), 0.01);
    }

    /**
     * UT056: Test delivery fee calculation for other province
     */
    @Test
    public void testCalculateDeliveryFeeOtherProvince() {
        // Create a specific delivery info for other province
        DeliveryInfo otherProvinceInfo = new DeliveryInfo(
            4,
            "Da Nang Customer",
            "danang@example.com",
            "0123456789",
            "Da Nang",
            "Hai Chau"
        );

        // Set mock delivery service to return other province rate
        mockDeliveryService = new MockDeliveryService(30000f);
        orderController = new OrderController(mockDeliveryService, mockEmailService);

        Order order = orderController.placeOrder(cart, otherProvinceInfo);

        // Verify delivery fee calculation
        assertNotNull("Order should be created successfully", order);
        assertEquals("Delivery fee for other province should be 30000",
                    30000f, order.getRegularDeliveryFee(), 0.01);
    }

    /**
     * UT057: Test free shipping eligibility (full discount)
     */
    @Test
    public void testFreeShippingEligible() {
        // Setup a cart with a high value product to qualify for free shipping
        Cart expensiveCart = new Cart(5);
        MockProduct expensiveProduct = new MockProduct(3, "Expensive Item", 200000f, true);
        CartItem expensiveItem = new CartItem(3, 1, 200000f, 5) {
            @Override
            public Product getProduct() {
                return expensiveProduct;
            }
        };
        expensiveCart.getCartItemsList().add(expensiveItem);

        // Free delivery configuration (for qualifying orders)
        mockDeliveryService = new MockDeliveryService(0f);
        orderController = new OrderController(mockDeliveryService, mockEmailService);

        Order order = orderController.placeOrder(expensiveCart, deliveryInfo);

        // Verify free shipping was applied
        assertNotNull("Order should be created successfully", order);
        assertEquals("Delivery fee should be 0 for eligible orders",
                    0f, order.getRegularDeliveryFee(), 0.01);
    }

    /**
     * UT058: Test free shipping with partial discount
     */
    @Test
    public void testFreeShippingPartial() {
        // Setup for partial free shipping (25000 VND max discount)
        mockDeliveryService = new MockDeliveryService(5000f); // After discount from 30000
        orderController = new OrderController(mockDeliveryService, mockEmailService);

        Order order = orderController.placeOrder(cart, deliveryInfo);

        // Verify partial discount was applied
        assertNotNull("Order should be created successfully", order);
        assertEquals("Delivery fee should be discounted to 5000",
                    5000f, order.getRegularDeliveryFee(), 0.01);
    }

    /**
     * UT059: Test VAT calculation
     */
    @Test
    public void testCalculateVAT() {
        Order order = orderController.placeOrder(cart, deliveryInfo);

        // Verify VAT calculation (10% of subtotal)
        assertNotNull("Order should be created successfully", order);
        assertEquals("Subtotal should be 130000", 130000f, order.getSubtotal(), 0.01);
        assertEquals("VAT should be 13000 (10% of 130000)",
                    13000f, order.getVatAmount(), 0.01);
    }

    /**
     * UT060: Test order persistence
     */
    @Test
    public void testOrderPersistence() {
        Order order = orderController.placeOrder(cart, deliveryInfo);

        // Verify order is in controller's list
        assertNotNull("Order should be created successfully", order);
        List<Order> orderList = orderController.getOrderList();
        assertTrue("Order should be in controller's list",
                  orderList.contains(order));

        // Verify current order is set
        assertEquals("Current order should be the newly created order",
                    order, orderController.getCurrentOrder());
    }

    /**
     * UT061: Test email confirmation
     */
    @Test
    public void testEmailConfirmation() {
        Order order = orderController.placeOrder(cart, deliveryInfo);

        // Verify email was sent correctly
        assertNotNull("Order should be created successfully", order);
        assertTrue("Email service should send confirmation",
                  mockEmailService.wasEmailSent());
        assertEquals("Email should be sent to customer's email",
                    "test@example.com", mockEmailService.getLastEmailAddress());
        assertEquals("Email should reference the correct order",
                    order, mockEmailService.getLastOrder());
    }
}
