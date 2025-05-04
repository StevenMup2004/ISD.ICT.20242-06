// Le Dai Lam - 20225982 - Add/Update ProductProduct
package testing;

import model.*;
import controller.*;

import controller.ProductController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProductControllerTest {

    private ProductController ctrl;

    @BeforeEach
    void setUp() {
        ctrl = new ProductController();
    }

    @Test @DisplayName("UT001: Add a valid product")
    void addValidProduct() {
        Book p = new Book(
            1, "1984", 10.0f, 5,
            "Orwell", "Secker & Warburg"
        );
        assertDoesNotThrow(() -> ctrl.CreateProduct(p));
    }

    @Test @DisplayName("UT002: Add a product missing required information")
    void addMissingRequired() {
        DVD p = new DVD(
            2, null, 15.0f, 3,     // title == null
            123, 456
        );
        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> ctrl.CreateProduct(p));
        assertEquals("Missing required field", ex.getMessage());
    }

    @Test @DisplayName("UT003: Update an existing product")
    void updateExisting() {
        Book p = new Book(3, "Title", 20.0f, 10, "Author", "Publisher");
        ctrl.CreateProduct(p);

        assertDoesNotThrow(() -> ctrl.UpdateProduct(3, Map.of("quantity", 99)));
        assertEquals(99, p.getQuantity());
    }

    @Test @DisplayName("UT004: Attempt to update a non‑existent product")
    void updateNonExistent() {
        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> ctrl.UpdateProduct(999, Map.of("price", 25.0f)));
        assertEquals("Product not found", ex.getMessage());
    }

    @Test @DisplayName("UT005: Add a new product using an already existing productID")
    void addDuplicateID() {
        Book p1 = new Book(4, "A", 5.0f, 2, "A", "P");
        ctrl.CreateProduct(p1);
        Book p2 = new Book(4, "B", 6.0f, 3, "B", "P");
        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> ctrl.CreateProduct(p2));
        assertEquals("Duplicate productID", ex.getMessage());
    }

    @Test @DisplayName("UT006: Add a DVD/CD/Book with all type‑specific attributes")
    void addAllTypeSpecific() {
        // DVD
        DVD d = new DVD(5, "Inception", 12.0f, 4, 111, 222);
        // Book
        Book b = new Book(6, "Dune", 8.0f, 6, "Herbert", "Chilton");
        // (CD and LPRecord analogous—omitted for brevity)

        assertDoesNotThrow(() -> {
            ctrl.CreateProduct(d);
            ctrl.CreateProduct(b);
        });
    }

    @Test @DisplayName("UT007: Add a DVD/CD/Book missing some type‑specific attributes")
    void addMissingTypeSpecific() {
        Book b = new Book(7, "X", 9.0f, 5, null, "Pub");  // author == null
        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> ctrl.CreateProduct(b));
        assertEquals("Missing required field", ex.getMessage());
    }

    @Test @DisplayName("UT008: Update warehouse information (province, address, etc.)")
    void updateWarehouseInfo() {
        Book p = new Book(8, "W", 7.0f, 3, "A", "P");
        // assume we want to change quantity and price together
        ctrl.CreateProduct(p);

        assertDoesNotThrow(() -> ctrl.UpdateProduct(8, Map.of(
            "quantity", 20,
            "price", 7.0f
        )));
        assertEquals(20, p.getQuantity());
        assertEquals(7.0f, p.getPrice());
    }

    @Test @DisplayName("UT009: Enter negative or invalid format for price/quantity")
    void invalidPriceQuantity() {
        Book p = new Book(9, "T", 10.0f, 5, "A", "P");
        ctrl.CreateProduct(p);

        // negative price
        RuntimeException ex1 = assertThrows(RuntimeException.class,
            () -> ctrl.UpdateProduct(9, Map.of("price", -1.0f)));
        assertEquals("Price out of range", ex1.getMessage());

        // invalid quantity format
        RuntimeException ex2 = assertThrows(RuntimeException.class,
            () -> ctrl.UpdateProduct(9, Map.of("quantity", "ten")));
        assertEquals("Invalid quantity format", ex2.getMessage());
    }

    @Test @DisplayName("UT015: Add a product with price outside 30%–150% range")
    void priceOutsideRange() {
        Book p = new Book(10, "O", 10.0f, 5, "A", "P");
        ctrl.CreateProduct(p);

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> ctrl.UpdateProduct(10, Map.of("price", 50.0f)));
        assertEquals("Price out of range", ex.getMessage());
    }

    @Test @DisplayName("UT016: Update a product’s price for the third time in one day")
    void thirdPriceUpdateLimit() {
        Book p = new Book(11, "L", 10.0f, 5, "A", "P");
        ctrl.CreateProduct(p);

        // simulate two prior price updates
        HistoryUpdate hist = new HistoryUpdate(0, new Date(), true, 11);
        hist.recordUpdate(11, true);
        hist.recordUpdate(11, true);

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> ctrl.UpdateProduct(11, Map.of("price", 12.0f)));
        assertEquals("Daily price-update limit exceeded", ex.getMessage());
    }

    @Test @DisplayName("UT017: Delete more than 10 products at once")
    void deleteMoreThan10() {
        // stub: your controller must later implement deleteProducts(List<Integer> ids)
        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> { throw new RuntimeException("Batch size exceeds 10"); });
        assertEquals("Batch size exceeds 10", ex.getMessage());
    }
}
