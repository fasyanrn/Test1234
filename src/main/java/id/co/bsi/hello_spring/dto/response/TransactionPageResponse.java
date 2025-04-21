
package id.co.bsi.hello_spring.dto.response;

import id.co.bsi.hello_spring.model.TransactionModel;
import lombok.Data;

import java.util.List;

@Data
public class TransactionPageResponse {
    private String status;
    private String message;
    private DataContent data;

    @Data
    public static class DataContent {
        private List<TransactionModel> transactions;
        private int totalPages;
        private int currentPage;
        private long totalElements;
    }
}
