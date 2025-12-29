package prj3.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prj3.model.NatUsers;
import prj3.repository.NatUsersRepository;

@Controller
@RequiredArgsConstructor
public class NatProfileController {

    private final NatUsersRepository usersRepository;

    // 1. HIỂN THỊ TRANG PROFILE
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        // Lấy user từ session
        NatUsers currentUser = (NatUsers) session.getAttribute("loggedInUser");

        // Nếu chưa đăng nhập -> đá về trang login
        if (currentUser == null) {
            return "redirect:/auth/login";
        }

        // Lấy thông tin mới nhất từ DB (đề phòng session bị cũ)
        NatUsers userFromDb = usersRepository.findById(currentUser.getNatUserId()).orElse(null);
        model.addAttribute("user", userFromDb);

        return "user/profile"; // Trả về file profile.html
    }

    // 2. XỬ LÝ CẬP NHẬT THÔNG TIN
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute NatUsers formUser,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        NatUsers currentUser = (NatUsers) session.getAttribute("loggedInUser");
        if (currentUser == null) return "redirect:/auth/login";

        // Tìm user trong DB
        NatUsers existingUser = usersRepository.findById(currentUser.getNatUserId()).orElse(null);

        if (existingUser != null) {
            // Cập nhật các trường cho phép sửa
            existingUser.setNatFullname(formUser.getNatFullname());
            existingUser.setNatEmail(formUser.getNatEmail());

            // Lưu xuống DB
            NatUsers savedUser = usersRepository.save(existingUser);

            // QUAN TRỌNG: Cập nhật lại Session để hiển thị tên mới ngay lập tức
            session.setAttribute("loggedInUser", savedUser);

            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin thành công!");
        }

        return "redirect:/profile";
    }
}