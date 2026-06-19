package payment.elastic;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(indexName = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDocument {

    @Id
    private String trxId;

    private String customerId;

    private String merchant;

    private BigDecimal amount;

    private String status;
}
