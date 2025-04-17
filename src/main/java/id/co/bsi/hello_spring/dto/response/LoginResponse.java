package id.co.bsi.hello_spring.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String status;
    private String token;
    private String message;
}
