package prj3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj3.model.*;
import prj3.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID; // <--- Đã thêm thư viện để sinh mã vé

@Service
@Transactional
public class NatBookingsService {

    private final NatBookingsRepository bookingsRepository;
    private final NatUsersRepository usersRepository;
    private final NatShowtimesRepository showtimesRepository;
    private final NatSeatsRepository seatsRepository;
    private final NatBookingSeatsRepository bookingSeatsRepository;

    public NatBookingsService(
            NatBookingsRepository bookingsRepository,
            NatUsersRepository usersRepository,
            NatShowtimesRepository showtimesRepository,
            NatSeatsRepository seatsRepository,
            NatBookingSeatsRepository bookingSeatsRepository) {
        this.bookingsRepository = bookingsRepository;
        this.usersRepository = usersRepository;
        this.showtimesRepository = showtimesRepository;
        this.seatsRepository = seatsRepository;
        this.bookingSeatsRepository = bookingSeatsRepository;
    }

    // Tạo booking mới (Đã tích hợp Thanh toán giả + Mã vé)
    public NatBookings createBooking(Long userId, Long showtimeId, List<Long> seatIds) {
        // 1. Validate input
        if (seatIds == null || seatIds.isEmpty()) {
            throw new RuntimeException("Vui lòng chọn ít nhất một ghế!");
        }

        NatUsers user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        NatShowtimes showtime = showtimesRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        List<NatSeats> seatsToBook = new ArrayList<>();

        for (Long seatId : seatIds) {
            NatSeats seat = seatsRepository.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Seat not found: " + seatId));

            // Check phòng (Logic bạn vừa sửa đúng rồi)
            Long roomOfSeat = seat.getNatCinemaRoom().getNatRoomId();
            Long roomOfShowtime = showtime.getNatCinemaRoom().getNatRoomId();

            if (!roomOfSeat.equals(roomOfShowtime)) {
                throw new RuntimeException("Ghế " + seat.getNatSeatCode() + " không thuộc phòng chiếu này.");
            }

            // Check ghế đã bị đặt chưa
            List<NatBookingSeats> existingBookings = bookingSeatsRepository.findBookedSeat(showtimeId, seatId);
            if (!existingBookings.isEmpty()) {
                throw new RuntimeException("Ghế " + seat.getNatSeatCode() + " đã có người nhanh tay đặt trước!");
            }

            seatsToBook.add(seat);
        }

        // 2. TẠO VÉ (Logic mới)

        String randomCode = "NAT-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();//ngau nhien ma so

        NatBookings booking = NatBookings.builder()
                .natUser(user)
                .natShowtime(showtime)
                .natStatus(NatBookings.BookingStatus.PAID)
                .natBookingCode(randomCode) // Lưu mã vé
                .natCreatedAt(LocalDateTime.now())
                .build();

        NatBookings savedBooking = bookingsRepository.save(booking);

        // 3. Lưu danh sách ghế vào vé
        List<NatBookingSeats> bookingSeatsList = new ArrayList<>();
        for (NatSeats seat : seatsToBook) {
            bookingSeatsList.add(NatBookingSeats.builder()
                    .natBooking(savedBooking)
                    .natSeat(seat)
                    .build());
        }
        bookingSeatsRepository.saveAll(bookingSeatsList);
        savedBooking.setBookingSeats(bookingSeatsList);

        return savedBooking;
    }

    // Các hàm khác giữ nguyên
    public List<NatBookings> getUserBookings(Long userId) {
        return bookingsRepository.findByNatUser_NatUserIdOrderByNatCreatedAtDesc(userId);
    }

    public NatBookings getBookingById(Long bookingId) {
        return bookingsRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public NatBookings updateBookingStatus(Long bookingId, NatBookings.BookingStatus status) {
        NatBookings booking = getBookingById(bookingId);
        booking.setNatStatus(status);
        return bookingsRepository.save(booking);
    }

    public NatBookings cancelBooking(Long bookingId) {
        return updateBookingStatus(bookingId, NatBookings.BookingStatus.CANCELLED);
    }

    public List<NatBookings> getAllBookings() { return bookingsRepository.findAll(); }

    public List<NatBookings> getBookingsByStatus(NatBookings.BookingStatus status) {
        return bookingsRepository.findByNatStatusOrderByNatCreatedAtDesc(status);
    }
}