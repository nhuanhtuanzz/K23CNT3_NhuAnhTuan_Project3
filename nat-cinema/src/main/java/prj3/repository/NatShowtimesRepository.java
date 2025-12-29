package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prj3.model.NatMovies;
import prj3.model.NatShowtimes;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NatShowtimesRepository extends JpaRepository<NatShowtimes, Long> {
    List<NatShowtimes> findByNatMovie(NatMovies movie);
    // 1. Hàm cho trang chủ (Lấy lịch sắp chiếu)
    List<NatShowtimes> findByNatStartTimeAfterOrderByNatStartTimeAsc(LocalDateTime time);

    // 2. Hàm cho trang Quản lý Admin (Mới nhất lên đầu)
    List<NatShowtimes> findAllByOrderByNatStartTimeDesc();

    // 3. [QUAN TRỌNG] Hàm cho trang Chi tiết phim
    // Lấy lịch của phim X, nhưng chỉ lấy lịch TƯƠNG LAI, sắp xếp SÁNG -> TỐI
    List<NatShowtimes> findByNatMovie_NatMovieIdAndNatStartTimeAfterOrderByNatStartTimeAsc(Long movieId, LocalDateTime time);

    // 4. Hàm check trùng phòng (Giữ nguyên của bạn)
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM NatShowtimes s " +
            "WHERE s.natCinemaRoom.natRoomId = :roomId " +
            "AND ((s.natStartTime < :endTime) AND (s.natEndTime > :startTime))")
    boolean checkRoomBusy(@Param("roomId") Long roomId,
                          @Param("startTime") LocalDateTime startTime,
                          @Param("endTime") LocalDateTime endTime);
}