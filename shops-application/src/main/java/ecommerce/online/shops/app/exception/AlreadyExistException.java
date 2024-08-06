package ecommerce.online.shops.app.exception;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String msg) {
        super(msg);
    }
}
