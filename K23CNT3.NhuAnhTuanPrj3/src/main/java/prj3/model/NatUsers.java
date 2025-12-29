package prj3.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NatUsers implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "natUserId")
    private Long natUserId;

    @Column(name = "natFullname")
    private String natFullname;

    @Column(name = "natEmail", unique = true)
    private String natEmail;

    @Column(name = "natPassword")
    private String natPassword;

    @Column(name = "natPhone")
    private String natPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "natRole")
    private Role natRole;

    public enum Role {
        USER, ADMIN
    }
}