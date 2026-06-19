package payment.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import payment.elastic.PaymentDocument;
import payment.repository.PaymentElasticRepository;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentElasticRepository elasticRepository;
    
    private static final Logger log = LoggerFactory.getLogger(PaymentConsumer.class);

    @KafkaListener(topics = "payment-topic", groupId = "payment-group")
    public void consume(PaymentEvent event) {

        log.info("Received event trxId={}", event.getTrxId());

        PaymentDocument doc = PaymentDocument.builder()
                .trxId(event.getTrxId())
                .customerId(event.getCustomerId())
                .merchant(event.getMerchant())
                .amount(event.getAmount())
                .status(event.getStatus())
                .build();

        elasticRepository.save(doc);
    }
}

