package prj3.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import prj3.model.NatCinemaRooms;
import prj3.model.NatSeats;
import prj3.service.NatCinemaRoomsService;
import prj3.repository.NatSeatsRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/rooms")
public class NatCinemaRoomsController {

    private final NatCinemaRoomsService roomsService;
    private final NatSeatsRepository seatsRepository;

    public NatCinemaRoomsController(NatCinemaRoomsService roomsService, NatSeatsRepository seatsRepository) {
        this.roomsService = roomsService;
        this.seatsRepository = seatsRepository;
    }

    @GetMapping
    public String listRooms(Model model) {
        model.addAttribute("rooms", roomsService.getAllRooms());
        return "admin/rooms/list";
    }

    @GetMapping("/detail/{id}")
    public String roomDetail(@PathVariable Long id, Model model) {
        try {
            NatCinemaRooms room = roomsService.getRoomById(id);

            // === SỬA LỖI Ở ĐÂY ===
            // Gọi đúng tên hàm mới vừa sửa trong Repository
            List<NatSeats> seats = seatsRepository.findByNatCinemaRoom_NatRoomId(id);

            model.addAttribute("room", room);
            model.addAttribute("seats", seats);
            return "admin/rooms/detail";
        } catch (RuntimeException e) {
            return "redirect:/admin/rooms";
        }
    }
}