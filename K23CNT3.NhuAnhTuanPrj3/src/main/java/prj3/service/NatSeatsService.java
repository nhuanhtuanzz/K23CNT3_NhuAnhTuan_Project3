package prj3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj3.model.NatSeats;
import prj3.repository.NatSeatsRepository;

import java.util.List;
import java.util.Optional;

public interface NatSeatsService {
    List<NatSeats> getAll();
    Optional<NatSeats> getById(Long id);
    List<NatSeats> getByRoomId(Long roomId);
    NatSeats create(NatSeats s);
    NatSeats update(Long id, NatSeats s);
    void delete(Long id);

    @Service
    @Transactional
    class Impl implements NatSeatsService {
        private final NatSeatsRepository repo;
        public Impl(NatSeatsRepository repo) { this.repo = repo; }

        @Override
        public List<NatSeats> getAll() { return repo.findAll(); }

        @Override
        public Optional<NatSeats> getById(Long id) { return repo.findById(id); }

        @Override
        public List<NatSeats> getByRoomId(Long roomId) { return repo.findByNatRoomId(roomId); }

        @Override
        public NatSeats create(NatSeats s) { return repo.save(s); }

        @Override
        public NatSeats update(Long id, NatSeats s) {
            return repo.findById(id).map(ex -> {
                ex.setNatRoomId(s.getNatRoomId());
                ex.setNatSeatCode(s.getNatSeatCode());
                return repo.save(ex);
            }).orElseThrow(() -> new RuntimeException("NatSeats not found: " + id));
        }

        @Override
        public void delete(Long id) { repo.deleteById(id); }
    }
}
