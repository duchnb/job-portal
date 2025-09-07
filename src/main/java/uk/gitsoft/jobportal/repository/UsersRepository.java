package uk.gitsoft.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gitsoft.jobportal.entity.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmail(String email);
}
