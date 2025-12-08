package K23CNT3.NhuAnhTuanPrj3.K23CNT3.NhuAnhTuanPrj3.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class natPayments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natPaymentId;

    private Long natBookingId;

    private Double natAmount;

    private String natMethod;

    private LocalDateTime natPaidAt;
}

