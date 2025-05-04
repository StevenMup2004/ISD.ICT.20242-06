// Vu Hai Dang - 20225962 - UseCase View Product Details


package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import model.Product;
import model.Book;
import model.CD;
import model.DVD;
import model.LPRecord;
import controller.ViewProductDetailsController;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

public class ViewProductDetailsControllerTest {
    
    private ViewProductDetailsController controller;
    
    @Before
    public void setUp() {
    	clearProducts();
        addSampleProducts();
        controller = new ViewProductDetailsController();
    }
    
    //Test requesting to view a valid product's details.
    @Test
    public void testRequestToViewValidProductDetails() {
        Product product = controller.requestToViewProductDetails(1);
        
        assertNotNull("Should return a product object for a valid ID", product);
        assertEquals("Product should have the correct ID", 1, product.getProductID());
        assertEquals("Product should have the correct title", "The Great Gatsby", product.getTitle());
        assertEquals("Product should have the correct category", "Book", product.getCategory());
    }
    
    //Test requesting to view a non-existent product.
     
    @Test
    public void testRequestToViewNonExistentProductDetails() {
        Product product = controller.requestToViewProductDetails(999);
        
        assertNull("Should return null for a non-existent product ID", product);
    }
    
    // Test requesting to view a product with an invalid ID.
    @Test
    public void testRequestToViewInvalidProductID() {
        Product product = controller.requestToViewProductDetails(-1);
        
        assertNull("Should return null for an invalid product ID", product);
    }
    
    // Test rendering details for a valid product.
    @Test
    public void testRenderProductDetails() {
        Product product = controller.renderProductDetails(1);
        
        assertNotNull("Should return a product object for rendering", product);
        assertEquals("Product should have the correct ID", 1, product.getProductID());
        assertEquals("Product should have the correct title", "The Great Gatsby", product.getTitle());
    }
    
    //Test rendering details for a non-existent product.
    @Test
    public void testRenderNonExistentProductDetails() {
        Product product = controller.renderProductDetails(999);
        
        assertNull("Should return null for a non-existent product ID", product);
    }
    
    // Test that different product types are correctly returned.
    @Test
    public void testRequestDifferentProductTypes() {
        // Get a Book
        Product book = controller.requestToViewProductDetails(1);
        assertTrue("Product 1 should be a Book", book instanceof Book);
        
        // Get a CD
        Product cd = controller.requestToViewProductDetails(2);
        assertTrue("Product 2 should be a CD", cd instanceof CD);
        
        // Get a DVD
        Product dvd = controller.requestToViewProductDetails(3);
        assertTrue("Product 3 should be a DVD", dvd instanceof DVD);
        
        // Get an LP Record
        Product lpRecord = controller.requestToViewProductDetails(4);
        assertTrue("Product 4 should be an LP Record", lpRecord instanceof LPRecord);
    }
    
    // Test that product type-specific attributes are accessible.
    @Test
    public void testProductTypeSpecificAttributes() {
        Product product = controller.requestToViewProductDetails(1);
        Book book = (Book) product;
        assertEquals("Book should have the correct author", "F. Scott Fitzgerald", book.getAuthor());
        
        product = controller.requestToViewProductDetails(2);
        CD cd = (CD) product;
        assertEquals("CD should have the correct artist", "The Beatles", cd.getArtist());
        
        product = controller.requestToViewProductDetails(3);
        DVD dvd = (DVD) product;
        assertEquals("DVD should have the correct runtime", 175, dvd.getRuntime());
        
        product = controller.requestToViewProductDetails(4);
        LPRecord lpRecord = (LPRecord) product;
        assertEquals("LP Record should have the correct artist", "Pink Floyd", lpRecord.getArtist());
    }
    
  
    private static void clearProducts() {
        try {
            Field field = ViewProductDetailsController.class.getDeclaredField("productMap");
            field.setAccessible(true);
            Map<Integer, Product> map = (Map<Integer, Product>) field.get(null);
            map.clear();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear product map", e);
        }
    }

    private static void addProduct(Product product) {
        try {
            Field field = ViewProductDetailsController.class.getDeclaredField("productMap");
            field.setAccessible(true);
            Map<Integer, Product> map = (Map<Integer, Product>) field.get(null);
            map.put(product.getProductID(), product);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add product to map", e);
        }
    }

    private void addSampleProducts() {
        addProduct(new Book(
            1, "The Great Gatsby", 15.0f, 20.99f, 100, "A classic novel set in the Jazz Age",
            "http://example.com/gatsby.jpg", "9780743273565", new Date(), "5.5 x 0.5 x 8.2 inches",
            0.55f, "New York", "Manhattan", "123 Warehouse St", "F. Scott Fitzgerald",
            "Paperback", "Scribner", new Date(70, 3, 10), 180, "English", "Fiction"
        ));

        addProduct(new CD(
            2, "Abbey Road", 10.0f, 14.99f, 50, "The Beatles' iconic album",
            "http://example.com/abbey_road.jpg", "5099969943997", new Date(),
            "5.5 x 5.5 x 0.5 inches", 0.2f, "California", "Los Angeles", "456 Storage Blvd",
            "The Beatles", "Apple Records", "Come Together, Something, Maxwell's Silver Hammer...",
            "Rock", new Date(69, 8, 26)
        ));

        addProduct(new DVD(
            3, "The Godfather", 12.0f, 19.99f, 30, "Francis Ford Coppola's masterpiece",
            "http://example.com/godfather.jpg", "097361386645", new Date(),
            "7.5 x 5.5 x 0.5 inches", 0.3f, "California", "San Francisco", "789 Inventory Lane",
            1, 1, 175, 1, 1, 1, new Date(72, 2, 24), 1
        ));

        addProduct(new LPRecord(
            4, "Dark Side of the Moon", 25.0f, 29.99f, 20, "Pink Floyd's legendary album",
            "http://example.com/dark_side.jpg", "5099902987613", new Date(),
            "12.5 x 12.5 x 0.2 inches", 0.4f, "New York", "Brooklyn", "101 Warehouse Ave",
            "Pink Floyd", "Harvest Records", "Speak to Me, Breathe, On the Run...",
            "Progressive Rock", new Date(73, 2, 1)
        ));
    }
    }
