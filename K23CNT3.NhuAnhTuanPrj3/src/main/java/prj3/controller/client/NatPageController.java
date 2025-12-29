package prj3.controller.client;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prj3.model.NatUsers;

@Controller
public class NatPageController {

    @GetMapping({"/", "/home"})
    public String home(HttpSession session, Model model) {
        NatUsers user = (NatUsers) session.getAttribute("USER_LOGIN");
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "client/home";
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            Model model
    ) {
        // Xử lý tham số error
        if (error != null) {
            if (error.equals("true")) {
                model.addAttribute("errorMessage", "Email hoặc mật khẩu không đúng!");
            } else {
                model.addAttribute("errorMessage", error);
            }
        }
        return "login";
    }
}