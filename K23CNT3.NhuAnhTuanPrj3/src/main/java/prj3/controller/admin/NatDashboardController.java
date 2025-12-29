package prj3.controller.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import prj3.model.NatUsers;

@Controller
@RequestMapping("/admin")
public class NatDashboardController {

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        NatUsers user = (NatUsers) session.getAttribute("USER_LOGIN");

        if (user == null) {
            return "redirect:/login?error=Please login first";
        }

        if (user.getNatRole() != NatUsers.Role.ADMIN) {
            return "redirect:/home?error=Access denied";
        }

        model.addAttribute("user", user);
        return "admin/dashboard";
    }
}