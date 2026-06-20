package payment.service;

import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import payment.elastic.PaymentDocument;
import payment.entity.PaymentTransaction;
import payment.repository.PaymentElasticRepository;
import payment.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
public class ElasticIndexService {

    private final PaymentRepository paymentRepository;

    private final PaymentElasticRepository elasticRepository;

    public String reindexAll() {

        List<PaymentDocument> docs =
                paymentRepository.findAll()
                        .stream()
                        .map(this::toDocument)
                        .toList();

        elasticRepository.saveAll(docs);

        return docs.size() + " indexed";
    }

    private PaymentDocument toDocument(
            PaymentTransaction trx) {

        return PaymentDocument.builder()
                .trxId(trx.getTrxId())
                .customerId(trx.getCustomer().getCustomerId())
                .merchant(trx.getMerchant())
                .amount(trx.getAmount())
                .status(trx.getStatus())
                .createdDate(
                        trx.getCreatedDate()
                           .atZone(ZoneId.systemDefault())
                           .toInstant()
                    )
                .build();
    }
}
