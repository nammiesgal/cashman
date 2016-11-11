package cashman.exception;

public class CurrencyFulfilmentException extends Exception {

    public CurrencyFulfilmentException(String message) {
        super(message);
    }

    public CurrencyFulfilmentException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
