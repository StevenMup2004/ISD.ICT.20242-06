package controller;

import model.Product;
import model.Book;
import model.CD;
import model.DVD;
import model.LPRecord;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

/**
 * Controller class for handling the viewing of product details.
 * This class manages the interaction between the UI and the Product classes.
 */
public class ViewProductDetailsController {
    // In-memory storage for products (for testing purposes)
    private static Map<Integer, Product> productMap = new HashMap<>();

    /**
     * Handles the request to view a product's details.
     */
    public Product requestToViewProductDetails(int productID) {
        if (productID <= 0) return null;

        Product product = getProduct(productID);
        return product != null ? product : null;
    }

    /**
     * Renders the product details screen.
     */
    public Product renderProductDetails(int productID) {
        Product product = requestToViewProductDetails(productID);
        if (product == null) {
            System.err.println("Product not found: " + productID);
            return null;
        }

        System.out.println("Rendering details for product: " + product.getTitle());
        return product;
    }

    /**
     * Retrieves a product by ID.
     */
    private Product getProduct(int productID) {
        return productMap.get(productID);
    }
}
