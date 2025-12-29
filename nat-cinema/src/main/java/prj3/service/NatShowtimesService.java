package prj3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj3.model.NatShowtimes;
import prj3.repository.NatShowtimesRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NatShowtimesService {

    private final NatShowtimesRepository showtimesRepository;

    // ... (Các hàm get giữ nguyên) ...

    public List<NatShowtimes> getUpcomingShowtimes() {
        return showtimesRepository.findByNatStartTimeAfterOrderByNatStartTimeAsc(LocalDateTime.now());
    }

    public List<NatShowtimes> getShowtimesByMovieId(Long movieId) {
        return showtimesRepository.findByNatMovie_NatMovieIdAndNatStartTimeAfterOrderByNatStartTimeAsc(movieId, LocalDateTime.now());
    }

    public List<NatShowtimes> getAllShowtimesDesc() {
        return showtimesRepository.findAllByOrderByNatStartTimeDesc();
    }

    public boolean checkRoomBusy(Long roomId, LocalDateTime start, LocalDateTime end) {
        return showtimesRepository.checkRoomBusy(roomId, start, end);
    }

    public NatShowtimes getShowtimeById(Long id) {
        return showtimesRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    // === SỬA HÀM NÀY ===
    public void createShowtime(NatShowtimes showtime) {
        // 1. Tính toán giờ kết thúc (EndTime = StartTime + Duration)
        if (showtime.getNatMovie() != null && showtime.getNatStartTime() != null) {
            int duration = showtime.getNatMovie().getNatDuration(); // Lấy thời lượng phim (phút)

            // Cộng thêm thời lượng vào giờ chiếu để ra giờ kết thúc
            LocalDateTime endTime = showtime.getNatStartTime().plusMinutes(duration);

            // Lưu vào object
            showtime.setNatEndTime(endTime);
        }

        // 2. Lưu xuống DB
        showtimesRepository.save(showtime);
    }

    public void deleteShowtime(Long id) {
        showtimesRepository.deleteById(id);
    }
}