package prj3.controller.admin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prj3.model.NatBookings;
import prj3.service.NatBookingsService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class NatBookingsController {

    private final NatBookingsService service;

    @GetMapping
    public List<NatBookings> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public NatBookings getById(@PathVariable Long id) {
        return service.getById(id).orElseThrow();
    }

    @PostMapping
    public NatBookings create(@RequestBody NatBookings b) {
        return service.create(b);
    }

    @PutMapping("/{id}/status")
    public NatBookings updateStatus(@PathVariable Long id, @RequestParam NatBookings.Status status) {
        return service.updateStatus(id, status);
    }
}

