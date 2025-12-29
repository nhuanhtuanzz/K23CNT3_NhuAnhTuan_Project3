package prj3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj3.model.NatMoviePosterSlider;
import prj3.repository.NatMoviePosterSliderRepository;

import java.util.List;
import java.util.Optional;

public interface NatMoviePosterSliderService {
    List<NatMoviePosterSlider> getAll();
    Optional<NatMoviePosterSlider> getById(Long id);
    NatMoviePosterSlider create(NatMoviePosterSlider p);
    NatMoviePosterSlider update(Long id, NatMoviePosterSlider p);
    void delete(Long id);

    @Service
    @Transactional
    class Impl implements NatMoviePosterSliderService {
        private final NatMoviePosterSliderRepository repo;
        public Impl(NatMoviePosterSliderRepository repo) { this.repo = repo; }

        @Override
        public List<NatMoviePosterSlider> getAll() { return repo.findAllByOrderByNatSortOrderAsc(); }

        @Override
        public Optional<NatMoviePosterSlider> getById(Long id) { return repo.findById(id); }

        @Override
        public NatMoviePosterSlider create(NatMoviePosterSlider p) { return repo.save(p); }

        @Override
        public NatMoviePosterSlider update(Long id, NatMoviePosterSlider p) {
            return repo.findById(id).map(ex -> {
                ex.setNatMovieId(p.getNatMovieId());
                ex.setNatImageUrl(p.getNatImageUrl());
                ex.setNatSortOrder(p.getNatSortOrder());
                return repo.save(ex);
            }).orElseThrow(() -> new RuntimeException("NatMoviePosterSlider not found: " + id));
        }

        @Override
        public void delete(Long id) { repo.deleteById(id); }
    }
}
