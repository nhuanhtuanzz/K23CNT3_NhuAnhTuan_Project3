package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj3.model.NatShowtimes;

import java.util.List;

public interface NatShowtimesRepository extends JpaRepository<NatShowtimes, Long> {
    List<NatShowtimes> findByNatMovieId(Long movieId);
}
