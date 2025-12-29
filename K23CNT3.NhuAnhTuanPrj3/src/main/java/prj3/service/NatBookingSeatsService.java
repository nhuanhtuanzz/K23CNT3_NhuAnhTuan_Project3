package prj3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj3.model.NatBookingSeats;
import prj3.repository.NatBookingSeatsRepository;

import java.util.List;
import java.util.Optional;

public interface NatBookingSeatsService {
    List<NatBookingSeats> getAll();
    Optional<NatBookingSeats> getById(Long id);
    List<NatBookingSeats> getByBookingId(Long bookingId);
    NatBookingSeats create(NatBookingSeats bs);
    void delete(Long id);

    @Service
    @Transactional
    class Impl implements NatBookingSeatsService {
        private final NatBookingSeatsRepository repo;
        public Impl(NatBookingSeatsRepository repo) { this.repo = repo; }

        @Override
        public List<NatBookingSeats> getAll() { return repo.findAll(); }

        @Override
        public Optional<NatBookingSeats> getById(Long id) { return repo.findById(id); }

        @Override
        public List<NatBookingSeats> getByBookingId(Long bookingId) { return repo.findByNatBookingId(bookingId); }

        @Override
        public NatBookingSeats create(NatBookingSeats bs) { return repo.save(bs); }

        @Override
        public void delete(Long id) { repo.deleteById(id); }
    }
}
