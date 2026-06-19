package payment;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;


@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_create_customer_via_api() throws Exception {

    	String requestBody = """
    			{
    			    "name": "John",
    			    "email": "john@mail.com",
    			    "phone": "08123"
    			}
    			""";

    			mockMvc.perform(post("/api/customers")
    			        .contentType(MediaType.APPLICATION_JSON)
    			        .content(requestBody))
    			        .andExpect(status().isOk())
    			        .andExpect(jsonPath("$.name").value("John"))
    			        .andExpect(jsonPath("$.customerId").exists());
    }

    @Test
    void should_fail_when_customer_id_missing() throws Exception {

    	String requestBody = """
    			{
    			    "name": "John"
    			}
    			""";

    			mockMvc.perform(post("/api/customers")
    			        .contentType(MediaType.APPLICATION_JSON)
    			        .content(requestBody))
    			        .andExpect(status().isBadRequest());
    }
    
}
