package prj3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prj3.model.NatUsers;

import java.util.Optional;

public interface NatUsersRepository extends JpaRepository<NatUsers, Long> {
    Optional<NatUsers> findByNatEmail(String natEmail);
}

