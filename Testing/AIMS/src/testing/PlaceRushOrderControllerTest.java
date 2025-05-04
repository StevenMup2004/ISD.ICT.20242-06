package testing;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import controller.PlaceRushOrderController;
import model.DeliveryInfo;

public class PlaceRushOrderControllerTest {
	
	PlaceRushOrderController placeRushOrderController;
	
	@Before
	public void setUp() {
		placeRushOrderController = new PlaceRushOrderController();
	}
	
	@Test
	public void testCheckIfRushDeliveryInfoMeetsPolicies() {
		DeliveryInfo deliveryInfo;
		deliveryInfo = new DeliveryInfo(
				1,
				"John Doe",
				"user@example.com",
				"0912345678",
				"Hanoi",
				"Ba Dinh");
		assertEquals(0, placeRushOrderController.checkIfRushDeliveryInfoMeetsPolicies(deliveryInfo));
		deliveryInfo = new DeliveryInfo(
				2,
				"Thomas",
				"@gmail.com",
				"0912345678",
				"Thai Binh",
				"Dong Hung");
		assertEquals(1, placeRushOrderController.checkIfRushDeliveryInfoMeetsPolicies(deliveryInfo));
				
		deliveryInfo = new DeliveryInfo(
				3,
				"Thomas",
				"user@gmail.com",
				"0912378",
				"Thai Binh",
				"Dong Hung");
		assertEquals(2, placeRushOrderController.checkIfRushDeliveryInfoMeetsPolicies(deliveryInfo));
		
		deliveryInfo = new DeliveryInfo(
				4,
				"Shelly",
				"shelly123gmail.com",
				"1234",
				"Hanoi",
				"Thanh Xuan");
		assertEquals(3, placeRushOrderController.checkIfRushDeliveryInfoMeetsPolicies(deliveryInfo));
	}
	
}
