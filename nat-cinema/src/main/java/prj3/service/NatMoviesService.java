package prj3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj3.model.NatMovies;
import prj3.repository.NatMoviesRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class NatMoviesService {

    private final NatMoviesRepository moviesRepository;

    public NatMoviesService(NatMoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    // Lấy tất cả phim
    public List<NatMovies> getAllMovies() {
        return moviesRepository.findAllByOrderByNatReleaseDateDesc();
    }

    // Lấy phim theo ID
    public NatMovies getMovieById(Long id) {
        return moviesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    // Thêm phim mới
    public NatMovies createMovie(NatMovies movie) {
        // Validate
        if (movie.getNatTitle() == null || movie.getNatTitle().trim().isEmpty()) {
            throw new RuntimeException("Movie title is required");
        }
        if (movie.getNatDuration() == null || movie.getNatDuration() <= 0) {
            throw new RuntimeException("Movie duration must be greater than 0");
        }

        return moviesRepository.save(movie);
    }

    // Cập nhật phim
    public NatMovies updateMovie(Long id, NatMovies movieDetails) {
        NatMovies movie = getMovieById(id);

        movie.setNatTitle(movieDetails.getNatTitle());
        movie.setNatDescription(movieDetails.getNatDescription());
        movie.setNatDuration(movieDetails.getNatDuration());
        movie.setNatReleaseDate(movieDetails.getNatReleaseDate());
        movie.setNatPosterUrl(movieDetails.getNatPosterUrl());

        return moviesRepository.save(movie);
    }

    // Xóa phim
    public void deleteMovie(Long id) {
        NatMovies movie = getMovieById(id);
        moviesRepository.delete(movie);
    }

    // Tìm phim theo từ khóa
    public List<NatMovies> searchMovies(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllMovies();
        }
        return moviesRepository.findByNatTitleContainingIgnoreCase(keyword.trim());
    }

    // Lấy phim sắp chiếu
    public List<NatMovies> getComingSoonMovies() {
        return moviesRepository.findByNatReleaseDateAfter(LocalDate.now());
    }

    // Lấy phim đang chiếu
    public List<NatMovies> getNowShowingMovies() {
        return moviesRepository.findByNatReleaseDateBefore(LocalDate.now());
    }

    // Đếm tổng số phim
    public long getTotalMovies() {
        return moviesRepository.count();
    }
}