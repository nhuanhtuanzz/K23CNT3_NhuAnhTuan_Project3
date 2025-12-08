package K23CNT3.NhuAnhTuanPrj3.K23CNT3.NhuAnhTuanPrj3.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class natBookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natBookingId;

    private Long natUserId;

    private Long natShowtimeId;

    @Enumerated(EnumType.STRING)
    private Status natStatus = Status.PENDING;

    private LocalDateTime natCreatedAt = LocalDateTime.now();

    public enum Status {
        PENDING,
        PAID,
        CANCELLED
    }
}
