package K23CNT3.NhuAnhTuanPrj3.K23CNT3.NhuAnhTuanPrj3.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CinemaRooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class natCinemaRooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natRoomId;

    private String natRoomName;

    private Integer natTotalSeats;
}
