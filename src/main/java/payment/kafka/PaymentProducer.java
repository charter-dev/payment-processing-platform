package payment.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String,Object>
            kafkaTemplate;

    public void publish(
            PaymentEvent event){

        kafkaTemplate.send(
                "payment-created",
                event);
    }
}
