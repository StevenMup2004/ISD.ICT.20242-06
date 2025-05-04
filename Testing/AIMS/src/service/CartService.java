package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import repository.CartRepository;

import model.Cart;
import model.CartItem;
import model.DeliveryInfo;

public class CartService {
	private CartRepository cartRepository;
	
	public CartService(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}
	public Map<Integer, Boolean> checkEligibilityForRushDeliveryOfCartItems(List<CartItem> cartItems) {
		  Map<Integer, String> cartItemIDMapToAddress = cartRepository.getWarehouseProvinceOfCartItems(cartItems);
		  Map<Integer, Boolean> cartItemIDMapToRushDeliveryEligibility = new HashMap<>();

		  for (Map.Entry<Integer, String> entry : cartItemIDMapToAddress.entrySet()) {
		    boolean eligible = "Hanoi".equals(entry.getValue());
		    cartItemIDMapToRushDeliveryEligibility.put(entry.getKey(), eligible);
		  }
		  
		  return cartItemIDMapToRushDeliveryEligibility;
		}
}