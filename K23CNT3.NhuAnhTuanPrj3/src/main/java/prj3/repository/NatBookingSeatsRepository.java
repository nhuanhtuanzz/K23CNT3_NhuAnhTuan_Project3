package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prj3.model.NatBookingSeats;

import java.util.List;

@Repository
public interface NatBookingSeatsRepository extends JpaRepository<NatBookingSeats, Long> {

    List<NatBookingSeats> findByNatBookingId(Long bookingId);
}

