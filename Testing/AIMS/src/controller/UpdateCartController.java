// Vu Hai Dang - 20225962 - UseCase Manage Cart

package controller;

import model.Cart;
import model.Product;

import java.util.HashMap;
import java.util.Map;

public class UpdateCartController {
	private static Map<Integer, Cart> cartMap = new HashMap<>();

	public boolean requestToUpdateCart(int productID, int quantity, int cartID) {
		// Check input validity
		if (productID <= 0 || cartID <= 0) {
			return false;
		}

		Cart cart = getCart(cartID);
		if (cart == null) {
			return false;
		}

		if (quantity <= 0) {
			return cart.removeProductFromCart(productID);
		}

		Product product = getProduct(productID);
		if (product == null) {
			return false;
		}

		if (!product.checkProductAvailability(quantity)) {
			notifyError("The requested quantity is not available.");
			return false;
		}

		// Update the cart
		return cart.updateQuantity(productID, quantity);
	}

	public boolean removeProductFromCart(int productID, int cartID) {
		if (productID <= 0 || cartID <= 0) {
			return false;
		}

		Cart cart = getCart(cartID);
		if (cart == null) {
			return false;
		}

		return cart.removeProductFromCart(productID);
	}

	public boolean updateQuantity(int productID, int quantity, int cartID) {
		if (productID <= 0 || quantity < 0 || cartID <= 0) {
			return false;
		}

		Cart cart = getCart(cartID);
		if (cart == null) {
			return false;
		}

		Product product = getProduct(productID);
		if (product == null) {
			return false;
		}

		if (quantity > 0 && !product.checkProductAvailability(quantity)) {
			notifyError("The requested quantity is not available.");
			return false;
		}

		return cart.updateQuantity(productID, quantity);
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

	// For testing: Syncs with the cart map from AddProductToCartController
	public static void syncCarts() {
		cartMap = AddProductToCartController.getCartMap();
	}
}