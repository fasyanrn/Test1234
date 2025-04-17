package id.co.bsi.hello_spring.dto.request;

import lombok.Data;

@Data
public class TransactionRequest {
    private String dateTime;
    private String type;
    private String fromTo;
    private String description;
    private int amount;
}
