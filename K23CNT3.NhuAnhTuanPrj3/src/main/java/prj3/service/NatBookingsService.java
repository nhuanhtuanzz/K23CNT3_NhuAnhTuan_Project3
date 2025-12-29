package prj3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj3.model.NatBookings;
import prj3.model.NatBookingSeats;
import prj3.repository.NatBookingsRepository;
import prj3.repository.NatBookingSeatsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NatBookingsService {

    List<NatBookings> getAll();

    Optional<NatBookings> getById(Long id);

    NatBookings create(NatBookings b);

    NatBookings createWithSeats(NatBookings b, List<Long> seatIds);

    NatBookings update(Long id, NatBookings b);

    NatBookings updateStatus(Long id, NatBookings.Status status);

    void delete(Long id);

    NatBookings confirmBooking(Long id);

    NatBookings cancelBooking(Long id);

    List<NatBookings> getByUserId(Long userId);

    @Service
    @Transactional
    class Impl implements NatBookingsService {

        private final NatBookingsRepository repo;
        private final NatBookingSeatsRepository seatRepo;

        public Impl(NatBookingsRepository repo, NatBookingSeatsRepository seatRepo) {
            this.repo = repo;
            this.seatRepo = seatRepo;
        }

        @Override
        public List<NatBookings> getAll() {
            return repo.findAll();
        }

        @Override
        public Optional<NatBookings> getById(Long id) {
            return repo.findById(id);
        }

        @Override
        public NatBookings create(NatBookings b) {
            b.setNatCreatedAt(LocalDateTime.now());
            b.setNatStatus(NatBookings.Status.PENDING);
            return repo.save(b);
        }

        @Override
        public NatBookings createWithSeats(NatBookings b, List<Long> seatIds) {
            b.setNatCreatedAt(LocalDateTime.now());
            b.setNatStatus(NatBookings.Status.PENDING);
            NatBookings saved = repo.save(b);

            for (Long seatId : seatIds) {
                NatBookingSeats bs = NatBookingSeats.builder()
                        .natBookingId(saved.getNatBookingId())
                        .natSeatId(seatId)
                        .build();
                seatRepo.save(bs);
            }
            return saved;
        }

        @Override
        public NatBookings update(Long id, NatBookings b) {
            return repo.findById(id).map(ex -> {
                ex.setNatUserId(b.getNatUserId());
                ex.setNatShowtimeId(b.getNatShowtimeId());
                ex.setNatStatus(b.getNatStatus());
                return repo.save(ex);
            }).orElseThrow(() -> new RuntimeException("Booking not found"));
        }

        @Override
        public NatBookings updateStatus(Long id, NatBookings.Status status) {
            return repo.findById(id).map(ex -> {
                ex.setNatStatus(status);
                return repo.save(ex);
            }).orElseThrow(() -> new RuntimeException("Booking not found"));
        }

        @Override
        public void delete(Long id) {
            repo.deleteById(id);
        }

        @Override
        public NatBookings confirmBooking(Long id) {
            return updateStatus(id, NatBookings.Status.PAID);
        }

        @Override
        public NatBookings cancelBooking(Long id) {
            return updateStatus(id, NatBookings.Status.CANCELLED);
        }

        @Override
        public List<NatBookings> getByUserId(Long userId) {
            return repo.findByNatUserId(userId);
        }
    }
}
