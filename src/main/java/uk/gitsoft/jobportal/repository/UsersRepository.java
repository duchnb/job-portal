package uk.gitsoft.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gitsoft.jobportal.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

}
