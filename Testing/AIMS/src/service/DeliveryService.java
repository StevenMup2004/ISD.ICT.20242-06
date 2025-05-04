package service;

/**
 * Service for calculating delivery fees based on location, weight, and order total
 */
public class DeliveryService {
    
    private static final float HANOI_HCMC_BASE_RATE = 22000f; // VND
    private static final float OTHER_PROVINCE_BASE_RATE = 30000f; // VND
    private static final float ADDITIONAL_FEE_PER_HALF_KG = 2500f; // VND
    private static final float FREE_SHIPPING_THRESHOLD = 100000f; // VND
    private static final float MAX_FREE_SHIPPING_DISCOUNT = 25000f; // VND
    
    private static final float HANOI_HCMC_BASE_WEIGHT = 3.0f; // kg
    private static final float OTHER_PROVINCE_BASE_WEIGHT = 0.5f; // kg
    private static final float HALF_KG = 0.5f; // kg

    /**
     * Calculates delivery fee based on weight, location and order total
     * Applies free shipping discount if eligible
     * 
     * @param weight Total weight in kg
     * @param location "Inner" for Hanoi/HCMC, "Other" for other provinces
     * @param orderTotal Total order amount before shipping
     * @return The calculated delivery fee
     */
    public float calculateFee(double weight, String location, double orderTotal) {
        // Calculate base fee
        float baseFee = getBaseRate(location);
        
        // Calculate additional fee based on weight
        float additionalFee = calculateAdditionalFee(weight, location);
        
        // Total fee before free shipping discount
        float totalFee = baseFee + additionalFee;
        
        // Apply free shipping discount if eligible
        return applyFreeShipping(totalFee, orderTotal);
    }
    
    /**
     * Applies free shipping discount if order total exceeds threshold
     * Maximum discount is capped
     * 
     * @param fee Original delivery fee
     * @param orderTotal Total order amount before shipping
     * @return Fee after applying free shipping discount
     */
    public float applyFreeShipping(double fee, double orderTotal) {
        if (orderTotal >= FREE_SHIPPING_THRESHOLD) {
            // Apply discount up to maximum amount
            float discount = (float) Math.min(fee, MAX_FREE_SHIPPING_DISCOUNT);
            return (float) Math.max(0, fee - discount);
        }
        return (float) fee;
    }
    
    /**
     * Gets the base delivery rate depending on location
     * 
     * @param location "Inner" for Hanoi/HCMC, "Other" for other provinces
     * @return Base delivery rate
     */
    public float getBaseRate(String location) {
        if ("Inner".equalsIgnoreCase(location)) {
            return HANOI_HCMC_BASE_RATE;
        } else {
            return OTHER_PROVINCE_BASE_RATE;
        }
    }
    
    /**
     * Calculates additional fee for weight exceeding the base weight
     * 
     * @param weight Total weight in kg
     * @param location "Inner" for Hanoi/HCMC, "Other" for other provinces
     * @return Additional fee for extra weight
     */
    public float calculateAdditionalFee(double weight, String location) {
        double baseWeight = "Inner".equalsIgnoreCase(location) ? 
                HANOI_HCMC_BASE_WEIGHT : OTHER_PROVINCE_BASE_WEIGHT;
        
        // If weight is less than or equal to base weight, no additional fee
        if (weight <= baseWeight) {
            return 0;
        }
        
        // Calculate excess weight
        double excessWeight = weight - baseWeight;
        
        // Calculate additional half-kilos (rounded up)
        int additionalHalfKilos = (int) Math.ceil(excessWeight / HALF_KG);
        
        return additionalHalfKilos * ADDITIONAL_FEE_PER_HALF_KG;
    }
}
