package model;

import java.util.Date;

public class CD extends Product {
    private String artist;
    private String recordLabel;
    private String trackList;
    private String genre;
    private Date releaseDate;
    
   
    public CD(int productID, String title, float price, int quantity, 
              String artist, String recordLabel) {
        super(productID, title, price, quantity);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.category = "CD";
    }
    
   
    public CD(int productID, String title, float value, float price,
              int quantity, String description, String imageURL, String barCode,
              Date warehouseEntryDate, String dimensions, float weight,
              String warehouseProvince, String warehouseDistrict, String warehouseAddress,
              String artist, String recordLabel, String trackList, String genre, Date releaseDate) {
        super(productID, title, "CD", value, price, quantity, description, imageURL, barCode,
              warehouseEntryDate, dimensions, weight, warehouseProvince, warehouseDistrict, warehouseAddress);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.trackList = trackList;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }
    
    
    public String getArtist() {
        return artist;
    }
    
    
    public String getRecordLabel() {
        return recordLabel;
    }
    
    
    public String getTrackList() {
        return trackList;
    }
    
    
    public String getGenre() {
        return genre;
    }
    
    
    public Date getReleaseDate() {
        return releaseDate;
    }
    
    @Override
    public void createProduct() {
        // In a real implementation, this would add the CD to a database
        System.out.println("CD created: " + title + " by " + artist);
    }
    
    @Override
    public void updateProduct(int productID) {
        // In a real implementation, this would update the CD in a database
        System.out.println("CD updated: " + title + " by " + artist);
    }

	
}