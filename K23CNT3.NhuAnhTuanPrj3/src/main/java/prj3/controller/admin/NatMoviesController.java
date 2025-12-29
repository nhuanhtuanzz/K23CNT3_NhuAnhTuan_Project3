package prj3.controller.admin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prj3.model.NatMovies;
import prj3.service.NatMoviesService;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class NatMoviesController {

    private final NatMoviesService service;

    @GetMapping
    public List<NatMovies> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public NatMovies getById(@PathVariable Long id) {
        return service.getById(id).orElseThrow();
    }

    @PostMapping
    public NatMovies create(@RequestBody NatMovies m) {
        return service.create(m);
    }

    @PutMapping("/{id}")
    public NatMovies update(@PathVariable Long id, @RequestBody NatMovies m) {
        return service.update(id, m);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

