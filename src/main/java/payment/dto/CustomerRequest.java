package payment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRequest {

	@NotBlank(message = "name is required")
	private String name;
	@NotBlank(message = "email is required")
	private String email;
	@NotBlank(message = "phone is required")
	private String phone;
}
