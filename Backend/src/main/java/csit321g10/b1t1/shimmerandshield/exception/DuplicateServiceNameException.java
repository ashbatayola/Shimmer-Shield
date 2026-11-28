package csit321g10.b1t1.shimmerandshield.exception;

public class DuplicateServiceNameException extends RuntimeException {
    public DuplicateServiceNameException(String name) {
        super("Service name already in use: " + name);
    }
}
