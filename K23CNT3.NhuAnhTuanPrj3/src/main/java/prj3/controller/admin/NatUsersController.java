package prj3.controller.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prj3.model.NatUsers;
import prj3.service.NatUsersService;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class NatUsersController {

    private final NatUsersService usersService;

    public NatUsersController(NatUsersService usersService) {
        this.usersService = usersService;
    }

    // Kiểm tra admin trước mỗi request
    private boolean checkAdmin(HttpSession session) {
        NatUsers user = (NatUsers) session.getAttribute("USER_LOGIN");
        return user != null && user.getNatRole() == NatUsers.Role.ADMIN;
    }

    @GetMapping
    public String listUsers(HttpSession session, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login?error=Access denied";
        }

        List<NatUsers> users = usersService.getAll();
        model.addAttribute("users", users);
        return "admin/users/list";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id, HttpSession session, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login?error=Access denied";
        }

        NatUsers user = usersService.getById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        return "admin/users/detail";
    }

    @GetMapping("/create")
    public String createForm(HttpSession session, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login?error=Access denied";
        }

        model.addAttribute("user", new NatUsers());
        return "admin/users/create";
    }

    @PostMapping
    public String createUser(@ModelAttribute NatUsers user, HttpSession session) {
        if (!checkAdmin(session)) {
            return "redirect:/login?error=Access denied";
        }

        usersService.create(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login?error=Access denied";
        }

        NatUsers user = usersService.getById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        return "admin/users/edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute NatUsers user,
                             HttpSession session) {
        if (!checkAdmin(session)) {
            return "redirect:/login?error=Access denied";
        }

        usersService.update(id, user);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        if (!checkAdmin(session)) {
            return "redirect:/login?error=Access denied";
        }

        usersService.delete(id);
        return "redirect:/admin/users";
    }
}