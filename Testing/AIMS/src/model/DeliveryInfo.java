package model;

import java.time.LocalDateTime;

public class DeliveryInfo {
	private int deliveryInfoID;
	private String recipientName;
	private String email;
	private String phoneNumber;
	private String province;
	private String district;
	private String deliveryMethod;
	private LocalDateTime deliveryTime;
	private String deliveryInstruction;
	
	public DeliveryInfo(int deliveryInfoID, String recipientName, String email, String phoneNumber,
            String province, String district) {
		this.deliveryInfoID = deliveryInfoID;
		this.recipientName = recipientName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.province = province;
		this.district = district;
		this.deliveryMethod = "Rush Delivery";
		this.deliveryTime = LocalDateTime.now();
		this.deliveryInstruction = null;
	}
	public DeliveryInfo() {
		
	}
	public String getRecipientName() {
		return recipientName;
	}
	public String getEmail() {
		return email;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getProvince() {
		return province;
	}
	public String getDistrict() {
		return district;
	}
	
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	
	public LocalDateTime getDeliveryTime() {
		return deliveryTime;
	}
	
	public String getDeliveryInstruction() {
		return deliveryInstruction;
	}
}
