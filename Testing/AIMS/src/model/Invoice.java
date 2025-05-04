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
        // subtotal += item.getPrice() * item.getQuantity();
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

 // getter cho controller
 public int getInvoiceId()            { return invoiceId; }
 public float getTotalAmount()        { return totalAmount; }
 public String getPaymentMethod()     { return paymentMethod; }
 public void setStatus(String status) { this.status = status; }
}
