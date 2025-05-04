// Vu Hai Dang - 20225962 - UseCase Manage Cart

package controller;

import model.Cart;
import model.Product;

import java.util.HashMap;
import java.util.Map;

public class AddProductToCartController {

	private static Map<Integer, Cart> cartMap = new HashMap<>();

	public boolean requestToAddProductToCart(int productID, int quantity, int cartID) {
		// Check input validity
		if (productID <= 0 || quantity <= 0 || cartID <= 0) {
			return false;
		}

		Product product = getProduct(productID);
		if (product == null) {
			return false;
		}

		Cart cart = getCart(cartID);
		if (cart == null) {
			return false;
		}

		if (!product.checkProductAvailability(quantity)) {
			notifyError("The requested quantity is not available.");
			return false;
		}

		// Add product to cart
		return cart.addProductToCart(productID, quantity, product.getPrice());
	}

	public boolean checkProductInCart(int productID, int cartID) {
		Cart cart = getCart(cartID);
		if (cart == null) {
			return false;
		}

		return cart.checkProductInCart(productID);
	}

	private void notifyError(String message) {
		System.err.println("Error: " + message);
	}

	private Product getProduct(int productID) {
		if (productID <= 0) {
			return null;
		}
		return new Product(productID, "Product " + productID, 10.0f, 100);
	}

	private Cart getCart(int cartID) {
		if (cartID <= 0) {
			return null;
		}

		if (!cartMap.containsKey(cartID)) {
			cartMap.put(cartID, new Cart(cartID));
		}

		return cartMap.get(cartID);
	}

	public static void clearCarts() {
		cartMap.clear();
	}

	public static Map<Integer, Cart> getCartMap() {
		return cartMap;
	}
}