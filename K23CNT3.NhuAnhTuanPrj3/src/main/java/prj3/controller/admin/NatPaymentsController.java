package prj3.controller.admin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prj3.model.NatPayments;
import prj3.service.NatPaymentsService;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class NatPaymentsController {

    private final NatPaymentsService service;

    @GetMapping
    public List<NatPayments> getAll() {
        return service.getAll();
    }

    @PostMapping
    public NatPayments create(@RequestBody NatPayments p) {
        return service.create(p);
    }
}
