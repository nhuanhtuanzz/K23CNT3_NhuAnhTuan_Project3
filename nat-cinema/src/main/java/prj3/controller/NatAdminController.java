package prj3.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import prj3.model.NatUsers;
import prj3.repository.*;

@Controller
@RequestMapping("/admin") // Tất cả URL trong này đều bắt đầu bằng /admin
@RequiredArgsConstructor  // Tự động inject các Repository
public class NatAdminController {

    private final NatMoviesRepository moviesRepository;
    private final NatUsersRepository usersRepository;
    private final NatBookingsRepository bookingsRepository;
    private final NatShowtimesRepository showtimesRepository;

    // --- 1. DASHBOARD (TRANG TỔNG QUAN) ---
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/auth/login"; // Kiểm tra quyền

        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        model.addAttribute("user", user);

        // Thống kê
        model.addAttribute("movieCount", moviesRepository.count());
        model.addAttribute("userCount", usersRepository.count());
        model.addAttribute("bookingCount", bookingsRepository.count());
        model.addAttribute("showtimeCount", showtimesRepository.count());

        return "admin/dashboard"; // Trả về file: templates/admin/dashboard.html
    }
    // --- HELPER METHOD: KIỂM TRA QUYỀN ADMIN ---
    private boolean isAdmin(HttpSession session) {
        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        return user != null && NatUsers.Role.ADMIN.equals(user.getNatRole());
    }
}