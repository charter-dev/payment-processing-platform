package payment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import payment.dto.CustomerRequest;
import payment.dto.CustomerResponse;
import payment.dto.PageResponse;
import payment.entity.Customer;
import payment.exception.BusinessException;
import payment.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

	private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

	private final CustomerRepository repository;
	private final RedisTemplate<String, Object> redisTemplate;


	public PageResponse<CustomerResponse> getAll(int page, int size) {

		try {
			log.info("START GET ALL");

			Pageable pageable = PageRequest.of(page, size);

			Page<CustomerResponse> result = repository.findAll(pageable).map(this::mapToResponse);

			log.info("TOTAL DATA : " + result.getTotalElements());

			return new PageResponse<>(result.getContent(), result.getNumber(), result.getSize(),
					result.getTotalElements(), result.getTotalPages());
		} catch (Exception e) {
			log.error("getAll Customer ERROR ", e);
			throw new BusinessException("CUSTOMER_FAILED" , "Failed to retrieve customer data");
		}

	}

	@CacheEvict(value = "customers", allEntries = true)
	public CustomerResponse create(CustomerRequest request) {

		log.info("START create customer request={}", request);

		// VALIDATION

		Customer entity = Customer.builder().customerId(generateCustId()).name(request.getName())
				.email(request.getEmail()).phone(request.getPhone()).build();

		Customer saved = repository.save(entity);

		try {
			redisTemplate.opsForValue().set(saved.getCustomerId(), saved);
			log.info("Cached customer to Redis customerId={}", saved.getCustomerId());
		} catch (Exception e) {
			log.error("REDIS ERROR customerId={}", saved.getCustomerId(), e);
		}

		return CustomerResponse.builder().customerId(saved.getCustomerId()).name(saved.getName())
				.email(saved.getEmail()).build();
	}
	
	public CustomerResponse getByCustomerId(String customerId) {

		try {
			Customer cache = (Customer) redisTemplate.opsForValue().get(customerId);

			if (cache != null) {
				log.info("CACHE HIT customerId={}", customerId);
				return map(cache);
			}

			log.info("CACHE MISS customerId={}", customerId);

		} catch (Exception e) {
			log.error("REDIS GET ERROR customerId={}", customerId, e);
		}

		Customer customer = repository.findByCustomerId(customerId)
				.orElseThrow(() -> new BusinessException("CUSTOMER_NOT_FOUND", "Customer not found: " + customerId));

		try {
			redisTemplate.opsForValue().set(customerId, customer);
		} catch (Exception e) {
			log.error("REDIS SET ERROR customerId={}", customerId, e);
		}

		return map(customer);
	}

	private CustomerResponse map(Customer c) {
		return CustomerResponse.builder().customerId(c.getCustomerId()).name(c.getName()).email(c.getEmail()).build();
	}

	private CustomerResponse mapToResponse(Customer c) {
		return new CustomerResponse(c.getCustomerId(), c.getName(), c.getEmail(), c.getPhone());
	}

	private String generateCustId() {
		return "CUST-" + System.currentTimeMillis();
	}
}
