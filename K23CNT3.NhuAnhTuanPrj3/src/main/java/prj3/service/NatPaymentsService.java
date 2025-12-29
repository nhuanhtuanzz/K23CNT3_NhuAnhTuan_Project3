package prj3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj3.model.NatPayments;
import prj3.repository.NatPaymentsRepository;

import java.util.List;
import java.util.Optional;

public interface NatPaymentsService {
    List<NatPayments> getAll();
    Optional<NatPayments> getById(Long id);
    NatPayments create(NatPayments p);
    void delete(Long id);
    Optional<NatPayments> findByBookingId(Long bookingId);

    @Service
    @Transactional
    class Impl implements NatPaymentsService {
        private final NatPaymentsRepository repo;
        public Impl(NatPaymentsRepository repo) { this.repo = repo; }

        @Override
        public List<NatPayments> getAll() { return repo.findAll(); }

        @Override
        public Optional<NatPayments> getById(Long id) { return repo.findById(id); }

        @Override
        public NatPayments create(NatPayments p) { return repo.save(p); }

        @Override
        public void delete(Long id) { repo.deleteById(id); }

        @Override
        public Optional<NatPayments> findByBookingId(Long bookingId) { return Optional.ofNullable(repo.findByNatBookingId(bookingId)); }
    }
}
