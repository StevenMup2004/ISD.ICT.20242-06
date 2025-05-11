// src/controller/PayOrderController.java

/*
 * Class Purpose: Handles the process of paying an order via VNPay and updating invoice/payment states.
 * 
 * Cohesion Level: Logical Cohesion
 * 
 * SRP Violation: Yes
 * Reason:
 *   - This controller manages multiple distinct responsibilities:
 *     1. Payment orchestration (main logic of `payOrder()`) → OK
 *     2. Handles VNPay-specific error handling (`try-catch`, fallback logic) → borderline OK
 *     3. Updates and saves both Invoice and PaymentTransaction → crosses into persistence layer
 *     4. Displays error and diagnostic messages (`System.out`, `System.err`) → presentation concern
 *     5. Provides logging/save hooks (`savePaymentResult`) → unrelated to core controller logic
 * 
 * Recommendation:
 *   - Extract responsibilities:
 *     1. Keep this class focused on orchestration (controller role only).
 *     2. Move invoice/payment save logic into `InvoiceService` or `PaymentService`.
 *     3. Delegate exception handling and fallback logic to a helper or error handler if complex.
 *     4. Remove presentation logic (`System.out/err`) or abstract it behind a `Logger`.
 */





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
