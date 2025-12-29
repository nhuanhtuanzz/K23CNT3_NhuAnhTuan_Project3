package prj3.service;

import org.springframework.stereotype.Service;
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

    // 1. Tìm user bằng email (cho login)
    public Optional<NatUsers> findByEmail(String email) {
        return usersRepository.findByNatEmail(email);
    }

    // 2. Lấy tất cả users
    public List<NatUsers> getAll() {
        return usersRepository.findAll();
    }

    // 3. Lấy user theo ID
    public Optional<NatUsers> getById(Long id) {
        return usersRepository.findById(id);
    }

    // 4. Tạo user mới
    public NatUsers create(NatUsers user) {
        return usersRepository.save(user);
    }

    // 5. Cập nhật user
    public NatUsers update(Long id, NatUsers userDetails) {
        NatUsers existingUser = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Cập nhật các trường
        existingUser.setNatFullname(userDetails.getNatFullname());
        existingUser.setNatEmail(userDetails.getNatEmail());
        existingUser.setNatPhone(userDetails.getNatPhone());
        existingUser.setNatRole(userDetails.getNatRole());

        // Chỉ cập nhật password nếu có giá trị mới
        if (userDetails.getNatPassword() != null && !userDetails.getNatPassword().isEmpty()) {
            existingUser.setNatPassword(userDetails.getNatPassword());
        }

        return usersRepository.save(existingUser);
    }

    // 6. Xóa user
    public void delete(Long id) {
        if (!usersRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        usersRepository.deleteById(id);
    }
}