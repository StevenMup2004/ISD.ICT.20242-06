// Bui Xuan Son - 20226065 - Place Rush Order

/*
 * Cohesion Level: Communicational Cohesion
 * 
 * SRP Violation: Yes
 * This controller handles multiple responsibilities including:
 * - Database connection establishment
 * - Cart retrieval functionality
 * 
 * Improvement:
 * 1. Split database management fromt this class, build an utility class instead
 */

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
