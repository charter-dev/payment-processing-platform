package payment.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentResponse implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String trxId;
    private String merchant;
    private BigDecimal amount;
    private String status;
}
