package prj3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj3.model.NatCinemaRooms;
import prj3.model.NatSeats;
import prj3.repository.NatSeatsRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class NatSeatsService {
    private final NatSeatsRepository seatsRepository;

    public NatSeatsService(NatSeatsRepository seatsRepository) {
        this.seatsRepository = seatsRepository;
    }

    @Transactional
    public void generateSeatsForRoom(NatCinemaRooms room) {
        if (seatsRepository.existsByNatCinemaRoom_NatRoomId(room.getNatRoomId())) {
            return; // Đã có ghế thì không tạo lại
        }

        List<NatSeats> seatsList = new ArrayList<>();
        String[] rowLetters = {"A", "B", "C", "D", "E"};

        for (String row : rowLetters) {
            for (int i = 1; i <= 10; i++) {

                // Logic xác định ghế VIP (Giữa rạp)
                boolean isCenterRow = row.equals("B") || row.equals("C") || row.equals("D");
                boolean isCenterCol = (i >= 4 && i <= 7);

                // 2. SỬA: Dùng Enum thay vì String
                NatSeats.SeatType type = (isCenterRow && isCenterCol)
                        ? NatSeats.SeatType.VIP
                        : NatSeats.SeatType.NORMAL;

                NatSeats seat = NatSeats.builder()
                        .natCinemaRoom(room) // 3. SỬA: natRoom -> natCinemaRoom (Quan trọng nhất)
                        .natSeatCode(row + i) // Ví dụ: A1, A2
                        .natSeatType(type)        // 4. SỬA: natSeatType -> natType (theo Entity)
                        .natStatus(NatSeats.SeatStatus.AVAILABLE) // Mặc định là dùng được
                        .natPrice(type == NatSeats.SeatType.VIP ? 100000.0 : 60000.0) // Set giá mẫu
                        .build();

                seatsList.add(seat);
            }
        }

        seatsRepository.saveAll(seatsList);
    }
}