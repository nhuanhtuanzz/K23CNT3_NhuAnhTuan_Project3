package prj3.controller.auth;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prj3.model.NatUsers;
import prj3.service.NatUsersService;

@Controller
@RequestMapping("/auth")
public class NatAuthController {

    private static final Logger logger = LoggerFactory.getLogger(NatAuthController.class);
    private final NatUsersService usersService;

    public NatAuthController(NatUsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session
    ) {
        logger.info("=== LOGIN ATTEMPT ===");
        logger.info("Email: {}", email);
        logger.info("Password: {}", password);

        try {
            // 1. Tìm user
            var userOpt = usersService.findByEmail(email);

            if (userOpt.isEmpty()) {
                logger.error("User not found for email: {}", email);
                return "redirect:/login?error=User not found";
            }

            NatUsers user = userOpt.get();
            logger.info("User found: ID={}, Name={}",
                    user.getNatUserId(), user.getNatFullname());
            logger.info("DB Password: '{}'", user.getNatPassword());
            logger.info("Input Password: '{}'", password);

            // 2. So sánh password (đơn giản)
            boolean passwordMatch = user.getNatPassword().equals(password);
            logger.info("Password match: {}", passwordMatch);

            if (!passwordMatch) {
                return "redirect:/login?error=Invalid password";
            }

            // 3. Lưu session
            session.setAttribute("USER_LOGIN", user);
            logger.info("Session saved: {}", session.getId());

            // 4. Redirect theo role
            String redirectUrl = (user.getNatRole() == NatUsers.Role.ADMIN)
                    ? "/admin/dashboard"
                    : "/home";

            logger.info("Redirecting to: {}", redirectUrl);
            return "redirect:" + redirectUrl;

        } catch (Exception e) {
            logger.error("Login error: ", e);
            return "redirect:/login?error=System error";
        }
    }
}