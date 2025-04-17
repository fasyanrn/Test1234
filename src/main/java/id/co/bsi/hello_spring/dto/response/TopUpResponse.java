package id.co.bsi.hello_spring.dto.response;

import lombok.Data;

@Data
public class TopUpResponse {
    private String status;
    private String message;

    public static class OptionTopUp {
    }
}