package prj3.controller.admin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prj3.model.NatShowtimes;
import prj3.service.NatShowtimesService;

import java.util.List;


@RestController
@RequestMapping("/api/showtimes")
@RequiredArgsConstructor
public class NatShowtimesController {

    private final NatShowtimesService service;

    @GetMapping
    public List<NatShowtimes> getAll() {
        return service.getAll();
    }

    @PostMapping
    public NatShowtimes create(@RequestBody NatShowtimes s) {
        return service.create(s);
    }

    @GetMapping("/{id}")
    public NatShowtimes getById(@PathVariable Long id) {
        return service.getById(id).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
