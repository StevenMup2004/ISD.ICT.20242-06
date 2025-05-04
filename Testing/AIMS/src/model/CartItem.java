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
        // This is a simplified implementation
        // In a real system, this would be handled by a database or ID generator
        return (int) (Math.random() * 10000);
    }
}