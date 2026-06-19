package payment.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import payment.dto.CustomerRequest;
import payment.dto.CustomerResponse;
import payment.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customer API", description = "Customer Management System")
public class CustomerController {

    private static final Logger log =
            LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService service;

    @PostMapping
    @Operation(summary = "Create customer")
    public CustomerResponse create(@RequestBody CustomerRequest request) {

        log.info("POST /api/customers request={}", request);

        return service.create(request);
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Get customer by customerId")
    public CustomerResponse get(@PathVariable String customerId) {

        log.info("GET /api/customers/{}", customerId);

        return service.getByCustomerId(customerId);
    }

  
}