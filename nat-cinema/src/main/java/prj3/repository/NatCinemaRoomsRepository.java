package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj3.model.NatCinemaRooms;

public interface NatCinemaRoomsRepository extends JpaRepository<NatCinemaRooms, Long> {

    // Tìm phòng theo tên
    NatCinemaRooms findByNatRoomName(String roomName);

    // Kiểm tra tên phòng đã tồn tại chưa
    boolean existsByNatRoomName(String roomName);
}