package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj3.model.NatSeats;

import java.util.List;

public interface NatSeatsRepository extends JpaRepository<NatSeats, Long> {
    List<NatSeats> findByNatRoomId(Long roomId);
}
