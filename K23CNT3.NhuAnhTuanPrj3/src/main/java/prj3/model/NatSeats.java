package prj3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Seats")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class NatSeats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natSeatId;

    private Long natRoomId;
    private String natSeatCode;
}
