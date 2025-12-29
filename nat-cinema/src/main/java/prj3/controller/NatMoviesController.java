package prj3.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import prj3.model.NatMovies;
import prj3.model.NatShowtimes;
import prj3.model.NatUsers;
import prj3.service.NatMoviesService;
import prj3.service.NatShowtimesService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class NatMoviesController {

    private final NatMoviesService moviesService;
    private final NatShowtimesService showtimesService;

    // --- PUBLIC (Khách xem) ---

    // URL: /movies (Danh sách phim)
    @GetMapping
    public String listMovies(@RequestParam(required = false) String search,
                             Model model, HttpSession session) {
        addUserToModel(model, session);
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("movies", moviesService.searchMovies(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("movies", moviesService.getAllMovies());
        }
        return "movies/list";
    }

    // URL: /movies/detail/{id} (Chi tiết phim + Suất chiếu nhóm theo ngày)
    @GetMapping("/detail/{id}")
    public String movieDetail(@PathVariable Long id, Model model, HttpSession session) {
        addUserToModel(model, session);
        try {
            // 1. Lấy thông tin phim
            NatMovies movie = moviesService.getMovieById(id);

            // 2. Lấy danh sách suất chiếu
            List<NatShowtimes> showtimes = showtimesService.getShowtimesByMovieId(id);

            // 3. Logic: Lọc suất tương lai & Nhóm theo NGÀY (TreeMap để sắp xếp ngày tăng dần)
            Map<LocalDate, List<NatShowtimes>> showtimesByDate = showtimes.stream()
                    .filter(s -> s.getNatStartTime().isAfter(LocalDateTime.now())) // Chỉ lấy suất chưa chiếu
                    .collect(Collectors.groupingBy(
                            st -> st.getNatStartTime().toLocalDate(),
                            TreeMap::new,
                            Collectors.toList()
                    ));

            model.addAttribute("movie", movie);
            // Quan trọng: Biến này phải khớp với biến trong file HTML detail.html
            model.addAttribute("showtimesByDate", showtimesByDate);

            return "movies/detail";
        } catch (RuntimeException e) {
            return "redirect:/movies";
        }
    }

    // --- ADMIN ONLY ---

    // URL: /movies/list (Trang quản lý dạng bảng của Admin)
    @GetMapping("/list")
    public String manageList(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/auth/login";

        addUserToModel(model, session);
        model.addAttribute("movies", moviesService.getAllMovies());
        return "admin/movies/list";
    }

    // URL: /movies/create
    @GetMapping("/create")
    public String createForm(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/auth/login";

        addUserToModel(model, session);
        model.addAttribute("movie", new NatMovies());
        return "admin/movies/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute NatMovies movie,
                         @RequestParam("imageFile") MultipartFile imageFile,
                         HttpSession session) throws IOException {
        if (!isAdmin(session)) return "redirect:/auth/login";

        if (!imageFile.isEmpty()) {
            String fileName = saveImageToFolder(imageFile);
            movie.setNatPosterUrl(fileName);
        }
        moviesService.createMovie(movie);
        return "redirect:/movies/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/auth/login";
        addUserToModel(model, session);
        model.addAttribute("movie", moviesService.getMovieById(id));
        return "admin/movies/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute NatMovies movie,
                         @RequestParam("imageFile") MultipartFile imageFile,
                         HttpSession session) throws IOException {
        if (!isAdmin(session)) return "redirect:/auth/login";

        if (!imageFile.isEmpty()) {
            String fileName = saveImageToFolder(imageFile);
            movie.setNatPosterUrl(fileName);
        } else {
            NatMovies oldMovie = moviesService.getMovieById(id);
            movie.setNatPosterUrl(oldMovie.getNatPosterUrl());
        }
        moviesService.updateMovie(id, movie);
        return "redirect:/movies/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/auth/login";
        moviesService.deleteMovie(id);
        return "redirect:/movies/list";
    }

    // --- Helper Functions ---
    private String saveImageToFolder(MultipartFile file) throws IOException {
        String rootPath = System.getProperty("user.dir");
        Path uploadPath = Paths.get(rootPath, "src", "main", "resources", "static", "images");
        // Lưu ý: Sửa lại đường dẫn upload vào đúng thư mục static/images để hiển thị được ngay
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Files.write(uploadPath.resolve(fileName), file.getBytes());
        return fileName;
    }

    private boolean isAdmin(HttpSession session) {
        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        return user != null && NatUsers.Role.ADMIN.equals(user.getNatRole());
    }

    private void addUserToModel(Model model, HttpSession session) {
        NatUsers user = (NatUsers) session.getAttribute("loggedInUser");
        if (user != null) model.addAttribute("user", user);
    }
}