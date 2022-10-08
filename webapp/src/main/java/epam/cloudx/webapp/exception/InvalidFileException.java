package epam.cloudx.webapp.exception;

public class InvalidFileException extends RuntimeException {
    public InvalidFileException() {
    }

    public InvalidFileException(String message) {
        super(message);
    }
}
