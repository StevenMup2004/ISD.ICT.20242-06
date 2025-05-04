package model;

public class VNPay {
    private final String apiKey;
    private final String gatewayUrl;

    /** 
     * Default constructor initializes sandbox credentials. 
     * In real use, inject these via configuration.
     */
    public VNPay() {
        this.apiKey = "sandbox_api_key";
        this.gatewayUrl = "https://sandbox.vnpayment.vn/payment";
    }

    /**
     * Sends the payment request to VNPay.
     * @throws VNPayConnectionException if gatewayUrl is not set
     * @throws VNPayResponseException if VNPay returns an error
     */
    public void processByVNPay(PaymentTransaction tx)
            throws VNPayConnectionException, VNPayResponseException {
        if (gatewayUrl == null || gatewayUrl.isEmpty()) {
            throw new VNPayConnectionException("VNPay gateway URL is not configured.");
        }
        System.out.println("Calling VNPay at " + gatewayUrl
            + " [API key=" + apiKey + "] for tx #" + tx.getTransactionId()
            + ", amount=" + tx.getAmount());
        // --- simulate gateway response ---
        // If VNPay had returned an error code:
        //   throw new VNPayResponseException("VNPay declined the transaction");
    }

    /**
     * Sends a refund request to VNPay.
     * @return true if refund succeeded
     * @throws VNPayConnectionException if gatewayUrl is not set
     * @throws VNPayResponseException if VNPay returns an error
     */
    public boolean processRefund(PaymentTransaction tx)
            throws VNPayConnectionException, VNPayResponseException {
        if (gatewayUrl == null || gatewayUrl.isEmpty()) {
            throw new VNPayConnectionException("VNPay gateway URL is not configured.");
        }
        System.out.println("Calling VNPay refund for tx #" + tx.getTransactionId());
        // simulate always success:
        return true;
    }

    // Custom exception classes for VNPay errors:
    public static class VNPayConnectionException extends Exception {
        public VNPayConnectionException(String msg) { super(msg); }
    }
    public static class VNPayResponseException extends Exception {
        public VNPayResponseException(String msg) { super(msg); }
    }
}