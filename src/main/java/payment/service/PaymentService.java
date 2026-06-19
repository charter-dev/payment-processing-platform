package payment.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import payment.dto.PaymentRequest;
import payment.dto.PaymentResponse;
import payment.entity.Customer;
import payment.entity.PaymentTransaction;
import payment.exception.BusinessException;
import payment.exception.ResourceNotFoundException;
import payment.repository.CustomerRepository;
import payment.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

	private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

	private final PaymentRepository paymentRepository;
	private final CustomerRepository customerRepository;
	

	public PaymentResponse create(PaymentRequest request) {

	    log.info("START payment process request={}", request);

	    // VALIDASI AMOUNT
	    if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
	        throw new BusinessException("INVALID_AMOUNT", "Amount must be greater than 0");
	    }
	    
	    Customer customer = customerRepository.findByCustomerId(request.getCustomerId())
	            .orElseThrow(() -> new BusinessException(
	                    "CUSTOMER_NOT_FOUND",
	                    "Customer not found: " + request.getCustomerId()
	            ));

	    PaymentTransaction saved = null;

	    try {
	        // 1. SAVE TO DB (CRITICAL)
	        PaymentTransaction entity = PaymentTransaction.builder()
	                .trxId(generateTrxId())
	                .customer(customer)
	                .merchant(request.getMerchant())
	                .amount(request.getAmount())
	                .status("SUCCESS")
	                .build();

	        saved = paymentRepository.save(entity);
	        log.info("Saved to DB trxId={}", saved.getTrxId());

	    } catch (Exception e) {
	        log.error("DB ERROR while saving payment", e);
	        throw e;
	    }


	    return PaymentResponse.builder()
	            .trxId(saved.getTrxId())
	            .status(saved.getStatus())
	            .build();
	}

	public PaymentTransaction get(String trxId) {


		PaymentTransaction data = paymentRepository.findByTrxId(trxId).orElseThrow(() -> {
			log.error("Payment NOT FOUND trxId={}", trxId);
			return new ResourceNotFoundException("Payment not found");
		});


		return data;
	}


	private String generateTrxId() {
		return "TRX-" + System.currentTimeMillis();
	}
}