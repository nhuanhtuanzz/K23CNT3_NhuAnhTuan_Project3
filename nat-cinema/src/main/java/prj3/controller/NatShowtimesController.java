package prj3.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prj3.model.*;
import prj3.service.*;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class NatShowtimesController {

    private final NatShowtimesService showtimesService;
    private final NatMoviesService moviesService;
    private final NatCinemaRoomsService roomsService;

    // ==========================================
    // 🟢 PHẦN 1: PUBLIC (KHÁCH XEM)
    // ==========================================

    @GetMapping("/showtimes")
    public String listPublic(Model model, HttpSession session) {
        addUserToModel(model, session);
        model.addAttribute("showtimes", showtimesService.getUpcomingShowtimes());
        return "showtimes/list";
    }

    @GetMapping("/showtimes/movie/{movieId}")
    public String listByMovie(@PathVariable Long movieId, Model model, HttpSession session) {
        addUserToModel(model, session);
        try {
            model.addAttribute("movie", moviesService.getMovieById(movieId));
            model.addAttribute("showtimes", showtimesService.getShowtimesByMovieId(movieId));
            return "showtimes/by-movie";
        } catch (RuntimeException e) {
            return "redirect:/showtimes";
        }
    }

    // ==========================================
    // 🔴 PHẦN 2: ADMIN ONLY
    // ==========================================

    @GetMapping("/admin/showtimes")
    public String manageList(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/auth/login";

        addUserToModel(model, session);
        model.addAttribute("showtimes", showtimesService.getAllShowtimesDesc());
        return "admin/showtimes/list";
    }

    @GetMapping("/admin/showtimes/create")
    public String createForm(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/auth/login";
        addUserToModel(model, session);

        model.addAttribute("showtime", new NatShowtimes());
        model.addAttribute("movies", moviesService.getAllMovies());
        model.addAttribute("rooms", roomsService.getAllRooms());
        return "admin/showtimes/create";
    }

    // === [SỬA LỖI Ở HÀM NÀY] ===
    @PostMapping("/admin/showtimes/create")
    public String create(@RequestParam Long movieId,
                         @RequestParam Long roomId,
                         @RequestParam LocalDateTime natStartTime,
                         HttpSession session,
                         Model model) {
        if (!isAdmin(session)) return "redirect:/auth/login";

        try {
            NatMovies movie = moviesService.getMovieById(movieId);
            NatCinemaRooms room = roomsService.getRoomById(roomId);

            // Tính toán giờ kết thúc
            int duration = movie.getNatDuration(); // Giả sử duration là phút
            LocalDateTime endTime = natStartTime.plusMinutes(duration + 15); // +15p dọn dẹp

            // Check trùng lịch (Lưu ý: Service cũng phải dùng đúng tên biến nhé)
            if (showtimesService.checkRoomBusy(roomId, natStartTime, endTime)) {
                throw new RuntimeException("Phòng chiếu bị trùng lịch!");
            }

            NatShowtimes showtime = new NatShowtimes();
            showtime.setNatMovie(movie);

            // SỬA: Đổi setNatRoom -> setNatCinemaRoom
            showtime.setNatCinemaRoom(room);

            showtime.setNatStartTime(natStartTime);

            showtimesService.createShowtime(showtime);
            return "redirect:/admin/showtimes";

        } catch (Exception e) {
            addUserToModel(model, session);
            model.addAttribute("error", "Lỗi: " + e.getMessage());
            // Trả lại form để điền lại
            model.addAttribute("showtime", new NatShowtimes());
            model.addAttribute("movies", moviesService.getAllMovies());
            model.addAttribute("rooms", roomsService.getAllRooms());
            return "admin/showtimes/create";
        }
    }

    @GetMapping("/admin/showtimes/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/auth/login";
        showtimesService.deleteShowtime(id);
        return "redirect:/admin/showtimes";
    }

    // Helper functions
    private boolean isAdmin(HttpSession session) {
        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        return user != null && NatUsers.Role.ADMIN.equals(user.getNatRole());
    }
    private void addUserToModel(Model model, HttpSession session) {
        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        if (user != null) model.addAttribute("user", user);
    }
}