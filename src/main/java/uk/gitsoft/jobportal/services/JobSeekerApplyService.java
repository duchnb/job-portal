package uk.gitsoft.jobportal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gitsoft.jobportal.entity.JobSeekerApply;
import uk.gitsoft.jobportal.entity.JobSeekerProfile;
import uk.gitsoft.jobportal.repository.JobSeekerApplyRepository;

import java.util.List;

@Service
public class JobSeekerApplyService {

    private final JobSeekerApplyRepository jobSeekerApplyRepository;

    @Autowired
    public JobSeekerApplyService(JobSeekerApplyRepository jobSeekerApplyRepository) {
        this.jobSeekerApplyRepository = jobSeekerApplyRepository;
    }

    public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId){
        return jobSeekerApplyRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerApply> geJobCandidates(JobSeekerApply job){
        return jobSeekerApplyRepository.findByJob(job);
    }

}
