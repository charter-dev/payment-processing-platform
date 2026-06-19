package payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import payment.dto.PaymentRequest;
import payment.dto.PaymentResponse;
import payment.entity.Customer;
import payment.exception.BusinessException;
import payment.repository.CustomerRepository;
import payment.service.PaymentService;

@SpringBootTest
@Transactional
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void should_create_payment_successfully() {

        Customer customer = customerRepository.findByCustomerId("CUST001")
                .orElseThrow();

        PaymentRequest request = new PaymentRequest();
        request.setCustomerId(customer.getCustomerId());
        request.setMerchant("Tokolala");
        request.setAmount(BigDecimal.valueOf(10000));

        PaymentResponse response = paymentService.create(request);

        assertNotNull(response);
        assertNotNull(response.getTrxId());
        assertEquals("SUCCESS", response.getStatus());
    }
    
    
    
    @Test
    void should_throw_exception_when_amount_zero() {

        PaymentRequest request = new PaymentRequest();
        request.setCustomerId("CUST001");
        request.setMerchant("Tokolala");
        request.setAmount(BigDecimal.ZERO);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> paymentService.create(request)
        );

        assertEquals("INVALID_AMOUNT", ex.getErrorCode());
        assertEquals("Amount must be greater than 0", ex.getMessage());
    }
}
