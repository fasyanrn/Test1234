package id.co.bsi.hello_spring.dto.request;

import lombok.Data;

@Data
public class RekeningRequest {
    private String account_number;
    private String account_name;
}
