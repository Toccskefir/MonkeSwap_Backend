package hu.wv.MonkeSwapBackend.exceptions;

public class IsRegisteredException extends RuntimeException{
    public IsRegisteredException(String errorMessage) {
        super(errorMessage);
    }
}
