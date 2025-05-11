package testing;


//Nguyen Minh Khoi - 20226050 - Pay Order



import org.junit.jupiter.api.Test;
import model.*;
import controller.*;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PayOrderControllerTest {

    private VNPay mockVNPay;
    private PaymentTransaction mockTx;
    private PayOrderController controller;

    @BeforeEach
    void setUp() {
        mockVNPay = mock(VNPay.class);

        // Tạo mock transaction mặc định
        mockTx = mock(PaymentTransaction.class);
        when(mockTx.getTransactionId()).thenReturn(1);
        when(mockTx.getAmount()).thenReturn(100f);
        when(mockTx.getPaymentMethod()).thenReturn("Credit Card");

        // Sử dụng factory trả về mock transaction
        PaymentTransactionFactory mockFactory = (amount, method, orderId) -> mockTx;

        controller = new PayOrderController(mockVNPay, mockFactory);
    }

    @Test
    void UT032_payOrder_Success() throws Exception {
        Invoice invoice = new Invoice(101);
        when(mockTx.evaluatePaymentResult()).thenReturn(true);

        boolean result = controller.payOrder(invoice);

        assertTrue(result);
        assertEquals("paid", getInvoiceStatus(invoice));
        verify(mockVNPay).processByVNPay(mockTx);
        verify(mockTx).save();
    }

    @Test
    void UT033_payOrder_EvaluateFalse() throws Exception {
        Invoice invoice = new Invoice(102);
        when(mockTx.evaluatePaymentResult()).thenReturn(false);

        boolean result = controller.payOrder(invoice);

        assertFalse(result);
        assertEquals("unpaid", getInvoiceStatus(invoice));
        verify(mockTx).save();
    }

    @Test
    void UT034_payOrder_VNPayConnectionError() throws Exception {
        Invoice invoice = new Invoice(103);
        doThrow(new VNPay.VNPayConnectionException("connection fail"))
                .when(mockVNPay).processByVNPay(mockTx);

        boolean result = controller.payOrder(invoice);

        assertFalse(result);
        assertEquals("unpaid", getInvoiceStatus(invoice));
    }

    @Test
    void UT035_payOrder_VNPayResponseError() throws Exception {
        Invoice invoice = new Invoice(104);
        doThrow(new VNPay.VNPayResponseException("vnPay refused"))
                .when(mockVNPay).processByVNPay(mockTx);

        boolean result = controller.payOrder(invoice);

        assertFalse(result);
        assertEquals("unpaid", getInvoiceStatus(invoice));
    }

    @Test
    void UT036_payOrder_NullInvoice() {
        boolean result = controller.payOrder(null);

        assertFalse(result);
        // không throw exception là thành công
    }

    // Hàm hỗ trợ vì status của Invoice không có getter
    private String getInvoiceStatus(Invoice invoice) {
        // workaround: convert ra map để lấy status gián tiếp
        return invoice.generateInvoiceDetails().getOrDefault("status", invoice.toString()).toString()
                .contains("paid") ? "paid" : "unpaid";
    }
}