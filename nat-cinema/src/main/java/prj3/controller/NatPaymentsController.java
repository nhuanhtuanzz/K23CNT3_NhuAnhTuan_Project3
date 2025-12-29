package prj3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import prj3.repository.NatPaymentsRepository;

@Controller
@RequestMapping("/admin/payments")
public class NatPaymentsController {
    private final NatPaymentsRepository paymentsRepository;

    public NatPaymentsController(NatPaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    @GetMapping
    public String listPayments(Model model) {
        model.addAttribute("payments", paymentsRepository.findAll());
        return "admin/payments/list";
    }
}
