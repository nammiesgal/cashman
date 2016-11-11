package cashman.exception;

public class InsufficentFundsException extends Exception   {

    public InsufficentFundsException(String message) {
        super(message);
    }

    public InsufficentFundsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
