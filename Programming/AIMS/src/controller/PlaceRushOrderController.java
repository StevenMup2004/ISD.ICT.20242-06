/* Bui Xuan Son - 20226065 - Place Rush Order */

/*
 * Cohesion Level: Functional Cohesion
 * 
 * SRP Violation: No
 * This controller has good cohesion - all methods work together to accomplish 
 * the single responsibility of retrieving and displaying product details.
 * 
 * Improvement: No significant improvements needed.
 */
package controller;

import java.util.List;
import java.util.Map;

import model.Cart;
import model.CartItem;
import model.DeliveryInfo;
import service.CartService;

public class PlaceRushOrderController {
  private CartService cartService;
  private List<CartItem> cartItems;

  public PlaceRushOrderController() {

  }

  public int checkIfRushDeliveryInfoMeetsPolicies(DeliveryInfo deliveryInfo) {
    int err = 0;
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    String email = deliveryInfo.getEmail();
    if (!email.matches(emailRegex))
      err = err | 1;

    String phoneNumberRegex = "^(0|\\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-5]|9[0-9])[0-9]{7}$";
    String phoneNumber = deliveryInfo.getPhoneNumber();
    if (!phoneNumber.matches(phoneNumberRegex))
      err = err | 2;
    return err;
  }

  // event handler when user selects rush delivery method
  void requestToPlaceRushOrder() {
    DeliveryInfo deliveryInfo = new DeliveryInfo();
    // TO-DO: set deliveryInfo attributes from input components
    boolean blank = false;
    if (deliveryInfo.getRecipientName().equals("")) {
      // notify "Recipient name is blank"
      blank = true;
    }
    if (deliveryInfo.getEmail().equals("")) {
      // notify "Email is blank"
      blank = true;
    }
    if (deliveryInfo.getPhoneNumber().equals("")) {
      // notify "Phone number is black"
      blank = true;
    }
    if (deliveryInfo.getProvince().equals("")) {
      // notify "Province is blank"
      blank = true;
    }
    if (deliveryInfo.getDistrict().equals("")) {
      // notify "District is blank"
      blank = true;
    }
    if (deliveryInfo.getDeliveryMethod().equals("")) {
      // notify "Delivery method is blank"
      blank = true;
    }
    if (deliveryInfo.getDeliveryTime() == null) {
      // notify "Delivery time is missing"
      blank = true;
    }
    if (deliveryInfo.getDeliveryInstruction().equals("")) {
      // notify "Delivery instruction is blank"
      blank = true;
    }
    if (blank)
      return;

    int error = checkIfRushDeliveryInfoMeetsPolicies(deliveryInfo);
    if ((error & 1) == 0) {

    }
    if ((error & 2) == 0) {

    }

    if (error == 0)
      return;
    if (deliveryInfo.getProvince().equals("Hanoi")) {
      Map<Integer, Boolean> cartItemIDMapToRushDeliveryEligibility = cartService
          .checkEligibilityForRushDeliveryOfCartItems(cartItems);
      for (Map.Entry<Integer, Boolean> entry : cartItemIDMapToRushDeliveryEligibility.entrySet()) {
        // TO-DO: iterating to notify which items in user cart is eligible for rush
        // delivery method
      }
    }
    // navigate to payment process
  }

}
