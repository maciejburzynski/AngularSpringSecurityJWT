package pl.maltoza.maltozasecurityjwt.exception;

public class ActivationCodeNotFoundException extends RuntimeException {
    public ActivationCodeNotFoundException(String activationCodeNotFound) {
        super(activationCodeNotFound);
    }
}
