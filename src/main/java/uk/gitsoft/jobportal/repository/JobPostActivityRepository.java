package uk.gitsoft.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gitsoft.jobportal.entity.JobPostActivity;


public interface JobPostActivityRepository extends JpaRepository<JobPostActivity, Integer> {
}
