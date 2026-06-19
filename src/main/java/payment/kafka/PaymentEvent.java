package payment.kafka;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {
    
    private String trxId;
    private String customerId;
    private String merchant;
    private BigDecimal amount;
    private String status;
}
