package model;



public interface PaymentTransactionFactory {
 PaymentTransaction create(float amount, String method, int orderId);
}
