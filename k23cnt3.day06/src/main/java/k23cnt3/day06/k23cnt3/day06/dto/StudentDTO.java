package k23cnt3.day06.k23cnt3.day06.dto;

import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
