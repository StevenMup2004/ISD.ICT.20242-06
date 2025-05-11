// Pham Thanh Nam 20225989 - Place Order
/*
 * Class Purpose: Represents a customer's invoice, including price breakdowns, delivery fees, and status.
 * 
 * Cohesion Level: Logical Cohesion
 * 
 * SRP Violation: Yes
 * Reason:
 *   - This class mixes multiple responsibilities:
 *     1. Business entity/data storage: holds invoice info (ID, date, subtotal, VAT, etc.)
 *     2. Business logic: price calculation (`calculateSubtotal`, `calculateVAT`, etc.)
 *     3. Persistence logic: `save()` simulates writing to a DB
 *     4. Presentation logic: `generateInvoiceDetails()` prepares formatted data
 * 
 * Recommendation:
 *   - Split responsibilities into dedicated classes:
 *     1. Keep `Invoice` as a pure data model (POJO).
 *     2. Move calculation logic to an `InvoiceCalculator` or `InvoiceService`.
 *     3. Move `save()` to `InvoiceRepository`.
 *     4. Optionally, separate formatting into an `InvoicePresenter` if complex.
 */


package model;


import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Invoice {
 private int invoiceId;
 private Date issueDate;
 private List<OrderItem> productList = new ArrayList<>();
 private float subtotal;
 private float VAT;
 private float totalWithVAT;
 private float regularDeliveryFee;
 private float rushDeliveryFee;
 private float totalAmount;    // bao gồm VAT + phí
 private String paymentMethod = "Credit Card";
 private String status = "unpaid";
// private DeliveryInfo recipientInfo;

 public Invoice(int invoiceId) {
     this.invoiceId = invoiceId;
     this.issueDate = new Date();
 }

 /** Tính tổng giá sản phẩm */
 public void calculateSubtotal() {
     subtotal = 0;
     for (OrderItem item : productList) {
        subtotal += item.getUnitPrice() * item.getQuantity();
     }
 }

 /** Tính VAT = 10% subtotal */
 public void calculateVAT() {
     VAT = subtotal * 0.1f;
 }

 /** Tính tổng có VAT */
 public void calculateTotalWithVAT() {
     totalWithVAT = subtotal + VAT;
 }

 /** Tính phí vận chuyển (ví dụ cố định) */
 public void calculateDeliveryFees() {
     regularDeliveryFee = 20000;  // ví dụ
     rushDeliveryFee = 10000;     // ví dụ
 }

 /** Tính tổng cuối cùng */
 public void calculateTotalAmount() {
     totalAmount = totalWithVAT + regularDeliveryFee + rushDeliveryFee;
 }

 /** Sinh bản chi tiết invoice */
 public Map<String, Object> generateInvoiceDetails() {
     Map<String,Object> m = new HashMap<>();
     m.put("invoiceId", invoiceId);
     m.put("subtotal", subtotal);
     m.put("VAT", VAT);
     m.put("totalWithVAT", totalWithVAT);
     m.put("deliveryFees", regularDeliveryFee + rushDeliveryFee);
     m.put("totalAmount", totalAmount);
     return m;
 }

 /** Lưu invoice */
 public void save() {
     System.out.println("Saved invoice #" + invoiceId + " status=" + status);
 }

/**
  * Adds an OrderItem to the invoice's product list
  * @param item The OrderItem to add
  */
 public void addOrderItem(OrderItem item) {
     if (item != null) {
         productList.add(item);
     }
 }
 
 /**
  * Sets the order items from an order
  * @param orderItems List of order items to set
  */
 public void setOrderItems(List<OrderItem> orderItems) {
     if (orderItems != null) {
         this.productList.clear();
         this.productList.addAll(orderItems);
     }
 }
 
 /**
  * Sets delivery fees from an order
  * @param regularFee Regular delivery fee
  * @param rushFee Rush delivery fee
  */
 public void setDeliveryFees(float regularFee, float rushFee) {
     this.regularDeliveryFee = regularFee;
     this.rushDeliveryFee = rushFee;
 }
 
 /**
  * Gets the product list (order items)
  * @return List of order items
  */
 public List<OrderItem> getProductList() {
     return productList;
 }
 
 /**
  * Creates an invoice from an order
  * @param order The order to create invoice from
  * @return The created invoice
  */
 public static Invoice createFromOrder(Order order) {
     if (order == null) return null;
     
     Invoice invoice = new Invoice(generateInvoiceId());
     invoice.setOrderItems(order.getOrderItems());
     invoice.setDeliveryFees(order.getRegularDeliveryFee(), order.getRushDeliveryFee());
     
     // Calculate all invoice totals
     invoice.calculateSubtotal();
     invoice.calculateVAT();
     invoice.calculateTotalWithVAT();
     invoice.calculateTotalAmount();
     
     return invoice;
 }
 
 /**
  * Generates a unique invoice ID
  * @return A new unique invoice ID
  */
 private static int generateInvoiceId() {
     return (int)(System.currentTimeMillis() % 100000);
 }

 // getter cho controller
 public int getInvoiceId()            { return invoiceId; }
 public float getTotalAmount()        { return totalAmount; }
 public String getPaymentMethod()     { return paymentMethod; }
 public void setStatus(String status) { this.status = status; }
}
