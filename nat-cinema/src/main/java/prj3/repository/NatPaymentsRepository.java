package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj3.model.NatPayments;
import java.util.List;

public interface NatPaymentsRepository extends JpaRepository<NatPayments, Long> {

    // Tìm payment của một booking
    NatPayments findByNatBooking_NatBookingId(Long bookingId);

    // Lấy payment theo phương thức
    List<NatPayments> findByNatMethod(String method);

}