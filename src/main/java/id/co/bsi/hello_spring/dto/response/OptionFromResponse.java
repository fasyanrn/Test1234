package id.co.bsi.hello_spring.dto.response;

import lombok.Data;

@Data
public class OptionFromResponse {
private String option;

    @lombok.Data
    public static class OptionTopUp {
        private String option;
    }
}
