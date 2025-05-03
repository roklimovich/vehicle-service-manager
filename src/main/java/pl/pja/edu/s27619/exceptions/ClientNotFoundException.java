package pl.pja.edu.s27619.exceptions;

public class ClientNotFoundException extends IllegalArgumentException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
