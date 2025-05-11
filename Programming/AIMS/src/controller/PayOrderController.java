// src/controller/PayOrderController.java
package controller;

import model.*;
import model.PaymentTransaction;

public class PayOrderController {
    private Invoice currentInvoice;
    private PaymentTransaction paymentTransaction;
    private final VNPay vnpay;
    private final PaymentTransactionFactory txFactory;
    public PayOrderController() {
        this(new VNPay(), new DefaultPaymentTransactionFactory());
    }

    // Constructor để test có thể inject fake VNPay và fake factory
    public PayOrderController(VNPay vnpay, PaymentTransactionFactory factory) {
        this.vnpay = vnpay;
        this.txFactory = factory;
    }

    public boolean payOrder(Invoice invoice) {
        if (invoice == null) return false;

        // 1) Tạo transaction
        PaymentTransaction tx = txFactory.create(
            invoice.getTotalAmount(),
            invoice.getPaymentMethod(),
            invoice.getInvoiceId()
        );

        try {
            // 2) Gửi tới VNPay
            vnpay.processByVNPay(tx);

            // 3) Đánh giá kết quả
            boolean success = tx.evaluatePaymentResult();
            tx.save();

            if (success) {
                // 4) Cập nhật invoice
                invoice.setStatus("paid");
                invoice.save();
                return true;
            } else {
                // payment thất bại
                invoice.setStatus("unpaid");
                invoice.save();
                return false;
            }

        } catch (VNPay.VNPayConnectionException | VNPay.VNPayResponseException e) {
            // 5) Xử lý exception
            invoice.setStatus("unpaid");
            invoice.save();
            return false;
        }
    }

   
    /** Called when payment fails for any reason */
    public void paymentFailed() {
        System.err.println("Payment failed for invoice #" 
            + (currentInvoice != null ? currentInvoice.getInvoiceId() : "unknown"));
        // Roll back or update invoice status if you like:
        if (currentInvoice != null) {
            currentInvoice.setStatus("unpaid");
            currentInvoice.save();
        }
    }

    /**
     * Records the final payment result (e.g. in a log or separate table).
     * @return true if save was successful
     */
    public boolean savePaymentResult() {
        System.out.println("Saving payment result for invoice #" 
            + currentInvoice.getInvoiceId());
        // Implement actual persistence logic here
        return true;
    }
}
