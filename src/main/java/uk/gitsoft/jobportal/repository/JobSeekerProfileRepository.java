package uk.gitsoft.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gitsoft.jobportal.entity.JobSeekerProfile;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile, Integer> {
}
