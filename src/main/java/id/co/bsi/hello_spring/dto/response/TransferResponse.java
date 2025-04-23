
package id.co.bsi.hello_spring.dto.response;

public class TransferResponse {
    private String status;
    private String message;
    private String fromName;
    private String fromAccountnum;
    private String toName;
    private String toAccountnum;
    private Integer amount;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromAccountnum() {
        return fromAccountnum;
    }

    public void setFromAccountnum(String fromAccountnum) {
        this.fromAccountnum = fromAccountnum;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getToAccountnum() {
        return toAccountnum;
    }

    public void setToAccountnum(String toAccountnum) {
        this.toAccountnum = toAccountnum;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
