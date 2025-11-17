package k23cnt3.day03.k23cnt3.day03.service;
import k23cnt3.day03.k23cnt3.day03.entity.NatStudent;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NatServiceStudent {
    List<NatStudent> natStudents = new ArrayList<NatStudent>();

    public NatServiceStudent() {
        natStudents.addAll(Arrays.asList(
                new NatStudent(1L, "NatCNT3 1", 20, "Non", "Số 25 VNP", "0912345678", "abc@gmail.com"),
                new NatStudent(2L, "NatCNT3 2", 25, "Non", "Số 25 VNP", "0912345678", "bdd@gmail.com"),
                new NatStudent(3L, "NatCNT3 3", 22, "Non", "Số 25 VNP", "0912345678", "xyz@gmail.com")
        ));
    }

    // Lấy toàn bộ danh sách sinh viên
    public List<NatStudent> getNatStudents() {
        return natStudents;
    }

    // Lấy sinh viên theo id
    public NatStudent getNatStudent(Long natId) {
        return natStudents.stream()
                .filter(natStudent -> natStudent.getNatId().equals(natId))
                .findFirst().orElse(null);
    }

    // Thêm mới một sinh viên
    public NatStudent addNatStudent(NatStudent natStudent) {
        natStudents.add(natStudent);
        return natStudent;
    }

    // Cập nhật thông tin sinh viên
    public NatStudent updateNatStudent(Long natId, NatStudent natStudent) {
        NatStudent check = getNatStudent(natId);
        if (check == null) {
            return null;
        }
        natStudents.forEach(item -> {
            if (item.getNatId().equals(natId)) {
                item.setNatName(natStudent.getNatName());
                item.setNatAddress(natStudent.getNatAddress());
                item.setNatEmail(natStudent.getNatEmail());
                item.setNatPhone(natStudent.getNatPhone());
                item.setNatAge(natStudent.getNatAge());
                item.setNatGender(natStudent.getNatGender());
            }
        });
        return natStudent;
    }

    // Xóa thông tin sinh viên
    public boolean deleteNatStudent(Long natId) {
        NatStudent check = getNatStudent(natId);
        return natStudents.remove(check);
    }
}

