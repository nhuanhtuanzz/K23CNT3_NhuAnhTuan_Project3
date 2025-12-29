package prj3.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Bookings")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class NatBookings {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natBookingId;

    @Column(unique = true)
    private String natBookingCode;

    @ManyToOne @JoinColumn(name = "natUserId")
    private NatUsers natUser;

    @ManyToOne @JoinColumn(name = "natShowtimeId")
    private NatShowtimes natShowtime;

    @Enumerated(EnumType.STRING)
    private BookingStatus natStatus; // PENDING, PAID, CANCELLED

    private LocalDateTime natCreatedAt;

    @OneToMany(mappedBy = "natBooking", cascade = CascadeType.ALL)
    private List<NatBookingSeats> bookingSeats;

    public enum BookingStatus { PENDING, PAID, CANCELLED }
}