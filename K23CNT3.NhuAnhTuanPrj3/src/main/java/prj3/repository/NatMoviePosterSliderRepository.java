package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj3.model.NatMoviePosterSlider;

import java.util.List;

public interface NatMoviePosterSliderRepository extends JpaRepository<NatMoviePosterSlider, Long> {

    List<NatMoviePosterSlider> findAllByOrderByNatSortOrderAsc();
}
