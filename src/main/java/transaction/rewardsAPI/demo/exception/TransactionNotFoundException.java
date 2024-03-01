package transaction.rewardsAPI.demo.exception;

public class TransactionNotFoundException extends TransactionException {

    public TransactionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
