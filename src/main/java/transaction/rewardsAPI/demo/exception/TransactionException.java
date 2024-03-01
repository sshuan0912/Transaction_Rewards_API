package transaction.rewardsAPI.demo.exception;

public class TransactionException extends RuntimeException {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public TransactionException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public TransactionException() {
        super();
    }
}
