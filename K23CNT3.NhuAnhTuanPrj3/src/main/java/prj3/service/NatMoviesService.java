package prj3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj3.model.NatMovies;
import prj3.repository.NatMoviesRepository;

import java.util.List;
import java.util.Optional;

public interface NatMoviesService {
    List<NatMovies> getAll();
    Optional<NatMovies> getById(Long id);
    NatMovies create(NatMovies m);
    NatMovies update(Long id, NatMovies m);
    void delete(Long id);

    @Service
    @Transactional
    class Impl implements NatMoviesService {
        private final NatMoviesRepository repo;
        public Impl(NatMoviesRepository repo) { this.repo = repo; }

        @Override
        public List<NatMovies> getAll() { return repo.findAll(); }

        @Override
        public Optional<NatMovies> getById(Long id) { return repo.findById(id); }

        @Override
        public NatMovies create(NatMovies m) { return repo.save(m); }

        @Override
        public NatMovies update(Long id, NatMovies m) {
            return repo.findById(id).map(ex -> {
                ex.setNatTitle(m.getNatTitle());
                ex.setNatDescription(m.getNatDescription());
                ex.setNatDuration(m.getNatDuration());
                ex.setNatReleaseDate(m.getNatReleaseDate());
                ex.setNatPosterUrl(m.getNatPosterUrl());
                return repo.save(ex);
            }).orElseThrow(() -> new RuntimeException("NatMovies not found: " + id));
        }

        @Override
        public void delete(Long id) { repo.deleteById(id); }
    }
}
