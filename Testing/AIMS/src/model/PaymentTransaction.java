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
