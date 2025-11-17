package k23cnt3.day04.k23cnt3.day04.service;
import k23cnt3.day04.k23cnt3.day04.dto.UserDTO;
import k23cnt3.day04.k23cnt3.day04.entity.Users;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    List<Users> userList = new ArrayList<>();

    public UsersService() {
        userList.add(new Users(1, "user1", "pass1", "John Doe",
                LocalDate.parse("1995-09-01"), "johndoe@example.com", "0987654321", 34, true));

        userList.add(new Users(2, "user2", "pass2", "Jane Smith",
                LocalDate.parse("1992-05-12"), "jane@example.com", "0978564121", 32, false));

        userList.add(new Users(3, "user3", "pass3", "Alice Johnson",
                LocalDate.parse("1985-11-22"), "alice@example.com", "1122334455", 39, true));

        userList.add(new Users(4, "user4", "pass4", "Bob Brown",
                LocalDate.parse("1988-03-18"), "bob@example.com", "6677889900", 36, true));

        userList.add(new Users(5, "user5", "pass5", "Charlie White",
                LocalDate.parse("1995-09-30"), "charlie@example.com", "4433221100", 29, false));
    }

    public List<Users> findAll() {
        return userList;
    }

    public Boolean create(Users users) {
        try {
            Users user = new Users();

            user.setId(Math.toIntExact(userList.stream().count() + 1));
            user.setUsername(users.getUsername());
            user.setPassword(users.getPassword());
            user.setFullName(users.getFullName());
            user.setEmail(users.getEmail());
            user.setPhone(users.getPhone());
            user.setBirthDay(users.getBirthDay());
            user.setAge(users.getAge());
            user.setStatus(users.getStatus());

            userList.add(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
