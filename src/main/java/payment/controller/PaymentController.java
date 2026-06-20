package payment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import payment.dto.DashboardResponse;
import payment.dto.PageResponse;
import payment.dto.PaymentRequest;
import payment.dto.PaymentResponse;
import payment.elastic.PaymentDocument;
import payment.entity.PaymentTransaction;
import payment.service.ElasticIndexService;
import payment.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment API", description = "Payment Processing System")
public class PaymentController {

	private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

	private final PaymentService service;
	private final ElasticIndexService elasticIndexService;

	@PostMapping
	@Operation(summary = "Create payment transaction")
	public PaymentResponse create(@RequestBody PaymentRequest request) {

		log.info("POST /api/payments request={}", request);

		return service.create(request);
	}

	@GetMapping("/{trxId}")
	@Operation(summary = "Get payment by trxId")
	public PaymentTransaction get(@PathVariable String trxId) {

		log.info("GET /api/payments/{}", trxId);

		return service.get(trxId);
	}

	@GetMapping
	@Operation(summary = "Get all payments (pagination)", description = "Retrieve payment transactions with pagination")
	public PageResponse<PaymentResponse> getAll(@Parameter() @RequestParam(defaultValue = "0") int page,

			@Parameter() @RequestParam(defaultValue = "10") int size) {
		return service.getAll(page, size);
	}

	@GetMapping("/dashboard")
	@Operation(summary = "Payment dashboard summary")
	public DashboardResponse dashboard() {

		log.info("GET /api/payments/dashboard");

		return service.dashboard();
	}

	@GetMapping("/search")
	@Operation(summary = "Search Payment doc Elastic")
	public List<PaymentDocument> search(@RequestParam String merchant) {

		log.info("GET /api/payments/search");

		return service.paymentdoc(merchant);

	}
	
	@PostMapping("/reindex")
	@Operation(summary = "Index Payment doc Elastic setelah pakai fly away")
	public String reindex() {

		return elasticIndexService.reindexAll();
	}
}