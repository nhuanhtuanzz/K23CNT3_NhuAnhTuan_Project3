package K23CNT3.NhuAnhTuanPrj3.K23CNT3.NhuAnhTuanPrj3.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Showtimes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class natShowtimes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natShowtimeId;

    private Long natMovieId;

    private Long natRoomId;

    private LocalDateTime natStartTime;
}

