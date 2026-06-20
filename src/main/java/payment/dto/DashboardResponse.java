package payment.dto;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {

    private long totalTransaction;

    private BigDecimal totalAmount;

    private BigDecimal averageAmount;

    private Map<String, Long> transactionByStatus;

    private Map<String, BigDecimal> amountByMerchant;
}
