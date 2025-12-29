package prj3.controller.admin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prj3.model.NatMoviePosterSlider;
import prj3.service.NatMoviePosterSliderService;

import java.util.List;

@RestController
@RequestMapping("/api/posters")
@RequiredArgsConstructor
public class NatMoviePosterSliderController {

    private final NatMoviePosterSliderService service;

    @GetMapping
    public List<NatMoviePosterSlider> getAll() {
        return service.getAll();
    }

    @PostMapping
    public NatMoviePosterSlider create(@RequestBody NatMoviePosterSlider p) {
        return service.create(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

