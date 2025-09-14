package uk.gitsoft.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gitsoft.jobportal.entity.JobSeekerApply;
import uk.gitsoft.jobportal.entity.JobSeekerProfile;

import java.util.List;

public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply, Integer> {
    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);

    List<JobSeekerApply> findByJob(JobSeekerApply job);
}
