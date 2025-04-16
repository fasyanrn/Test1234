package id.co.bsi.hello_spring.dto.request;

import lombok.Data;

@Data
public class TopUpRequest {
    private int amount;
    private String from;
    private String notes;
}
