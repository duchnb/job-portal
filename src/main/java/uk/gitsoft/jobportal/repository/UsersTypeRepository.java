package uk.gitsoft.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uk.gitsoft.jobportal.entity.UsersType;

import java.util.List;

public interface UsersTypeRepository extends JpaRepository<UsersType, Integer> {
    @Query(value = "SELECT * FROM users_type", nativeQuery = true)
    List<UsersType> findAllUserTypes();
}
