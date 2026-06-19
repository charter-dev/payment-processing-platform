package payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import payment.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustomerId(String customerId);
    
    

    @Query(value = "SELECT * FROM customer WHERE customer_id = :customerId", nativeQuery = true)
    Optional<Customer> findByCustomerIdNative(@Param("customerId") String customerId);

    
}