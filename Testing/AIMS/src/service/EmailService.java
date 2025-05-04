package service;

import model.Order;
import model.OrderItem;
import java.util.Date;

/**
 * Service for sending emails related to orders
 */
public class EmailService {
    
    /**
     * Sends an order confirmation email to the customer
     * 
     * @param email Customer's email address
     * @param order The order to send confirmation for
     * @return true if email was sent successfully
     */
    public boolean sendOrderConfirmation(String email, Order order) {
        if (email == null || email.trim().isEmpty() || order == null) {
            System.out.println("Failed to send email: Invalid email or order");
            return false;
        }
        
        try {
            // In a real implementation, this would use JavaMail or similar API
            // For this demo, we'll just simulate sending an email
            
            String emailContent = generateOrderEmailContent(order);
            System.out.println("Sending order confirmation email to: " + email);
            System.out.println("Email content: " + emailContent);
            
            // Simulate successful email sending
            System.out.println("Email sent successfully to: " + email);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Generates the email content for an order confirmation
     * 
     * @param order The order to generate content for
     * @return The generated email content
     */
    public String generateOrderEmailContent(Order order) {
        StringBuilder content = new StringBuilder();
        
        // Email header
        content.append("Dear ").append(order.getDeliveryInfo().getRecipientName()).append(",\n\n");
        content.append("Thank you for your order! Here are the details of your purchase:\n\n");
        
        // Order summary
        content.append("Order #: ").append(order.getOrderId()).append("\n");
        content.append("Order Date: ").append(new Date().toString()).append("\n");
        content.append("Order Status: ").append(order.getStatus()).append("\n\n");
        
        // Order items
        content.append("ORDER ITEMS:\n");
        content.append("--------------------------------------------------\n");
        for (OrderItem item : order.getOrderItems()) {
            content.append(item.getQuantity())
                  .append(" x ")
                  .append(item.getProduct() != null ? item.getProduct().getTitle() : "Product")
                  .append(" - ")
                  .append(String.format("%,.0f", item.getSubtotal()))
                  .append(" VND\n");
        }
        content.append("--------------------------------------------------\n\n");
        
        // Order totals
        content.append("Subtotal: ").append(String.format("%,.0f", order.getSubtotal())).append(" VND\n");
        content.append("VAT (10%): ").append(String.format("%,.0f", order.getVatAmount())).append(" VND\n");
        content.append("Shipping: ").append(String.format("%,.0f", order.getRegularDeliveryFee())).append(" VND\n");
        content.append("Total: ").append(String.format("%,.0f", order.getTotalAmount())).append(" VND\n\n");
        
        // Delivery information
        content.append("DELIVERY INFORMATION:\n");
        content.append("Recipient: ").append(order.getDeliveryInfo().getRecipientName()).append("\n");
        content.append("Phone: ").append(order.getDeliveryInfo().getPhoneNumber()).append("\n");
        content.append("Province: ").append(order.getDeliveryInfo().getProvince()).append("\n");
        content.append("District: ").append(order.getDeliveryInfo().getDistrict()).append("\n\n");
        
        // Footer
        content.append("If you have any questions, please contact our customer service.\n\n");
        content.append("Thank you for shopping with AIMS!");
        
        return content.toString();
    }
}
