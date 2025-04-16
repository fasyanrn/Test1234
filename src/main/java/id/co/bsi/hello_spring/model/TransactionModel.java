package id.co.bsi.hello_spring.model;

import lombok.Data;

@Data
public class TransactionModel {
    private String dateTime;
    private String type;
    private String fromTo;
    private String description;
    private int amount;
}
