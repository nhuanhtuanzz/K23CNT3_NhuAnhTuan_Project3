package prj3.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Showtimes")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class NatShowtimes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natShowtimeId;

    private Long natMovieId;
    private Long natRoomId;
    private LocalDateTime natStartTime;
}
