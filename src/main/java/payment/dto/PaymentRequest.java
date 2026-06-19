package payment.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotBlank
    private String customerId;

    @NotBlank
    private String merchant;

    @NotNull
    private BigDecimal amount;
}
