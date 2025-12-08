package K23CNT3.NhuAnhTuanPrj3.K23CNT3.NhuAnhTuanPrj3.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BookingSeats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class natBookingSeats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natBookingSeatId;

    private Long natBookingId;

    private Long natSeatId;
}

