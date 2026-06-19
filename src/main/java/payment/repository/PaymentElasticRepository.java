package payment.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import payment.elastic.PaymentDocument;

@Repository
public interface PaymentElasticRepository
extends ElasticsearchRepository<
        PaymentDocument,
        String> {
}