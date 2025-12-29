package prj3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj3.model.NatShowtimes;
import prj3.repository.NatShowtimesRepository;

import java.util.List;
import java.util.Optional;

public interface NatShowtimesService {
    List<NatShowtimes> getAll();
    Optional<NatShowtimes> getById(Long id);
    List<NatShowtimes> getByMovieId(Long movieId);
    NatShowtimes create(NatShowtimes s);
    NatShowtimes update(Long id, NatShowtimes s);
    void delete(Long id);

    @Service
    @Transactional
    class Impl implements NatShowtimesService {
        private final NatShowtimesRepository repo;
        public Impl(NatShowtimesRepository repo) { this.repo = repo; }

        @Override
        public List<NatShowtimes> getAll() { return repo.findAll(); }

        @Override
        public Optional<NatShowtimes> getById(Long id) { return repo.findById(id); }

        @Override
        public List<NatShowtimes> getByMovieId(Long movieId) { return repo.findByNatMovieId(movieId); }

        @Override
        public NatShowtimes create(NatShowtimes s) { return repo.save(s); }

        @Override
        public NatShowtimes update(Long id, NatShowtimes s) {
            return repo.findById(id).map(ex -> {
                ex.setNatMovieId(s.getNatMovieId());
                ex.setNatRoomId(s.getNatRoomId());
                ex.setNatStartTime(s.getNatStartTime());
                return repo.save(ex);
            }).orElseThrow(() -> new RuntimeException("NatShowtimes not found: " + id));
        }

        @Override
        public void delete(Long id) { repo.deleteById(id); }
    }
}
