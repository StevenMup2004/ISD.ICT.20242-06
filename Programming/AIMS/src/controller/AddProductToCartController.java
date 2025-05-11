// Vu Hai Dang - 20225962 - UseCase Manage Cart

/*
 * Cohesion Level: Communicational Cohesion
 * 
 * SRP Violation: Yes
 * This controller handles multiple responsibilities including:
 * - Product retrieval functionality
 * - Cart retrieval functionality
 * 
 * Improvement:
 * 1. Extract static cart management to a dedicated CartService class
 * 2. Move product retrieval to a ProductService
 * 3. Focus this controller solely on the "add to cart" operation
 */
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
	// COMMUNICATIONAL COHESION: This method is only related to others through shared cart data

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
	// PROCEDURAL COHESION: This product retrieval logic should be in a ProductService
	private Product getProduct(int productID) {
		if (productID <= 0) {
			return null;
		}
		return new Product(productID, "Product " + productID, 10.0f, 100);
	}

	// PROCEDURAL COHESION: This cart retrieval logic should be in a CartService
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