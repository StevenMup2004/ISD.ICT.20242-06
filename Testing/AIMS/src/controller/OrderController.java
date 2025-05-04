// src/controller/OrderController.java
package controller;

import java.util.ArrayList;
import java.util.List;
import service.DeliveryService;
import service.EmailService;
import model.Order;
import model.OrderItem;
import model.PaymentTransaction;
import model.Cart;
import model.CartItem;
import model.DeliveryInfo;
import model.Product;

public class OrderController {
    private Order currentOrder;
    private final List<Order> orderList = new ArrayList<>();
    private DeliveryService deliveryService;
    private EmailService emailService;

    public OrderController() {
        this.deliveryService = new DeliveryService();
        this.emailService = new EmailService();
    }

    // For testing purposes to inject mock services
    public OrderController(DeliveryService deliveryService, EmailService emailService) {
        this.deliveryService = deliveryService;
        this.emailService = emailService;
    }

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

    /**
     * Main method for placing an order from a cart
     * Performs validations, creates order and processes it
     * @param cart Customer's shopping cart
     * @param deliveryInfo Delivery information for the order
     * @return The created order if successful, null otherwise
     */
    public Order placeOrder(Cart cart, DeliveryInfo deliveryInfo) {
        // Validate cart and delivery information
        if (!validateCart(cart)) {
            System.out.println("Order failed: Cart empty");
            return null;
        }

        if (!validateDeliveryInfo(deliveryInfo)) {
            System.out.println("Order failed: Invalid delivery information");
            return null;
        }
        
        if (!checkInventory(cart.getCartItemsList())) {
            System.out.println("Order failed: Insufficient inventory");
            return null;
        }
        
        // Create and process the order
        Order order = createOrder(cart, deliveryInfo);
        if (order != null) {
            processOrder(order);
            
            // Send confirmation email
            if (deliveryInfo.getEmail() != null && !deliveryInfo.getEmail().isEmpty()) {
                emailService.sendOrderConfirmation(deliveryInfo.getEmail(), order);
            }
            
            // Clear cart after successful order
            clearCart(cart);
            return order;
        }
        
        return null;
    }
    
    /**
     * Validates that the cart is not empty
     * @param cart Cart to validate
     * @return true if cart contains items, false otherwise
     */
    public boolean validateCart(Cart cart) {
        if (cart == null || cart.isEmpty()) {
            System.out.println("Cart validation failed: Cart is empty");
            return false;
        }
        return true;
    }
    
    /**
     * Validates inventory availability for all items in the cart
     * @param items List of cart items to check
     * @return true if all items have available stock, false otherwise
     */
    public boolean checkInventory(List<CartItem> items) {
        if (items == null || items.isEmpty()) {
            return true;
        }
        
        for (CartItem item : items) {
            Product product = item.getProduct();
            if (product != null && !product.checkProductAvailability(item.getQuantity())) {
                System.out.println("Inventory check failed for product ID: " + item.getProductID() + 
                    ", requested: " + item.getQuantity());
                return false;
            }
        }
        return true;
    }
    
    /**
     * Validates delivery information is complete and valid
     * @param info Delivery information to validate
     * @return true if delivery info is valid, false otherwise
     */
    public boolean validateDeliveryInfo(DeliveryInfo info) {
        if (info == null) {
            System.out.println("Delivery info validation failed: Info is null");
            return false;
        }
        
        if (info.getRecipientName() == null || info.getRecipientName().trim().isEmpty()) {
            System.out.println("Delivery info validation failed: Missing recipient name");
            return false;
        }
        
        if (info.getEmail() == null || info.getEmail().trim().isEmpty() || 
            !info.getEmail().contains("@")) {
            System.out.println("Delivery info validation failed: Invalid email");
            return false;
        }
        
        if (info.getPhoneNumber() == null || info.getPhoneNumber().trim().isEmpty()) {
            System.out.println("Delivery info validation failed: Missing phone number");
            return false;
        }
        
        if (info.getProvince() == null || info.getProvince().trim().isEmpty()) {
            System.out.println("Delivery info validation failed: Missing province");
            return false;
        }
        
        return true;
    }
    
    /**
     * Calculates delivery fee based on order weight and location
     * Applies free shipping discount if eligible
     * @param order Order to calculate delivery fee for
     * @return The calculated delivery fee
     */
    public float calculateDeliveryFee(Order order) {
        // Calculate total weight of all order items
        float totalWeight = 0;
        for (OrderItem item : order.getOrderItems()) {
            totalWeight += item.getTotalWeight();
        }
        
        // Get delivery location from order
        String location = "Other"; // Default location
        if (order.getDeliveryInfo() != null) {
            String province = order.getDeliveryInfo().getProvince();
            if ("Hanoi".equalsIgnoreCase(province) || "Ha Noi".equalsIgnoreCase(province) ||
                "HCMC".equalsIgnoreCase(province) || "Ho Chi Minh".equalsIgnoreCase(province)) {
                location = "Inner";
            }
        }
        
        // Calculate fee
        float fee = deliveryService.calculateFee(totalWeight, location, order.getSubtotal());
        
        return fee;
    }
    
    /**
     * Calculates the total order amount including subtotal, VAT, and delivery fee
     * @param order Order to calculate total for
     * @return The calculated total amount
     */
    public float calculateOrderTotal(Order order) {
        // Calculate subtotal from order items
        float subtotal = order.calculateSubtotal();
        
        // Calculate VAT (10% of subtotal)
        float vat = order.calculateVAT();
        
        // Calculate delivery fee
        float deliveryFee = calculateDeliveryFee(order);
        
        // Calculate and set total amount
        float totalAmount = subtotal + vat + deliveryFee;
        order.setTotalAmount(totalAmount);
        
        return totalAmount;
    }
    
    /**
     * Creates a new order from cart items and delivery info
     * @param cart Cart containing items to order
     * @param info Delivery information for the order
     * @return The created order
     */
    public Order createOrder(Cart cart, DeliveryInfo info) {
        // Create a new order with a unique ID
        int orderId = generateOrderId();
        Order order = new Order(orderId);
        
        // Set delivery info
        order.setDeliveryInfo(info);
        
        // Convert cart items to order items
        for (CartItem cartItem : cart.getCartItemsList()) {
            Product product = cartItem.getProduct();
            if (product != null) {
                OrderItem orderItem = new OrderItem(product, cartItem.getQuantity());
                order.addOrderItem(orderItem);
            }
        }
        
        // Calculate order totals
        calculateOrderTotal(order);
        
        // Set initial status
        order.updateStatus("Pending Processing");
        
        // Add to order list
        orderList.add(order);
        currentOrder = order;
        
        return order;
    }
    
    /**
     * Processes the order by updating inventory and saving the order
     * @param order Order to process
     * @return true if processing was successful
     */
    public boolean processOrder(Order order) {
        if (order == null) return false;
        
        // Update inventory for each order item
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            if (product != null) {
                product.updateStock(product.getProductID(), -item.getQuantity());
            }
        }
        
        // Save order
        order.save();
        return true;
    }
    
    /**
     * Clears the cart after successful order placement
     * @param cart Cart to clear
     */
    public void clearCart(Cart cart) {
        if (cart != null) {
            cart.empty();
            System.out.println("Cart cleared after successful order placement");
        }
    }
    
    /**
     * Generates a unique order ID
     * In a real application, this would be more sophisticated
     * @return A new unique order ID
     */
    private int generateOrderId() {
        // Simple implementation that uses timestamp + random number
        return (int)(System.currentTimeMillis() % 100000) + 
               (int)(Math.random() * 1000);
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
