package uk.gitsoft.jobportal.repository;

import uk.gitsoft.jobportal.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersTypeRepository extends JpaRepository<UserType,Integer> {
}
