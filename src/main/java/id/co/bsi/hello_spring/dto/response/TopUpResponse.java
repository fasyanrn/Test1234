
package id.co.bsi.hello_spring.dto.response;

import lombok.Data;

@Data
public class TopUpResponse {
    private String status;
    private String message;
    private OptionTopUp data;

    @Data
    public static class OptionTopUp {
        private String accountnum;
        private int balance;
    }
}
