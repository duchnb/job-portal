package uk.gitsoft.jobportal.services;

import org.springframework.stereotype.Service;
import uk.gitsoft.jobportal.entity.JobPostActivity;
import uk.gitsoft.jobportal.repository.JobPostActivityRepository;

@Service
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;

    public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository) {
        this.jobPostActivityRepository = jobPostActivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity){
        return jobPostActivityRepository.save(jobPostActivity);
    }
}
