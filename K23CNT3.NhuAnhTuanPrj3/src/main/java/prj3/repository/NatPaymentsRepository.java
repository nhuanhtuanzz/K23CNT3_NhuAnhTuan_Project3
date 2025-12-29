package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj3.model.NatPayments;

public interface NatPaymentsRepository extends JpaRepository<NatPayments, Long> {
    NatPayments findByNatBookingId(Long bookingId);
}


























