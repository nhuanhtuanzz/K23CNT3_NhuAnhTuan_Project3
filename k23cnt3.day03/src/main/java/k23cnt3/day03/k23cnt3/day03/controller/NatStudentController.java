package k23cnt3.day03.k23cnt3.day03.controller;
import k23cnt3.day03.k23cnt3.day03.entity.NatStudent;
import k23cnt3.day03.k23cnt3.day03.service.NatServiceStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class NatStudentController {

    @Autowired
    private NatServiceStudent natStudentService;

    @GetMapping("/nat-student-list")
    public List<NatStudent> getAllNatStudents() {
        return natStudentService.getNatStudents();
    }

    @GetMapping("/nat-student/{natId}")
    public NatStudent getNatStudent(@PathVariable String natId) {
        Long param = Long.parseLong(natId);
        return natStudentService.getNatStudent(param);
    }

    @PostMapping("/nat-student-add")
    public NatStudent addNatStudent(@RequestBody NatStudent natStudent) {
        return natStudentService.addNatStudent(natStudent);
    }

    @PutMapping("/nat-student/{natId}")
    public NatStudent updateNatStudent(@PathVariable String natId,
                                       @RequestBody NatStudent natStudent) {
        Long param = Long.parseLong(natId);
        return natStudentService.updateNatStudent(param, natStudent);
    }

    @DeleteMapping("/nat-student/{natId}")
    public boolean deleteNatStudent(@PathVariable String natId) {
        Long param = Long.parseLong(natId);
        return natStudentService.deleteNatStudent(param);
    }
}
