package id.co.bsi.hello_spring.dto.response;

import lombok.Data;

@Data
public class TransactionResponse {
    private String dateTime;
    private String type;
    private String fromTo;
    private String description;
    private int amount;
    private String status;
    private String message;
}
