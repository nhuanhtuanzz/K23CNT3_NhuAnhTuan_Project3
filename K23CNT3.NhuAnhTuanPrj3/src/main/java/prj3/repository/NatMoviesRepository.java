package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj3.model.NatMovies;

public interface NatMoviesRepository extends JpaRepository<NatMovies, Long> {

}
