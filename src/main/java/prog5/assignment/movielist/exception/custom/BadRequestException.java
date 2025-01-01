package prog5.assignment.movielist.exception.custom;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

}
