package hu.wv.MonkeSwapBackend.exceptions;

public class IsEmptyException extends RuntimeException{
    public IsEmptyException(String subject) {
        super(subject + " is empty");
    }
}
