package model;

import java.util.Date;


public class DVD extends Product {
    private int discType;
    private int director;
    private int runtime;
    private int studio;
    private int language;
    private int subtitle;
    private Date releaseDate;
    private int genre;
    
    
    public DVD(int productID, String title, float price, int quantity, 
               int director, int studio) {
        super(productID, title, price, quantity);
        this.director = director;
        this.studio = studio;
        this.category = "DVD";
    }
    
    
    public DVD(int productID, String title, float value, float price,
               int quantity, String description, String imageURL, String barCode,
               Date warehouseEntryDate, String dimensions, float weight,
               String warehouseProvince, String warehouseDistrict, String warehouseAddress,
               int discType, int director, int runtime, int studio, int language,
               int subtitle, Date releaseDate, int genre) {
        super(productID, title, "DVD", value, price, quantity, description, imageURL, barCode,
              warehouseEntryDate, dimensions, weight, warehouseProvince, warehouseDistrict, warehouseAddress);
        this.discType = discType;
        this.director = director;
        this.runtime = runtime;
        this.studio = studio;
        this.language = language;
        this.subtitle = subtitle;
        this.releaseDate = releaseDate;
        this.genre = genre;
    }
    
    
    public int getDiscType() {
        return discType;
    }
    
   
    public int getDirector() {
        return director;
    }
    
    
    public int getRuntime() {
        return runtime;
    }
    
 
    public int getStudio() {
        return studio;
    }
    
    public int getLanguage() {
        return language;
    }
    
    
    public int getSubtitle() {
        return subtitle;
    }
    
    
    public Date getReleaseDate() {
        return releaseDate;
    }
    
    
    public int getGenre() {
        return genre;
    }
    
    
    @Override
    public void createProduct() {
        // In a real implementation, this would add the DVD to a database
        System.out.println("DVD created: " + title);
    }
    
    
    @Override
    public void updateProduct(int productID) {
        // In a real implementation, this would update the DVD in a database
        System.out.println("DVD updated: " + title);
    }
}