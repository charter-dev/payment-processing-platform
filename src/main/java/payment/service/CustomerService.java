package payment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import payment.dto.CustomerRequest;
import payment.dto.CustomerResponse;
import payment.entity.Customer;
import payment.exception.BusinessException;
import payment.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

	private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

	private final CustomerRepository repository;

	public CustomerResponse create(CustomerRequest request) {

		log.info("START create customer request={}", request);

		// VALIDATION

		Customer entity = Customer.builder().customerId(generateCustId()).name(request.getName())
				.email(request.getEmail()).phone(request.getPhone()).build();

		Customer saved = repository.save(entity);


		return CustomerResponse.builder().customerId(saved.getCustomerId()).name(saved.getName())
				.email(saved.getEmail()).build();
	}

	public CustomerResponse getByCustomerId(String customerId) {


		Customer customer = repository.findByCustomerId(customerId)
				.orElseThrow(() -> new BusinessException("CUSTOMER_NOT_FOUND", "Customer not found: " + customerId));


		return map(customer);
	}

	private CustomerResponse map(Customer c) {
		return CustomerResponse.builder().customerId(c.getCustomerId()).name(c.getName()).email(c.getEmail()).build();
	}

	private String generateCustId() {
		return "CUST-" + System.currentTimeMillis();
	}
}
