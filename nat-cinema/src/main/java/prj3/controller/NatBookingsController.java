package prj3.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prj3.dto.SeatDTO;
import prj3.model.*;
import prj3.repository.NatBookingSeatsRepository;
import prj3.repository.NatSeatsRepository;
import prj3.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NatBookingsController {

    private final NatBookingsService bookingsService;
    private final NatShowtimesService showtimesService;
    private final NatSeatsRepository seatsRepository;
    private final NatBookingSeatsRepository bookingSeatsRepository;

    public NatBookingsController(
            NatBookingsService bookingsService,
            NatShowtimesService showtimesService,
            NatSeatsRepository seatsRepository,
            NatBookingSeatsRepository bookingSeatsRepository) {
        this.bookingsService = bookingsService;
        this.showtimesService = showtimesService;
        this.seatsRepository = seatsRepository;
        this.bookingSeatsRepository = bookingSeatsRepository;
    }

    // Redirect link cũ của Admin (Giữ nguyên)
    @GetMapping("/bookings/admin")
    public String redirectOldAdminLink() {
        return "redirect:/admin/bookings/list";
    }

    // Danh sách vé của tôi (Giữ nguyên)
    @GetMapping("/bookings/my-bookings")
    public String myBookings(Model model, HttpSession session) {
        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/auth/login";

        List<NatBookings> userBookings = bookingsService.getUserBookings(user.getNatUserId());
        model.addAttribute("user", user);
        model.addAttribute("bookings", userBookings);
        return "bookings/my-bookings";
    }

    // === 1. Form chọn ghế (GET) - Giữ nguyên logic ===
    @GetMapping("/bookings/create/{showtimeId}")
    public String showBookingPage(@PathVariable Long showtimeId, Model model) {
        // Lấy suất chiếu
        NatShowtimes showtime = showtimesService.getShowtimeById(showtimeId);

        // Lấy tất cả ghế
        List<NatSeats> allSeats = seatsRepository.findByNatCinemaRoom(showtime.getNatCinemaRoom());

        // Lấy ghế đã đặt
        List<Long> bookedSeatIds = bookingSeatsRepository.findBookedSeatIdsByShowtime(showtimeId);

        // Map sang DTO
        List<SeatDTO> seatDTOs = new ArrayList<>();
        for (NatSeats seat : allSeats) {
            seatDTOs.add(SeatDTO.builder()
                    .id(seat.getNatSeatId())
                    .code(seat.getNatSeatCode())
                    .type(seat.getNatSeatType().name()) // VIP/NORMAL
                    .price(seat.getNatPrice())
                    .isMaintenance(seat.getNatStatus() == NatSeats.SeatStatus.MAINTENANCE)
                    .isBooked(bookedSeatIds.contains(seat.getNatSeatId()))
                    .build());
        }

        model.addAttribute("showtime", showtime);
        model.addAttribute("seats", seatDTOs);
        return "bookings/booking-seat";
    }

    // === 2. Xử lý đặt vé (POST) - [ĐÃ SỬA ĐỂ NHẬN NHIỀU GHẾ] ===
    @PostMapping("/bookings/create")
    public String createBooking(
            @RequestParam Long showtimeId,
            @RequestParam String seatIds,
            HttpSession session,
            Model model
    ) {
        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/auth/login";

        try {
            List<Long> seatIdList = new ArrayList<>();
            if (seatIds != null && !seatIds.isEmpty()) {
                String[] ids = seatIds.split(",");
                for (String id : ids) {
                    if (!id.trim().isEmpty()) seatIdList.add(Long.parseLong(id.trim()));
                }
            }

            // SỬA: Service cần trả về đối tượng NatBookings đã lưu
            // (Hàm createBooking trong Service của bạn đã return savedBooking rồi, nên OK)
            NatBookings newBooking = bookingsService.createBooking(user.getNatUserId(), showtimeId, seatIdList);

            // Chuyển hướng sang trang thành công với ID của vé vừa tạo
            return "redirect:/bookings/success/" + newBooking.getNatBookingId();

        } catch (RuntimeException e) {
            // ... (Phần catch giữ nguyên) ...
            return "redirect:/bookings/create/" + showtimeId + "?error=" + e.getMessage();
        }
    }

    // 2. THÊM HÀM MỚI: Trang báo thành công
    @GetMapping("/bookings/success/{id}")
    public String bookingSuccess(@PathVariable Long id, Model model, HttpSession session) {
        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/auth/login";

        try {
            NatBookings booking = bookingsService.getBookingById(id);
            // Check quyền sở hữu vé (để người khác không mò vào xem được)
            if (!booking.getNatUser().getNatUserId().equals(user.getNatUserId())) {
                return "redirect:/";
            }

            model.addAttribute("booking", booking);
            return "bookings/booking-success"; // Trả về file HTML vừa tạo
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    // Hủy vé (Giữ nguyên)
    @PostMapping("/bookings/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, HttpSession session) {
        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/auth/login";

        try {
            NatBookings booking = bookingsService.getBookingById(id);
            if (booking.getNatUser().getNatUserId().equals(user.getNatUserId()) &&
                    booking.getNatStatus() == NatBookings.BookingStatus.PENDING) {
                bookingsService.cancelBooking(id);
            }
        } catch (RuntimeException e) {}
        return "redirect:/bookings/my-bookings";
    }

    @GetMapping("/bookings/detail/{id}")
    public String bookingDetail(@PathVariable Long id, Model model, HttpSession session) {
        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/auth/login";

        try {
            NatBookings booking = bookingsService.getBookingById(id);

            // Check quyền xem vé
            if (!booking.getNatUser().getNatUserId().equals(user.getNatUserId()) &&
                    user.getNatRole() != NatUsers.Role.ADMIN) {
                return "redirect:/bookings/my-bookings";
            }

            model.addAttribute("user", user);
            model.addAttribute("booking", booking);
            return "bookings/detail";
        } catch (RuntimeException e) {
            return "redirect:/bookings/my-bookings";
        }
    }

    // ========================================================
    // === ADMIN SECTION (Giữ nguyên hoàn toàn code của bạn) ===
    // ========================================================

    @GetMapping("/admin/bookings/list")
    public String adminBookings(
            @RequestParam(required = false) String status,
            Model model,
            HttpSession session,
            jakarta.servlet.http.HttpServletRequest request
    ) {
        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        if (user == null || user.getNatRole() != NatUsers.Role.ADMIN) {
            return "redirect:/bookings/my-bookings";
        }
        List<NatBookings> bookings;
        if (status != null && !status.isEmpty() && !status.equals("ALL")) {
            try {
                NatBookings.BookingStatus bookingStatus = NatBookings.BookingStatus.valueOf(status);
                bookings = bookingsService.getBookingsByStatus(bookingStatus);
                model.addAttribute("selectedStatus", status);
            } catch (IllegalArgumentException e) {
                bookings = bookingsService.getAllBookings();
                model.addAttribute("selectedStatus", "ALL");
            }
        } else {
            bookings = bookingsService.getAllBookings();
            model.addAttribute("selectedStatus", "ALL");
        }

        model.addAttribute("user", user);
        model.addAttribute("bookings", bookings);
        model.addAttribute("statuses", NatBookings.BookingStatus.values());
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/bookings/admin-list";
    }

    @PostMapping("/admin/bookings/update-status")
    public String adminUpdateStatus(@RequestParam Long bookingId, @RequestParam String newStatus, HttpSession session) {
        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        if (user == null || user.getNatRole() != NatUsers.Role.ADMIN) {
            return "redirect:/auth/login";
        }
        try {
            NatBookings.BookingStatus status = NatBookings.BookingStatus.valueOf(newStatus);
            bookingsService.updateBookingStatus(bookingId, status);
        } catch (Exception e) {}
        return "redirect:/admin/bookings/list";
    }
}