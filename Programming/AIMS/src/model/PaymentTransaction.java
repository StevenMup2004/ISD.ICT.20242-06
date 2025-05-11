/*
 * Class Purpose: Represents a payment transaction in the AIMS system, including its creation, status update, and persistence behavior.
 * 
 * Cohesion Level: Logical Cohesion
 * 
 * SRP Violation: Yes
 * Reason: This class handles multiple unrelated responsibilities:
 *   - Business entity logic (storing transaction data: id, amount, date, status)
 *   - Business rules (e.g., evaluatePaymentResult logic tied to VNPay)
 *   - Persistence responsibilities (save, saveRefundTransaction)
 * 
 * Recommendation:   Split responsibilities:
 *   - Keep this class as a pure data model (POJO) for transaction attributes.
 *   - Move persistence logic (e.g., `save()`, `saveRefundTransaction`) to a `PaymentTransactionRepository` or `PersistenceService`.
 *   - Move business logic (e.g., `evaluatePaymentResult`) to a separate `PaymentService`.
 */



package model;

import java.util.Date;

public class PaymentTransaction {
 private static int nextId = 1;

 private int transactionId;
 private float amount;
 private String paymentMethod;
 private Date transactionDate;
 private String status;        // Pending, Success, Failed, Refunded
 private String content;
 private int orderId;

 public PaymentTransaction(float amount, String paymentMethod, int orderId) {
     this.transactionId = nextId++;
     this.amount = amount;
     this.paymentMethod = paymentMethod;
     this.transactionDate = new Date();
     this.status = "Pending";
     this.orderId = orderId;
 }

 /** Tạo transaction mới */
 public static PaymentTransaction createTransaction(float amount, String method, int orderId) {
     return new PaymentTransaction(amount, method, orderId);
 }

 /** Giả lập đánh giá kết quả từ VNPay */
 public boolean evaluatePaymentResult() {
     this.status = "Success";
     return true;
 }

 /** Giả lập lưu transaction vào DB */
 public void save() {
     // persist logic...
     System.out.println("Saved transaction #" + transactionId + " status=" + status);
 }

 /** Trả về thông tin transaction dạng chuỗi */
 public String transactionInfo() {
     return "Transaction #" + transactionId 
         + " [amount=" + amount 
         + ", method=" + paymentMethod 
         + ", status=" + status + "]";
 }

 /** Giả lập lưu refund */
 public void saveRefundTransaction(PaymentTransaction refundTx) {
     refundTx.status = "Refunded";
     System.out.println("Saved refund for tx #" + refundTx.transactionId);
 }

 // getters
 public int getTransactionId()        { return transactionId; }
 public String getStatus()            { return status; }
 public float getAmount()             { return amount; }
 public String getPaymentMethod()     { return paymentMethod; }
 public int getOrderId()              { return orderId; }
}
