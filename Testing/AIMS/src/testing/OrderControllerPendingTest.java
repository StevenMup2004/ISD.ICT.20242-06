// src/test/java/testing/OrderControllerPendingTest.java
package testing;

import controller.OrderController;
import model.Order;
import model.OrderItem;
import model.Product;

import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderControllerPendingTest {

    private Order order;
    private OrderController ctrl;

    // Tạo các Product mẫu
    private Product product1 = new Product(1, "Prod1", 100.0f, 10);
    private Product product2 = new Product(2, "Prod2", 200.0f, 10);
    private Product product3 = new Product(3, "Prod3", 300.0f, 10);

    @BeforeEach
    void setUp() {
        OrderItem item = new OrderItem(product1, 3);
        order = new Order(100);
        ctrl = new OrderController();
    }

    @Test @DisplayName("UT010: Approve an order when stock is sufficient")
    void approveWhenSufficient() {
        assertDoesNotThrow(() -> ctrl.ApproveOrder());
        assertEquals("approved", order.getStatus().toLowerCase());
    }

    @Test @DisplayName("UT011: Reject an order even if stock is sufficient (manager)")
    void rejectEvenIfSufficient() {
        assertDoesNotThrow(() -> ctrl.RejectOrder());
        assertEquals("rejected", order.getStatus().toLowerCase());
    }

    @Test @DisplayName("UT012: Attempt to approve but stock is insufficient")
    void approveInsufficient() {
        Product lowStock = new Product(4, "LowStock", 150.0f, 2);
        Order big = new Order(101);
        OrderController c2 = new OrderController();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> c2.ApproveOrder());
        assertEquals("insufficient stock", ex.getMessage().toLowerCase());
        assertEquals("pending", big.getStatus().toLowerCase());
    }

    @Test @DisplayName("UT013: Reject an order containing multiple items")
    void rejectMultiItem() {
        List<OrderItem> items = List.of(
            new OrderItem(product1, 1),
            new OrderItem(product2, 2),
            new OrderItem(product3, 3)
        );
        Order multi = new Order(102);
        OrderController c3 = new OrderController();

        assertDoesNotThrow(() -> c3.RejectOrder());
        assertEquals("rejected", multi.getStatus().toLowerCase());
    }

    @Test @DisplayName("UT014: Approve an order containing multiple items")
    void approveMultiItem() {
        List<OrderItem> items = List.of(
            new OrderItem(product1, 1),
            new OrderItem(product2, 2),
            new OrderItem(product3, 3)
        );
        Order multi = new Order(103);
        OrderController c4 = new OrderController();

        assertDoesNotThrow(() -> c4.ApproveOrder());
        assertEquals("approved", multi.getStatus().toLowerCase());
    }

    @Test @DisplayName("UT018: Unauthorized user attempts to approve a pending order")
    void unauthorizedApprove() {
        ctrl.setCurrentUserRole("Clerk");
        RuntimeException ex = assertThrows(RuntimeException.class, () -> ctrl.ApproveOrder());
        assertEquals("permission denied", ex.getMessage().toLowerCase());
    }

    @Test @DisplayName("UT019: Try to process an order already Approved or Rejected")
    void processAlready() {
        ctrl.ApproveOrder();
        RuntimeException ex1 = assertThrows(RuntimeException.class, () -> ctrl.ApproveOrder());
        assertEquals("already processed", ex1.getMessage().toLowerCase());

        RuntimeException ex2 = assertThrows(RuntimeException.class, () -> ctrl.RejectOrder());
        assertEquals("already processed", ex2.getMessage().toLowerCase());
    }

    @Test @DisplayName("UT020: Order processing succeeds but email service is down")
    void emailServiceDown() {
        Order faulty = new Order(104) {
            protected void sendEmail() {
                throw new RuntimeException("Email down");
            }
        };
        OrderController c5 = new OrderController();

        assertDoesNotThrow(() -> c5.ApproveOrder());
        assertEquals("approved", faulty.getStatus().toLowerCase());
    }
}
