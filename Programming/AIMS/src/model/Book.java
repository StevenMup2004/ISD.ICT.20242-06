/*
 * Cohesion Level: Functional Cohesion
 * 
 * SRP Violation: No
 * This class correctly represents a specific product type with its
 * unique properties and behaviors.
 * 
 * No significant improvements needed.
 */
package model;

import java.util.Date;

public class Book extends Product {
    private String author;
    private String coverType;
    private String publisher;
    private Date publicationDate;
    private int pages;
    private String language;
    private String genre;
    
   
    public Book(int productID, String title, float price, int quantity, 
                String author, String publisher) {
        super(productID, title, price, quantity);
        this.author = author;
        this.publisher = publisher;
        this.category = "Book";
    }
    
   
    public Book(int productID, String title, float value, float price,
                int quantity, String description, String imageURL, String barCode,
                Date warehouseEntryDate, String dimensions, float weight,
                String warehouseProvince, String warehouseDistrict, String warehouseAddress,
                String author, String coverType, String publisher, Date publicationDate,
                int pages, String language, String genre) {
        super(productID, title, "Book", value, price, quantity, description, imageURL, barCode,
              warehouseEntryDate, dimensions, weight, warehouseProvince, warehouseDistrict, warehouseAddress);
        this.author = author;
        this.coverType = coverType;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.pages = pages;
        this.language = language;
        this.genre = genre;
    }
    
  
    public String getAuthor() {
        return author;
    }
    
   
    public String getCoverType() {
        return coverType;
    }
    
    
    public String getPublisher() {
        return publisher;
    }
    
  
    public Date getPublicationDate() {
        return publicationDate;
    }
    
    
    public int getPages() {
        return pages;
    }
    
   
    public String getLanguage() {
        return language;
    }
    
    
    public String getGenre() {
        return genre;
    }
    
    @Override
    public void createProduct() {
        System.out.println("Book created: " + title + " by " + author);
    }
    
   
    @Override
    public void updateProduct(int productID) {
        System.out.println("Book updated: " + title + " by " + author);
    }
}