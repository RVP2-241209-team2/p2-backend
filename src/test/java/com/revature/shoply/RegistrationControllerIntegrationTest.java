package com.revature.shoply;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.revature.shoply.models.enums.UserRole;
import com.revature.shoply.registration.dto.UserRegistrationRequestDTO;
import com.revature.shoply.registration.dto.UserRegistrationResponseDTO;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class RegistrationControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void registerUserTest(){
        final UserRegistrationRequestDTO newRequestDTO = new UserRegistrationRequestDTO(
			"batman",
			"Bruce",
			"Wayne",
			"batman@test.dev",
			"asdf1234",
			"111-222-333",
			UserRole.CUSTOMER
		);

        final ResponseEntity<UserRegistrationResponseDTO> response =
            template.postForEntity(
                String.format("http://localhost:%d/shoply/api/user/v1/register", port),
                "newRequestDTO",
                UserRegistrationResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNull();
        assertThat(response.getHeaders().getLocation().toString()).isEqualTo(String.format("http://localhost:%d/api/user/v1/register", port));
    }



}
