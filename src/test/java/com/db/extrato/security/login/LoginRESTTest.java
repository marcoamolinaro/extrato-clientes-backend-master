package com.db.extrato.security.login;

import org.springframework.boot.test.context.SpringBootTest;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest
public class LoginRESTTest {
/*
  @Inject
  LoginREST login;

  @Test
  void whenLoginThenShouldReturnJwtToken() throws Exception {
    // Arrange
    JwtRequest request = new JwtRequest("extderiv", "123456");

    // Act
    ResponseEntity<?> response = login.login(request);

    // Assert
    notNull(response, "Response should not be null");
    notNull(response.getBody(), "Response body should not be null");
    notEmpty(new Object[]{response.getBody()}, "Response body should not be empty");
    isTrue(response.getStatusCode().value() == 200, "Status code should be 200");
  }
 */
}
