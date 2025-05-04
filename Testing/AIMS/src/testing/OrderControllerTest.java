
// Nguyen Minh Khoi - 20226050 - Cancel Order
package testing;

import controller.OrderController;
import model.Order;
import model.OrderItem;
import model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OrderController – Inventory & Processed Tests")
class OrderControllerTest {

    private OrderController ctl;

    @BeforeEach
    void setUp() {
        ctl = new OrderController();
    }

    // ===== checkAvailableInventory(...) =====

    @Test
    @DisplayName("TC_INV_01 – tồn tại order, không có item → true")
    void testCheckAvailableInventory_OrderExistsNoItems() {
        Order o = ctl.createOrder(1);
        assertTrue(
            ctl.checkAvailableInventory(o.getOrderId()),
            "Order mới chưa có item → đủ hàng → true"
        );
    }

    @Test
    @DisplayName("TC_INV_02 – order không tồn tại → false")
    void testCheckAvailableInventory_NonExistentOrder() {
        assertFalse(
            ctl.checkAvailableInventory(999),
            "OrderId không tồn tại → false"
        );
    }

    @Test
    @DisplayName("TC_INV_03 – tất cả items đủ stock → true")
    void testCheckAvailableInventory_AllAvailable() {
        Order o = ctl.createOrder(2);
        Product p = new Product(100, "Test", 50f, 5);    // stock = 5
        o.getOrderItems().add(new OrderItem(p, 3));     // yêu cầu 3
        assertTrue(
            ctl.checkAvailableInventory(o.getOrderId()),
            "3 ≤ 5 → đủ hàng → true"
        );
    }

    @Test
    @DisplayName("TC_INV_04 – có item thiếu stock → false")
    void testCheckAvailableInventory_InsufficientStock() {
        Order o = ctl.createOrder(3);
        Product p = new Product(200, "Test2", 50f, 2);   // stock = 2
        o.getOrderItems().add(new OrderItem(p, 5));     // yêu cầu 5
        assertFalse(
            ctl.checkAvailableInventory(o.getOrderId()),
            "5 > 2 → thiếu hàng → false"
        );
    }

    // ===== checkAlreadyProcessed(...) =====

    @Test
    @DisplayName("TC_PROC_01 – order mới (pending) → false")
    void testCheckAlreadyProcessed_WhenPending() {
        Order o = ctl.createOrder(10);
        assertFalse(
            ctl.checkAlreadyProcessed(o.getOrderId()),
            "pending → chưa xử lý → false"
        );
    }

    @Test
    @DisplayName("TC_PROC_02 – order đã cancel → true")
    void testCheckAlreadyProcessed_WhenCanceled() {
        Order o = ctl.createOrder(11);
        o.updateToCancel();  // chuyển status thành "canceled"
        assertTrue(
            ctl.checkAlreadyProcessed(o.getOrderId()),
            "canceled → đã xử lý → true"
        );
    }

    @Test
    @DisplayName("TC_PROC_03 – chuyển sang order khác → load đúng order mới")
    void testCheckAlreadyProcessed_SwitchOrder() {
        Order o1 = ctl.createOrder(12);
        assertFalse(ctl.checkAlreadyProcessed(o1.getOrderId()));

        Order o2 = ctl.createOrder(13);
        boolean processed = ctl.checkAlreadyProcessed(o2.getOrderId());

        assertFalse(processed, "Order 13 mới (pending) → false");
        assertEquals(
            13,
            ctl.getCurrentOrder().getOrderId(),
            "currentOrder phải là Order có ID = 13"
        );
    }

    @Test
    @DisplayName("TC_PROC_04 – gọi nhiều lần, giữa chừng cancel → lần sau true")
    void testCheckAlreadyProcessed_MultipleCalls() {
        Order o = ctl.createOrder(14);
        assertFalse(ctl.checkAlreadyProcessed(o.getOrderId()));

        o.updateToCancel();

        assertTrue(
            ctl.checkAlreadyProcessed(o.getOrderId()),
            "sau cancel → true"
        );
    }
}
