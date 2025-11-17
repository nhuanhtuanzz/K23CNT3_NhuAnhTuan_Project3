package k23cnt3.day04.k23cnt3.day04.controller;

import k23cnt3.day04.k23cnt3.day04.dto.UserDTO;
import k23cnt3.day04.k23cnt3.day04.entity.Users;
import k23cnt3.day04.k23cnt3.day04.service.UsersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class UsersController {

    @Autowired
    UsersService usersService;

    // Endpoint để lấy danh sách tất cả người dùng
    @GetMapping("/user-list")
    public List<Users> getAllUsers() {
        return usersService.findAll();
    }

    // Endpoint để thêm người dùng mới
    @PostMapping("/user-add")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserDTO user) {
        usersService.create(Users);
        return ResponseEntity.ok().body("Users created successfully"); // Đã sửa từ .badRequest() sang .ok() vì tạo thành công
    }

}