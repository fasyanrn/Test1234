
package id.co.bsi.hello_spring.dto.request;

import lombok.Data;

@Data
public class TransferRequest {
    private String fromAccountnum;
    private String toAccountnum;
    private int amount;
    private String description;
    private String pin;
}
