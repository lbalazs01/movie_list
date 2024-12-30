package prog5.assignment.movielist.bean;

import java.time.LocalDateTime;

public record ErrorDTO(
        int status,
        LocalDateTime timestamp,
        String errorMessage
) {
}
