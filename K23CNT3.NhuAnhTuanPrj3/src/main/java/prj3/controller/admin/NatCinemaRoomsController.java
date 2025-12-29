package prj3.controller.admin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prj3.model.NatCinemaRooms;
import prj3.service.NatCinemaRoomsService;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class NatCinemaRoomsController {
    private final NatCinemaRoomsService service;

    @GetMapping
    public List<NatCinemaRooms> getAll() {
        return service.getAll();
    }
}

