package payment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import payment.entity.PaymentTransaction;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentTransaction, Long> {

	Optional<PaymentTransaction> findByTrxId(String trxId);

	@Query(value = """
			SELECT merchant, COUNT(*) total_trx, SUM(amount) total_amount, RANK() OVER( ORDER BY SUM(amount) DESC ) ranking 
			FROM payment_transaction GROUP BY merchant
				""", nativeQuery = true)
	List<Object[]> merchantRanking();

	@Query(value = """
			WITH trx_summary AS ( SELECT DATE(created_date) trx_date, COUNT(*) total_trx, SUM(amount) total_amount 
			FROM payment_transaction GROUP BY DATE(created_date) ) SELECT * FROM trx_summary ORDER BY trx_date DESC
						""", nativeQuery = true)
	List<Object[]> dailySummary();

}
