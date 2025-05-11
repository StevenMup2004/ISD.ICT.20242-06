package repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.CartItem;
import model.Product;


// test version : for unit test of Service layer
public class CartRepository {
	List<CartItem> cartItems;
	List<Product> products;
	
	public CartRepository(List<CartItem> cartItems, List<Product> products) {	
		this.cartItems = cartItems;
		this.products = products;
	}
	public Map<Integer, String> getWarehouseProvinceOfCartItems(List<CartItem> cartItems) {
		Map<Integer, String> cartItemIDMapToWarehousProvince = new HashMap<>();
		for (CartItem cartItem : cartItems) {
			for (Product product : products) {
				if (cartItem.getProductID() == product.getProductID()) {
					cartItemIDMapToWarehousProvince.put(cartItem.getCartItemID(), product.getWarehouseProvince());
				}
			}
		}
		return cartItemIDMapToWarehousProvince;
	}
}
