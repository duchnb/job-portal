package uk.gitsoft.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gitsoft.jobportal.entity.RecruiterProfile;

public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Integer> {

}
