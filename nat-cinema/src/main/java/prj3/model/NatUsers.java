package prj3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Users")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class NatUsers {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natUserId;

    private String natFullname;

    @Column(unique = true)
    private String natEmail;

    private String natPassword;
    private String natPhone;

    @Enumerated(EnumType.STRING)
    private Role natRole = Role.USER;

    public enum Role { USER, ADMIN }
}