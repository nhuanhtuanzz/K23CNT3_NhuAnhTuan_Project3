package prj3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prj3.model.NatSeats; // SỬA: Import từ model, có chữ 's'
import prj3.repository.NatSeatsRepository;

@RestController
@RequestMapping("/admin/seats")
public class NatAdminRoomController {

    @Autowired
    private NatSeatsRepository natSeatsRepository;

    @PostMapping("/toggle-maintenance/{seatId}")
    public ResponseEntity<String> toggleSeatMaintenance(@PathVariable Long seatId) {
        return natSeatsRepository.findById(seatId).map(seat -> {
            // Logic đảo trạng thái
            if (seat.getNatStatus() == NatSeats.SeatStatus.MAINTENANCE) {
                seat.setNatStatus(NatSeats.SeatStatus.AVAILABLE);
            } else {
                seat.setNatStatus(NatSeats.SeatStatus.MAINTENANCE);
            }
            natSeatsRepository.save(seat);
            return ResponseEntity.ok("Updated");
        }).orElse(ResponseEntity.notFound().build());
    }
}