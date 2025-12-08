package K23CNT3.NhuAnhTuanPrj3.K23CNT3.NhuAnhTuanPrj3.model;
import jakarta.persistence.*;
import lombok.*;
public class natUsers
{
    @Entity
    @Table(name = "Users")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class Users {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long natUserId;

        private String natFullname;

        @Column(unique = true)
        private String natEmail;

        private String natPassword;

        private String natPhone;

        @Enumerated(EnumType.STRING)
        private Role natRole = Role.USER;

        public enum Role {
            USER, ADMIN
        }
    }
}
