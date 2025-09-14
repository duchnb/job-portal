package uk.gitsoft.jobportal.repository;

import uk.gitsoft.jobportal.entity.JobPostActivity;
import uk.gitsoft.jobportal.entity.JobSeekerApply;
import uk.gitsoft.jobportal.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply, Integer> {

    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);

    List<JobSeekerApply> findByJob(JobPostActivity job);
}
