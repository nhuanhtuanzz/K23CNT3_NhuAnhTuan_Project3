package k23cnt3.day04.k23cnt3.day04.entity;

import java.time.LocalDate;

public class Users {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private LocalDate birthDay;
    private String email;
    private String phone;
    private int age;
    private Boolean status;

    public Users() {}

    public Users(int id, String username, String password, String fullName,
                 LocalDate birthDay, String email, String phone,
                 int age, Boolean status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.status = status;
    }

    // Getter + Setter đầy đủ
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public LocalDate getBirthDay() { return birthDay; }
    public void setBirthDay(LocalDate birthDay) { this.birthDay = birthDay; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
}
