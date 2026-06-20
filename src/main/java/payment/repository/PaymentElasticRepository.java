package payment.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import payment.elastic.PaymentDocument;

@Repository
public interface PaymentElasticRepository extends ElasticsearchRepository<PaymentDocument, String> {
	
	
	List<PaymentDocument> findByMerchantContainingIgnoreCase(String merchant);

	List<PaymentDocument> findByStatus(String status);
}