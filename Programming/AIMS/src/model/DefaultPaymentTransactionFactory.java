package model;


public class DefaultPaymentTransactionFactory implements PaymentTransactionFactory {
    @Override
    public PaymentTransaction create(float amount, String method, int orderId) {
        return PaymentTransaction.createTransaction(amount, method, orderId);
    }
}