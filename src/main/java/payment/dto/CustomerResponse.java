package payment.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomerResponse implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customerId;
    private String name;
    private String email;
    private String phone;
}
