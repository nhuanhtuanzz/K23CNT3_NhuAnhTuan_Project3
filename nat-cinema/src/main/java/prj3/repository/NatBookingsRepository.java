package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prj3.model.NatBookings;
import java.util.List;

public interface NatBookingsRepository extends JpaRepository<NatBookings, Long> {

    // Lấy booking của một user
    List<NatBookings> findByNatUser_NatUserIdOrderByNatCreatedAtDesc(Long userId);

    // Lấy booking theo trạng thái
    List<NatBookings> findByNatStatusOrderByNatCreatedAtDesc(NatBookings.BookingStatus status);

    // Lấy booking của một lịch chiếu
    List<NatBookings> findByNatShowtime_NatShowtimeId(Long showtimeId);

    // Đếm booking của user
    long countByNatUser_NatUserId(Long userId);

    // Đếm booking theo trạng thái
    long countByNatStatus(NatBookings.BookingStatus status);
}