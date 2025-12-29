package prj3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Seats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NatSeats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natSeatId;

    @ManyToOne
    @JoinColumn(name = "natRoomId")
    private NatCinemaRooms natCinemaRoom;

    private String natSeatCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "natSeatType")
    private SeatType natSeatType;

    @Enumerated(EnumType.STRING)
    private SeatStatus natStatus = SeatStatus.AVAILABLE;

    private Double natPrice;

    public enum SeatType { NORMAL, VIP } // Enum giữ nguyên
    public enum SeatStatus { AVAILABLE, MAINTENANCE }
}