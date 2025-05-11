/*
 * Cohesion Level: Functional Cohesion
 * 
 * SRP Violation: No
 * This base class properly defines common product attributes and behaviors,
 * with appropriate extension through subclasses.
 * 
 * No significant improvements needed.
 */
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
    /**
     * Constructor for creating a basic product with minimal information including weight
     * Added to support CartItem.getProduct() method
     */
    public Product(int productID, String title, String category, float price, float weight) {
        this.productID = productID;
        this.title = title;
        this.category = category;
        this.price = price;
        this.weight = weight;
        this.quantity = 1; // Default quantity
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
        System.out.println("Product created: " + title);
    }
    
    public void updateProduct(int productID) {
        System.out.println("Product updated: " + title);
    }
    
    
    public boolean checkEligibilityForRushDelivery() {
        return weight < 10.0f && quantity > 0;
    }
    
    
    public boolean checkProductAvailability(int requestedQuantity) {
        return requestedQuantity > 0 && requestedQuantity <= quantity;
    }
    
    public void searchProduct() {
        System.out.println("Searching for product: " + title);
    }

	public void updateStock(int productID2, int i) {
		// TODO Auto-generated method stub
		
	}

	public void setPrice(float price) {
		this.price = price;
		
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		
	}
}
