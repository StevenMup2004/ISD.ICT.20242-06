// Pham Thanh Nam 20225989 - Place Order
/* Cohesion Level: Functional Cohesion - This class is focused specifically on the place order
 * functionality with methods that all contribute to this single responsibility.
 * 
 * SRP Violation: No - The class has a single responsibility of managing the place order process.
 * It delegates more specific tasks to other classes/services rather than implementing them directly.
 * 
 * Suggested Improvement (if any): None - The class follows SRP by delegating specific tasks
 * to specialized services and maintains high cohesion by focusing on order placement only.
 */
package controller;

import model.Cart;
import model.DeliveryInfo;
import model.Invoice;
import model.Order;
import service.DeliveryService;
import service.EmailService;

public class PlaceOrderController {
    private OrderController orderController;
    
    public PlaceOrderController() {
        this.orderController = new OrderController();
    }
    
    // Constructor for testing with mock services
    public PlaceOrderController(DeliveryService deliveryService, EmailService emailService) {
        this.orderController = new OrderController(deliveryService, emailService);
    }
    
    /**
     * Main method for placing a new order
     * Delegates to OrderController but focuses specifically on the place order use case
     * 
     * @param cart Customer's shopping cart
     * @param deliveryInfo Delivery information for the order
     * @return The created order if successful, null otherwise
     */
    public Order placeOrder(Cart cart, DeliveryInfo deliveryInfo) {
        // Validate inputs
        if (!validateOrderInputs(cart, deliveryInfo)) {
            return null;
        }
        
        // Delegate to OrderController
        Order order = orderController.placeOrder(cart, deliveryInfo);
        
        // Generate invoice if order was created successfully
        if (order != null) {
            Invoice invoice = generateInvoice(order);
            System.out.println("Generated invoice #" + invoice.getInvoiceId() + " for order #" + order.getOrderId());
            invoice.save();
        }
        
        return order;
    }
    
    /**
     * Generates an invoice for a successfully placed order
     * 
     * @param order The order to generate invoice for
     * @return The generated invoice
     */
    public Invoice generateInvoice(Order order) {
        Invoice invoice = Invoice.createFromOrder(order);
        return invoice;
    }
    
    /**
     * Validates all inputs before placing an order
     * 
     * @param cart Customer's shopping cart
     * @param deliveryInfo Delivery information for the order
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateOrderInputs(Cart cart, DeliveryInfo deliveryInfo) {
        return orderController.validateCart(cart) && 
               orderController.validateDeliveryInfo(deliveryInfo) &&
               orderController.checkInventory(cart.getCartItemsList());
    }
    
    /**
     * Calculates the delivery fee for a given order
     * 
     * @param order Order to calculate delivery fee for
     * @return The calculated delivery fee
     */
    public float calculateDeliveryFee(Order order) {
        return orderController.calculateDeliveryFee(order);
    }
    
    /**
     * Calculates the total order amount
     * 
     * @param order Order to calculate total for
     * @return The calculated total amount
     */
    public float calculateOrderTotal(Order order) {
        return orderController.calculateOrderTotal(order);
    }
}
