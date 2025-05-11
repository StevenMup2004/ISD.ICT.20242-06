// src/model/OrderItem.java
package model;

import java.util.HashMap;
import java.util.Map;

public class OrderItem {
    private String productId;
    private Product product;
    private int quantity;
    private float unitPrice;
    private float subtotal;
    private boolean isRushDeliveryEligible;
    private float weight;

    /**
     * Constructor từ Product object và số lượng.
     * Lấy unitPrice, weight và eligibility từ Product sau đó tính subtotal.
     */
    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.productId = String.valueOf(product.getProductID());
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
        this.weight = product.getWeight();
        this.isRushDeliveryEligible = product.checkEligibilityForRushDelivery();
        calculateSubtotal();
    }

    /**
     * Constructor đầy đủ thông tin.
     */
    public OrderItem(String productId,
                     int quantity,
                     float unitPrice,
                     float weight,
                     boolean isRushDeliveryEligible) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.weight = weight;
        this.isRushDeliveryEligible = isRushDeliveryEligible;
        this.product = null;
        calculateSubtotal();
    }

    /** Tính subtotal = unitPrice × quantity */
    public float calculateSubtotal() {
        this.subtotal = this.unitPrice * this.quantity;
        System.out.println("OrderItem #" + productId + " subtotal=" + subtotal);
        return subtotal;
    }

    /** Trả về tổng trọng lượng = weight × quantity */
    public float getTotalWeight() {
        float totalW = this.weight * this.quantity;
        System.out.println("OrderItem #" + productId + " totalWeight=" + totalW);
        return totalW;
    }

    /** Trả về eligibility flag */
    public boolean checkEligibilityForRushDelivery() {
        return isRushDeliveryEligible;
    }

    /**
     * Chuyển thành DTO (Map) để hiển thị hoặc API
     */
    public Map<String,Object> toOrderItemDTO() {
        Map<String,Object> dto = new HashMap<>();
        dto.put("productId", productId);
        if (product != null) {
            dto.put("productTitle", product.getTitle());
        }
        dto.put("quantity", quantity);
        dto.put("unitPrice", unitPrice);
        dto.put("subtotal", subtotal);
        dto.put("weight", weight);
        dto.put("isRushDeliveryEligible", isRushDeliveryEligible);
        return dto;
    }

    // --- Getters ---

    /** Trả về productId dưới dạng String */
    public String getProductId() {
        return productId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public boolean isRushDeliveryEligible() {
        return isRushDeliveryEligible;
    }

    public float getWeight() {
        return weight;
    }
}
