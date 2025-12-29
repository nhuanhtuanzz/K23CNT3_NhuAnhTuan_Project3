package prj3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "BookingSeats")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class NatBookingSeats {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natBookingSeatId;

    @ManyToOne
    @JoinColumn(name = "natBookingId")
    private NatBookings natBooking;

    @ManyToOne
    @JoinColumn(name = "natSeatId")
    private NatSeats natSeat;
}