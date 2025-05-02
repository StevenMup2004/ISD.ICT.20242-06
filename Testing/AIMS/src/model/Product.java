package model;

import java.util.Date;

public class Product {
    protected int productID;
    protected String title;
    protected String category;
    protected float value;
    protected float price;
    protected int quantity;
    protected String description;
    protected String imageURL;
    protected String barCode;
    protected Date warehouseEntryDate;
    protected String dimensions;
    protected float weight;
    protected String warehouseProvince;
    protected String warehouseDistrict;
    protected String warehouseAddress;
    
    
    public Product(int productID, String title, float price, int quantity) {
        this.productID = productID;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }
    
    public Product(int productID, String title, String category, float value, float price,
                   int quantity, String description, String imageURL, String barCode,
                   Date warehouseEntryDate, String dimensions, float weight,
                   String warehouseProvince, String warehouseDistrict, String warehouseAddress) {
        this.productID = productID;
        this.title = title;
        this.category = category;
        this.value = value;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.imageURL = imageURL;
        this.barCode = barCode;
        this.warehouseEntryDate = warehouseEntryDate;
        this.dimensions = dimensions;
        this.weight = weight;
        this.warehouseProvince = warehouseProvince;
        this.warehouseDistrict = warehouseDistrict;
        this.warehouseAddress = warehouseAddress;
    }
    
    
    public int getProductID() {
        return productID;
    }
    
    
    public String getTitle() {
        return title;
    }
    
    
    public String getCategory() {
        return category;
    }
    
    
    public float getValue() {
        return value;
    }
    
    public float getPrice() {
        return price;
    }
    
    
    public int getQuantity() {
        return quantity;
    }
    
    
    public String getDescription() {
        return description;
    }
    
   
    public String getImageURL() {
        return imageURL;
    }
    
    
    public String getBarCode() {
        return barCode;
    }
    
    
    public Date getWarehouseEntryDate() {
        return warehouseEntryDate;
    }
    
    
    public String getDimensions() {
        return dimensions;
    }
    
    
    public float getWeight() {
        return weight;
    }
    
    
    public String getWarehouseProvince() {
        return warehouseProvince;
    }
    
    
    public String getWarehouseDistrict() {
        return warehouseDistrict;
    }
    
    
    public String getWarehouseAddress() {
        return warehouseAddress;
    }
    
    public void createProduct() {
        // In a real implementation, this would add the product to a database
        System.out.println("Product created: " + title);
    }
    
    public void updateProduct(int productID) {
        // In a real implementation, this would update the product in a database
        System.out.println("Product updated: " + title);
    }
    
    
    public boolean checkEligibilityForRushDelivery() {
        // In a real implementation, this would check various conditions
        // For example, product weight, dimensions, warehouse location, etc.
        return weight < 10.0f && quantity > 0;
    }
    
    
    public boolean checkProductAvailability(int requestedQuantity) {
        return requestedQuantity > 0 && requestedQuantity <= quantity;
    }
    
    public void searchProduct() {
        System.out.println("Searching for product: " + title);
    }
}