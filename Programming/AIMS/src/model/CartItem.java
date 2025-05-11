// Pham Thanh Nam 20225989 - Place Order
/*
 * Cohesion Level: Functional Cohesion
 * 
 * SRP Violation: No
 * This class has a clear single responsibility - representing and managing 
 * a single cart item. All properties and methods are focused on this purpose.
 * 
 * Improvement: No significant improvements needed.
 */

package model;


public class CartItem {
    private int cartItemID;
    private int productID;
    private int cartID;
    private int quantity;
    private float price;
    
    
    public CartItem(int productID, int quantity, float price, int cartID) {
        this.cartItemID = generateCartItemID();
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.cartID = cartID;
    }
    
    public CartItem(int cartItemID, int productID, int cartID, int quantity, float price) {
        this.cartItemID = cartItemID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.cartID = cartID;
    }
    public int getCartItemID() {
        return cartItemID;
    }
    
   
    public int getProductID() {
        return productID;
    }
    
 
    public int getCartID() {
        return cartID;
    }
    
    
    public int getQuantity() {
        return quantity;
    }
    
    
    public float getPrice() {
        return price;
    }
    
    
    public void setPrice(float price) {
        this.price = price;
    }
    
   
    public boolean changeQuantityOfProduct(int productID, int newQuantity) {
        if (this.productID != productID || newQuantity <= 0) {
            return false;
        }
        this.quantity = newQuantity;
        return true;
    }
    
    private int generateCartItemID() {
        return (int) (Math.random() * 10000);
    }

        /**
     * Gets the associated Product for this cart item
     * Retrieves the product from the database or data source based on productID
     * @return Product object associated with this cart item
     */
    public Product getProduct() {
        // In a real implementation, this would fetch the product from a repository
        // For now, we'll create a simple stub that returns a basic product
        return new Product(productID, "Product " + productID, "", price, 0.5f);
    }
    
    /**
     * Constructor that takes a Product object and quantity
     * This allows easy creation of cart items directly from products
     * @param product The product to add to cart
     * @param quantity The quantity to add
     */
    public CartItem(Product product, int quantity) {
        this.cartItemID = generateCartItemID();
        this.productID = product.getProductID();
        this.quantity = quantity;
        this.price = product.getPrice();
        this.cartID = 0; // Default cart ID, should be set by Cart when added
    }
}

