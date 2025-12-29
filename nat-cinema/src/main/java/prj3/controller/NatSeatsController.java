package prj3.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import prj3.repository.NatSeatsRepository;

@Controller
@RequestMapping("/admin/seats")
public class NatSeatsController {
    private final NatSeatsRepository seatsRepository;

    public NatSeatsController(NatSeatsRepository seatsRepository) {
        this.seatsRepository = seatsRepository;
    }

    @GetMapping("/room/{roomId}")
    public String listSeatsByRoom(@PathVariable Long roomId, Model model) {
        model.addAttribute("seats", seatsRepository.findByNatCinemaRoom_NatRoomId(roomId));
        model.addAttribute("roomId", roomId);
        return "admin/seats/list";
    }
}