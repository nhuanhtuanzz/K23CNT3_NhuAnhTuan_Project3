package prj3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj3.model.NatCinemaRooms;
import prj3.repository.NatCinemaRoomsRepository;

import java.util.List;
import java.util.Optional;

public interface NatCinemaRoomsService {
    List<NatCinemaRooms> getAll();
    Optional<NatCinemaRooms> getById(Long id);
    NatCinemaRooms create(NatCinemaRooms r);
    NatCinemaRooms update(Long id, NatCinemaRooms r);
    void delete(Long id);

    @Service
    @Transactional
    class Impl implements NatCinemaRoomsService {
        private final NatCinemaRoomsRepository repo;
        public Impl(NatCinemaRoomsRepository repo) { this.repo = repo; }

        @Override
        public List<NatCinemaRooms> getAll() { return repo.findAll(); }

        @Override
        public Optional<NatCinemaRooms> getById(Long id) { return repo.findById(id); }

        @Override
        public NatCinemaRooms create(NatCinemaRooms r) { return repo.save(r); }

        @Override
        public NatCinemaRooms update(Long id, NatCinemaRooms r) {
            return repo.findById(id).map(ex -> {
                ex.setNatRoomName(r.getNatRoomName());
                ex.setNatTotalSeats(r.getNatTotalSeats());
                return repo.save(ex);
            }).orElseThrow(() -> new RuntimeException("NatCinemaRooms not found: " + id));
        }

        @Override
        public void delete(Long id) { repo.deleteById(id); }
    }
}
