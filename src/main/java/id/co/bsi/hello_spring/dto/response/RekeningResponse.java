package id.co.bsi.hello_spring.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RekeningResponse {
    private List<RekeningData> data = new ArrayList<RekeningData>();

    @lombok.Data
    public static class RekeningData {
        private String account_number;
        private String account_name;
    }
}
