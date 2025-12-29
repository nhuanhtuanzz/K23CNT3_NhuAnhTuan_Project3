package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj3.model.NatBookings;

import java.util.List;

public interface NatBookingsRepository extends JpaRepository<NatBookings, Long> {
    List<NatBookings> findByNatUserId(Long userId);
}
