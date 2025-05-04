package controller;

import model.Product;
import model.Book;
import model.CD;
import model.DVD;
import model.LPRecord;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;


public class ViewProductDetailsController {
    private static Map<Integer, Product> productMap = new HashMap<>();

    public Product requestToViewProductDetails(int productID) {
        if (productID <= 0) return null;

        Product product = getProduct(productID);
        return product != null ? product : null;
    }

    
    public Product renderProductDetails(int productID) {
        Product product = requestToViewProductDetails(productID);
        if (product == null) {
            System.err.println("Product not found: " + productID);
            return null;
        }

        System.out.println("Rendering details for product: " + product.getTitle());
        return product;
    }

    
    private Product getProduct(int productID) {
        return productMap.get(productID);
    }
}
