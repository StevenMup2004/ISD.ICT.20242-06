// writer: Bui Xuan Son - 20226065

package testing;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import model.CartItem;
import model.Product;
import repository.CartRepository;
import service.CartService;


public class CartServiceTest {
	List<Product> products = Arrays.asList(
		      new Product(1, "Book", "Education", 50f, 60f, 100, "Educational book",
		        "http://example.com/book.jpg", "1234567890", null, "20x15x2", 0.5f,
		        "Hanoi", "Dong Da", "123 Nguyen Luong Bang"),
		      new Product(2, "Pen", "Stationery", 5f, 10f, 200, "Blue ink pen",
		        "http://example.com/pen.jpg", "0987654321", null, "5x1x1", 0.1f,
		        "Ho Chi Minh", "District 1", "456 Le Loi"),
		      new Product(3, "Notebook", "Stationery", 20f, 25f, 150, "Lined notebook",
		        "http://example.com/notebook.jpg", "1122334455", null, "21x15x1", 0.3f,
		        "Da Nang", "Hai Chau", "789 Tran Phu")
		    );
	List<CartItem> cartItems = Arrays.asList(
		      new CartItem(101, 1, 1, 2, 120.0f),
		      new CartItem(102, 2, 1, 5, 50.0f),
		      new CartItem(103, 3, 1, 3, 75.0f)
		    );
	CartRepository cartRepository;
	CartService cartService;
	@Before
	public void setUp() {
		cartRepository = new CartRepository(cartItems, products);
		cartService = new CartService(cartRepository);
	}
	@Test
	public void testCheckEligibilityForRushDeliveryOfCartItems() {
		Map<Integer, Boolean> refData = new HashMap<>();
		refData.put(101, true);
		refData.put(102, false);
		refData.put(103, false);

		assertEquals(refData, cartService.checkEligibilityForRushDeliveryOfCartItems(cartItems));
	}
}
