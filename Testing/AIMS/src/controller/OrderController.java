// src/controller/OrderController.java
package controller;

import java.util.ArrayList;
import java.util.List;

import model.Order;
import model.OrderItem;
import model.PaymentTransaction;

public class OrderController {
    private Order currentOrder;
    private final List<Order> orderList = new ArrayList<>();

    /** Tạo đơn mới và lưu vào list */
    public Order createOrder(int orderId) {
        Order o = new Order(orderId);
        orderList.add(o);
        System.out.println("Created Order #" + orderId);
        return o;
    }

    /** Helper: tìm đúng instance Order trong orderList */
    private Order findOrder(int orderId) {
        for (Order o : orderList) {
            if (o.getOrderId() == orderId) {
                return o;
            }
        }
        return null;
    }

    /** Phê duyệt đơn */
    public void approveOrder(int orderId) {
        Order o = findOrder(orderId);
        if (o != null && !checkAlreadyProcessed(orderId)) {
            currentOrder = o;
            o.updateToApprove();
            o.save();
        }
    }

    /** Từ chối đơn */
    public void rejectOrder(int orderId) {
        Order o = findOrder(orderId);
        if (o != null && !checkAlreadyProcessed(orderId)) {
            currentOrder = o;
            o.updateToReject();
            o.save();
        }
    }

    /** Hiển thị danh sách đơn đang chờ */
    public void viewOrderList() {
        System.out.println("Order list:");
        for (Order o : orderList) {
            System.out.println(" - Order #" + o.getOrderId() + " [" + o.getStatus() + "]");
        }
    }

    /** Lấy chi tiết đơn */
    public void getOrderDetails(int orderId) {
        Order o = findOrder(orderId);
        if (o != null) {
            currentOrder = o;
            o.getOrderDetails();
        }
    }

    /** Kiểm tra tồn kho:
     *  - order không tồn tại -> false
     *  - không có item -> true
     *  - bất kỳ item yêu cầu > stock -> false
     *  - ngược lại -> true
     */
    public boolean checkAvailableInventory(int orderId) {
        Order o = findOrder(orderId);
        if (o == null) return false;
        if (o.getOrderItems().isEmpty()) return true;
        for (OrderItem item : o.getOrderItems()) {
            if (!item.getProduct().checkProductAvailability(item.getQuantity())) {
                return false;
            }
        }
        return true;
    }

    /** Bước chọn huỷ (luôn true nếu order tồn tại) */
    public boolean selectCancel(int orderId) {
        Order o = findOrder(orderId);
        if (o == null) return false;
        currentOrder = o;
        System.out.println("User selected cancel for Order #" + orderId);
        return true;
    }

    /** Kiểm tra đã xử lý rồi (pending -> false; khác pending -> true) */
    public boolean checkAlreadyProcessed(int orderId) {
        Order o = findOrder(orderId);
        if (o == null) return false;
        currentOrder = o;
        boolean processed = !"pending".equals(o.getStatus());
        System.out.println("Order #" + orderId + " alreadyProcessed? " + processed);
        return processed;
    }

    /** Xác nhận và thực hiện refund */
    public void refundConfirm(PaymentTransaction tx) {
        System.out.println("Refunding transaction #" + tx.getTransactionId());
        tx.saveRefundTransaction(tx);
    }

    /** Thực hiện huỷ đơn:
     *  - nếu chưa xử lý & selectCancel == true
     *  - refund nếu tx.status == Success
     *  - updateToCancel + save()
     */
    public boolean processCancel(int orderId, PaymentTransaction tx) {
        if (!checkAlreadyProcessed(orderId) && selectCancel(orderId)) {
            if ("Success".equals(tx.getStatus())) {
                refundConfirm(tx);
            }
            currentOrder.updateToCancel();
            currentOrder.save();
            return true;
        }
        return false;
    }

    /** Lấy lại đơn hiện tại (dành cho test) */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /** Lấy toàn bộ orderList (dành cho test/ UI) */
    public List<Order> getOrderList() {
        return new ArrayList<>(orderList);
    }
}
