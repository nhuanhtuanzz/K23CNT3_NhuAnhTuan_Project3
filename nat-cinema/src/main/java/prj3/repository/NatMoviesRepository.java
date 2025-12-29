package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prj3.model.NatMovies;
import java.time.LocalDate;
import java.util.List;

public interface NatMoviesRepository extends JpaRepository<NatMovies, Long> {

    List<NatMovies> findByNatTitleContainingIgnoreCase(String keyword);

    List<NatMovies> findByNatReleaseDateAfter(LocalDate date);

    List<NatMovies> findByNatReleaseDateBefore(LocalDate date);

    List<NatMovies> findAllByOrderByNatReleaseDateDesc();
}