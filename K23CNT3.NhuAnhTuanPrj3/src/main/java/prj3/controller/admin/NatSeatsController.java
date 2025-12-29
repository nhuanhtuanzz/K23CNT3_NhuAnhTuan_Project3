package prj3.controller.admin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prj3.model.NatSeats;
import prj3.service.NatSeatsService;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class NatSeatsController {
    private final NatSeatsService service;

    @GetMapping("/room/{roomId}")
    public List<NatSeats> getByRoom(@PathVariable Long roomId) {
        return service.getByRoomId(roomId);
    }
}

