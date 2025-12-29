package prj3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prj3.model.NatUsers;
import prj3.repository.NatUsersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NatUsersService {

    private final NatUsersRepository usersRepository;

    public NatUsersService(NatUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<NatUsers> findAll() {
        return usersRepository.findAll();
    }

    // Hàm tìm kiếm trả về Optional để xử lý null an toàn
    public Optional<NatUsers> findByEmail(String email) {
        return usersRepository.findByNatEmail(email);
    }

    // Logic đăng nhập cơ bản
    public boolean authenticate(String email, String password) {
        return findByEmail(email)
                .map(user -> user.getNatPassword().equals(password))
                .orElse(false);
    }

    @Transactional
    public void updateRole(Long userId, NatUsers.Role role) {
        usersRepository.findById(userId).ifPresent(user -> {
            user.setNatRole(role);
            usersRepository.save(user);
        });
    }

    @Transactional
    public void delete(Long userId) {
        usersRepository.deleteById(userId);
    }

    // =========================================================
    // THÊM HÀM NÀY ĐỂ CHỨC NĂNG ĐĂNG KÝ HOẠT ĐỘNG
    // =========================================================
    @Transactional
    public void registerUser(NatUsers user) {
        // 1. Kiểm tra xem email đã có trong DB chưa
        // Lưu ý: findByNatEmail trả về Optional, nên ta dùng .isPresent()
        if (usersRepository.findByNatEmail(user.getNatEmail()).isPresent()) {
            throw new RuntimeException("Email này đã được sử dụng! Vui lòng chọn email khác.");
        }

        // 2. Thiết lập quyền mặc định là USER (Khách hàng)
        user.setNatRole(NatUsers.Role.USER);

        // 3. Lưu người dùng mới vào Database
        usersRepository.save(user);
    }
}