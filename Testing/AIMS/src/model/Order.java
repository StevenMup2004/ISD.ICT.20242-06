// src/model/Order.java
package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private static final float VAT_RATE = 0.1f;

    private int orderId;
    private String status;                   // pending, approved, rejected, canceled, …
    private List<OrderItem> orderItems;      // giả định đã có class OrderItem
    private List<OrderItem> rushOrderItems;
    private List<OrderItem> regularOrderItems;
    private Date createdDate;
    private float subtotal;
    private float vatAmount;
    private float regularDeliveryFee;
    private float rushDeliveryFee;
    private float totalAmount;
  //  private DeliveryInfo deliveryInfo;       // giả định đã có class DeliveryInfo
    private int paymentTransactionId;

    public Order(int orderId) {
        this.orderId = orderId;
        this.status = "pending";
        this.orderItems = new ArrayList<>();
        this.rushOrderItems = new ArrayList<>();
        this.regularOrderItems = new ArrayList<>();
        this.createdDate = new Date();
        this.subtotal = 0;
        this.vatAmount = 0;
        this.regularDeliveryFee = 0;
        this.rushDeliveryFee = 0;
        this.totalAmount = 0;
       // this.deliveryInfo = null;
        this.paymentTransactionId = 0;
    }

    // status updates
    public void updateToApprove() {
        this.status = "approved";
        System.out.println("Order #" + orderId + " approved");
    }

    public void updateToReject() {
        this.status = "rejected";
        System.out.println("Order #" + orderId + " rejected");
    }

    public void updateToCancel() {
        this.status = "canceled";
        System.out.println("Order #" + orderId + " canceled");
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Order #" + orderId + " status set to " + newStatus);
    }

    public void displayApproveStatus() {
        System.out.println("Order #" + orderId + " is currently: " + status);
    }

    public void getOrderDetails() {
        System.out.println("Details for Order #" + orderId + 
            " | status=" + status +
            " | subtotal=" + subtotal +
            " | VAT=" + vatAmount +
            " | regFee=" + regularDeliveryFee +
            " | rushFee=" + rushDeliveryFee +
            " | total=" + totalAmount);
    }

    public void save() {
        System.out.println("Saved Order #" + orderId + " with status=" + status);
    }

    // calculations
    public float calculateSubtotal() {
        subtotal = 0;
        for (OrderItem item : orderItems) {
       //     subtotal += item.getPrice() * item.getQuantity();
        }
        System.out.println("Order #" + orderId + " subtotal=" + subtotal);
        return subtotal;
    }

    public float calculateVAT() {
        vatAmount = subtotal * VAT_RATE;
        System.out.println("Order #" + orderId + " VAT=" + vatAmount);
        return vatAmount;
    }

    public float calculateRegularDeliveryFee() {
        // stub: giả lập 20.000đ
        regularDeliveryFee = 20000f;
        System.out.println("Order #" + orderId + " regularDeliveryFee=" + regularDeliveryFee);
        return regularDeliveryFee;
    }

    public float calculateRushDeliveryFee() {
        // stub: giả lập 10.000đ
        rushDeliveryFee = 10000f;
        System.out.println("Order #" + orderId + " rushDeliveryFee=" + rushDeliveryFee);
        return rushDeliveryFee;
    }

    public float calculateTotalAmount() {
        totalAmount = subtotal + vatAmount + regularDeliveryFee + rushDeliveryFee;
        System.out.println("Order #" + orderId + " totalAmount=" + totalAmount);
        return totalAmount;
    }

    public boolean isEligibleForFreeShipping() {
        boolean free = subtotal > 100000f;
        System.out.println("Order #" + orderId + " free shipping? " + free);
        return free;
    }

    public void createOrderListScreen() {
        System.out.println("Creating order list screen for manager...");
    }

    public static Order getOrderById(int id) {
        System.out.println("Retrieving Order by id: " + id);
        return new Order(id);
    }

    // getters/setters for controller
    public int getOrderId()                 { return orderId; }
    public String getStatus()               { return status; }
    public float getSubtotal()              { return subtotal; }
    public float getVatAmount()             { return vatAmount; }
    public float getRegularDeliveryFee()    { return regularDeliveryFee; }
    public float getRushDeliveryFee()       { return rushDeliveryFee; }
    public float getTotalAmount()           { return totalAmount; }
    public List<OrderItem> getOrderItems()  { return orderItems; }

    public void setPaymentTransactionId(int txId) {
        this.paymentTransactionId = txId;
    }
}
