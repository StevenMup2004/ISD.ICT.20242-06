// Bui Xuan Son - 20226065 - Place Rush Order
/*
 * Cohesion Level: Functional Cohesion
 * 
 * SRP Violation: No
 * This controller has good cohesion - all methods work together to accomplish 
 * the single responsibility of retrieving and displaying product details.
 * 
 * Improvement: No significant improvements needed.
 */

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
