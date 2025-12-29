package prj3.auth;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prj3.model.NatUsers;
import prj3.service.NatUsersService;

@Controller
@RequestMapping("/auth")
public class NatAuthController {

    private final NatUsersService usersService;

    public NatAuthController(NatUsersService usersService) {
        this.usersService = usersService;
    }

    // GET: Hiển thị form đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        // Tạo object rỗng để hứng dữ liệu từ form
        model.addAttribute("user", new NatUsers());
        return "auth/register"; // Trả về file templates/auth/register.html
    }

    // POST: Xử lý khi bấm nút Đăng ký
    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") NatUsers user,
                                  Model model) {
        try {
            // Gọi service để lưu user (đã có logic check trùng email trong service)
            usersService.registerUser(user);

            // Thành công -> Chuyển hướng về trang đăng nhập
            return "redirect:/auth/login?registerSuccess";
        } catch (RuntimeException e) {
            // Nếu lỗi (ví dụ trùng email) -> Ở lại trang đăng ký và hiện thông báo lỗi
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    // GET: Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    // POST: Xử lý đăng nhập
    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        // Kiểm tra email và password
        if (!usersService.authenticate(email, password)) {
            model.addAttribute("error", "Email hoặc mật khẩu không đúng!");
            return "auth/login";
        }

        // Lấy thông tin user
        NatUsers user = usersService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Lưu thông tin user vào session
        session.setAttribute("loggedInUser", user);
        session.setAttribute("isAdmin", user.getNatRole() == NatUsers.Role.ADMIN);

        System.out.println("✅ Login successful: " + email + " - Role: " + user.getNatRole());

        // Chuyển hướng theo role
        if (user.getNatRole() == NatUsers.Role.ADMIN) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/";
        }
    }

    // Đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa toàn bộ session
        return "redirect:/auth/login";
    }

    // Kiểm tra session (debug)
    @GetMapping("/check")
    @ResponseBody
    public String checkSession(HttpSession session) {
        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        if (user != null) {
            return "Logged in as: " + user.getNatEmail() +
                    " | Role: " + user.getNatRole();
        }
        return "Not logged in";
    }
}