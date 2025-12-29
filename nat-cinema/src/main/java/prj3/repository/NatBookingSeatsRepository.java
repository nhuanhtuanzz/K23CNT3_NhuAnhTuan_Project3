package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prj3.model.NatBookingSeats;
import java.util.List;

public interface NatBookingSeatsRepository extends JpaRepository<NatBookingSeats, Long> {

    // Kiểm tra xem 1 ghế cụ thể đã được đặt trong suất chiếu đó chưa (Trừ trường hợp đã hủy)
    @Query("SELECT bs FROM NatBookingSeats bs " +
            "WHERE bs.natBooking.natShowtime.natShowtimeId = :showtimeId " +
            "AND bs.natSeat.natSeatId = :seatId " +
            "AND bs.natBooking.natStatus <> 'CANCELLED'")
    List<NatBookingSeats> findBookedSeat(@Param("showtimeId") Long showtimeId,
                                         @Param("seatId") Long seatId);

    // Lấy danh sách ID các ghế đã được đặt trong suất chiếu (Dùng để hiển thị lên View)
    @Query("SELECT bs.natSeat.natSeatId FROM NatBookingSeats bs " +
            "WHERE bs.natBooking.natShowtime.natShowtimeId = :showtimeId " +
            "AND bs.natBooking.natStatus <> 'CANCELLED'")
    List<Long> findBookedSeatIdsByShowtime(@Param("showtimeId") Long showtimeId);
}