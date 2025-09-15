package uk.gitsoft.jobportal.controller;

import uk.gitsoft.jobportal.entity.*;
import uk.gitsoft.jobportal.entity.JobPostActivity;
import uk.gitsoft.jobportal.entity.JobSeekerProfile;
import uk.gitsoft.jobportal.entity.JobSeekerSave;
import uk.gitsoft.jobportal.entity.Users;
import uk.gitsoft.jobportal.services.JobPostActivityService;
import uk.gitsoft.jobportal.services.JobSeekerProfileService;
import uk.gitsoft.jobportal.services.JobSeekerSaveService;
import uk.gitsoft.jobportal.services.UsersService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class JobSeekerSaveController {

    private final JobSeekerProfileService jobSeekerProfileService;
    private final UsersService usersService;
    private final JobPostActivityService jobPostActivityService;
    private final JobSeekerSaveService jobSeekerSaveService;

    public JobSeekerSaveController(JobSeekerProfileService jobSeekerProfileService, UsersService usersService, JobPostActivityService jobPostActivityService, JobSeekerSaveService jobSeekerSaveService) {
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.usersService = usersService;
        this.jobPostActivityService = jobPostActivityService;
        this.jobSeekerSaveService = jobSeekerSaveService;
    }

    @PostMapping("/job-details/save/{id}")
    public String save(@PathVariable("id") int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            JobSeekerProfile seekerProfile = jobSeekerProfileService.getCurrentSeekerProfile();
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
            if (seekerProfile != null && jobPostActivity != null) {
                JobSeekerSave jobSeekerSave = new JobSeekerSave();
                jobSeekerSave.setJob(jobPostActivity);
                jobSeekerSave.setUserId(seekerProfile);
                jobSeekerSaveService.addNew(jobSeekerSave);
            } else {
                throw new RuntimeException("User or Job not found");
            }
        }
        return "redirect:/dashboard/";
    }


    @GetMapping({"/saved-jobs", "/saved-jobs/"})
    public String savedJobs(Model model) {
        List<JobPostActivity> jobPost = new ArrayList<>();
        Object currentUserProfile = usersService.getCurrentUserProfile();

        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getCandidatesJob((JobSeekerProfile) currentUserProfile);
        for (JobSeekerSave jobSeekerSave : jobSeekerSaveList) {
            jobPost.add(jobSeekerSave.getJob());
        }

        model.addAttribute("jobPost", jobPost);
        model.addAttribute("user", currentUserProfile);

        return "saved-jobs";
    }
}
