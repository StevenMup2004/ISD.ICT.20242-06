package model;

import java.util.Date;

public class LPRecord extends Product {
    private String artist;
    private String recordLabel;
    private String trackList;
    private String genre;
    private Date releaseDate;
    
    
    public LPRecord(int productID, String title, float price, int quantity, 
                    String artist, String recordLabel) {
        super(productID, title, price, quantity);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.category = "LP Record";
    }
    
   
    public LPRecord(int productID, String title, float value, float price,
                    int quantity, String description, String imageURL, String barCode,
                    Date warehouseEntryDate, String dimensions, float weight,
                    String warehouseProvince, String warehouseDistrict, String warehouseAddress,
                    String artist, String recordLabel, String trackList, String genre, Date releaseDate) {
        super(productID, title, "LP Record", value, price, quantity, description, imageURL, barCode,
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
        System.out.println("LP Record created: " + title + " by " + artist);
    }
    
    
    @Override
    public void updateProduct(int productID) {
        System.out.println("LP Record updated: " + title + " by " + artist);
    }
}
