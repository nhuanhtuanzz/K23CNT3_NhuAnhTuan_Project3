package prj3.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Showtimes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NatShowtimes { // Đã thêm 's'

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natShowtimeId;

    @ManyToOne
    @JoinColumn(name = "natMovieId")
    private NatMovies natMovie;

    @ManyToOne
    @JoinColumn(name = "natRoomId")
    private NatCinemaRooms natCinemaRoom;

    private LocalDateTime natStartTime;
    private LocalDateTime natEndTime;
    private Double natPrice;
}