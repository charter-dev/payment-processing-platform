package payment.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentResponse {

    private String trxId;
    private String merchant;
    private BigDecimal amount;
    private String status;
}
