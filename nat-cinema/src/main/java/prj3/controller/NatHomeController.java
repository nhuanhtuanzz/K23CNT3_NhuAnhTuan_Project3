package prj3.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prj3.model.NatMoviePosterSlider;
import prj3.model.NatUsers;
import prj3.repository.NatMoviePosterSliderRepository;
import prj3.repository.NatMoviesRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor // Tự động inject các field 'final'
public class NatHomeController {

    private final NatMoviesRepository moviesRepository;

    // 1. THÊM DÒNG NÀY: Khai báo Repository cho Slider
    private final NatMoviePosterSliderRepository sliderRepository;

    @GetMapping({"/", "/home"})
    public String index(Model model,
                        HttpSession session,
                        @RequestParam(name = "keyword", required = false) String keyword) {

        // Xử lý thông tin người dùng
        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        if (user != null) {
            model.addAttribute("user", user);
        }
        List<NatMoviePosterSlider> slides = sliderRepository.findAllByOrderByNatSortOrderAsc(); // 2. SỬA: Gọi qua biến 'sliderRepository'
        model.addAttribute("slides", slides);

        // Logic tìm kiếm / hiển thị phim
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("movies", moviesRepository.findByNatTitleContainingIgnoreCase(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("movies", moviesRepository.findAll());
        }

        return "index";
    }

    @GetMapping("/contact")
    public String contact() {
        return "home/contact";
    }
}