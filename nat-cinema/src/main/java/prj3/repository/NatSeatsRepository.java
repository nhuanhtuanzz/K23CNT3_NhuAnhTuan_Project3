package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prj3.model.NatCinemaRooms;
import prj3.model.NatSeats;
import java.util.List;

@Repository
public interface NatSeatsRepository extends JpaRepository<NatSeats, Long> {

    // === SỬA LẠI TÊN HÀM Ở ĐÂY ===
    // Đổi NatRoom -> NatCinemaRoom (để khớp với tên biến trong Entity NatSeats)

    // 1. Tìm theo ID phòng (Sửa tên hàm)
    List<NatSeats> findByNatCinemaRoom_NatRoomId(Long roomId);

    // 2. Tìm theo Object phòng (Sửa tên hàm)
    List<NatSeats> findByNatCinemaRoom(NatCinemaRooms room);

    // 3. Tìm ghế theo mã ghế trong phòng (Sửa tên hàm)
    NatSeats findByNatCinemaRoom_NatRoomIdAndNatSeatCode(Long roomId, String seatCode);

    // 4. Kiểm tra tồn tại (Sửa tên hàm)
    boolean existsByNatCinemaRoom_NatRoomId(Long natRoomId);

    // 5. Đếm số ghế (Sửa tên hàm)
    long countByNatCinemaRoom_NatRoomId(Long roomId);
}