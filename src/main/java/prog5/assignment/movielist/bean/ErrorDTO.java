package prog5.assignment.movielist.bean;
// Creating DTO Bean

import java.time.LocalDateTime;

public record ErrorDTO(
        int status,
        LocalDateTime timestamp,
        String errorMessage
) {
}
