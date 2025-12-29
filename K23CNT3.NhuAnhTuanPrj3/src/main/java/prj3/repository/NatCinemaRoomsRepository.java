package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj3.model.NatCinemaRooms;

public interface NatCinemaRoomsRepository extends JpaRepository<NatCinemaRooms, Long> {
}
