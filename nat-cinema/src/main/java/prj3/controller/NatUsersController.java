package prj3.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prj3.model.NatUsers;
import prj3.service.NatUsersService;

@Controller
@RequestMapping("/admin/users") // Quản lý tất cả URL bắt đầu bằng /admin/users
@RequiredArgsConstructor        // Tự động inject Service
public class NatUsersController {

    private final NatUsersService usersService;

    // --- 1. HIỂN THỊ DANH SÁCH ---
    @GetMapping
    public String listUsers(Model model, HttpSession session) {
        // Bảo mật: Kiểm tra có phải Admin không?
        if (!isAdmin(session)) {
            return "redirect:/auth/login";
        }

        model.addAttribute("currentUser", session.getAttribute("loggedInUser"));
        model.addAttribute("users", usersService.findAll());

        // Trả về file: templates/admin/users/list.html
        return "admin/users/list";
    }

    // --- 2. XÓA USER ---
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        // Bảo mật: Chặn nếu không phải admin
        if (!isAdmin(session)) {
            return "redirect:/auth/login";
        }

        try {
            usersService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Xóa người dùng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa người dùng này.");
        }

        return "redirect:/admin/users";
    }

    // --- 3. CẬP NHẬT QUYỀN (ROLE) ---
    @PostMapping("/update-role/{id}")
    public String updateRole(@PathVariable Long id,
                             @RequestParam NatUsers.Role role,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/auth/login";
        }

        usersService.updateRole(id, role);
        redirectAttributes.addFlashAttribute("message", "Cập nhật quyền thành công!");

        return "redirect:/admin/users";
    }

    // --- HELPER: Hàm kiểm tra quyền Admin ---
    private boolean isAdmin(HttpSession session) {
        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        return user != null && NatUsers.Role.ADMIN.equals(user.getNatRole());
    }
}