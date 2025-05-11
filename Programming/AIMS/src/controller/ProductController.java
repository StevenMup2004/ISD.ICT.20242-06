// Le Dai Lam - 20225982 - Add/Update product
/* Cohesion Level: Communicational Cohesion
 * 
 * SRP Violation: Yes - Handles multiple concerns: 
 * 1) Product data validation 
 * 2) Business logic coordination (create/update) 
 * 3) Error handling and routing. These concerns should be separated into different classes/services.
 * 
 * Solution: 
 * 1) Extract a ProductValidationService.
 * 2) Keep the controller focused only on calling services like ProductService.create() and update() after validation.
 */
package controller;

import java.util.*;

import model.Product;
import model.HistoryUpdate;

public class ProductController {
    private Map<Integer, Product> productRepo = new HashMap<>();
    private HistoryUpdate historyService = new HistoryUpdate();

    public ProductController() {}

    public void CreateProduct(Product p) {
        if (productRepo.containsKey(p.getProductID())) throw new RuntimeException("Duplicate productID");
        if (p.getTitle() == null || p.getCategory() == null) throw new RuntimeException("Missing required field");
        productRepo.put(p.getProductID(), p);
        historyService.recordUpdate(p.getProductID(), false);
    }

    public void UpdateProduct(int productID, Map<String, Object> changes) {
        Product p = productRepo.get(productID);
        if (p == null) throw new RuntimeException("Product not found");
        if (changes.containsKey("price")) {
            float newPrice = (Float)changes.get("price");
            if (newPrice < p.getValue()*0.3 || newPrice > p.getValue()*1.5) throw new RuntimeException("Price out of range");
            int count = historyService.getUpdateHistory(productID).stream().filter(h -> h.isUpdatePrice()).mapToInt(h->1).sum();
            if (count >= 2) throw new RuntimeException("Daily price-update limit exceeded");
            p.setPrice(newPrice);
            historyService.recordUpdate(productID, true);
        }
        if (changes.containsKey("quantity")) {
            Object q = changes.get("quantity");
            if (!(q instanceof Integer)) throw new RuntimeException("Invalid quantity format");
            int newQty = (Integer)q;
            if (newQty < 0) throw new RuntimeException("Negative quantity");
            p.setQuantity(newQty);
            historyService.recordUpdate(productID, false);
        }
    }

    public boolean ValidateProduct(int productID) {
        Product p = productRepo.get(productID);
        return p != null
            && p.getTitle() != null
            && p.getPrice() >= p.getValue()*0.3
            && p.getPrice() <= p.getValue()*1.5;
    }
}
