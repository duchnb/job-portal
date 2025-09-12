package uk.gitsoft.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gitsoft.jobportal.entity.JobPostActivity;
import uk.gitsoft.jobportal.entity.Users;
import uk.gitsoft.jobportal.services.JobPostActivityService;
import uk.gitsoft.jobportal.services.UsersService;

import java.util.Date;

@Controller
public class JobPostActivityController {

    private final UsersService usersService;
    private final JobPostActivityService jobPostActivityService;

    @Autowired
    public JobPostActivityController(UsersService usersService, JobPostActivityService jobPostActivityService) {
        this.usersService = usersService;
        this.jobPostActivityService = jobPostActivityService;
    }
    @GetMapping("/dashboard")
    public String searchJob(Model model){
        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            model.addAttribute("username", currentUsername);
        }
        model.addAttribute("user", currentUserProfile);
        model.addAttribute("jobPost", java.util.Collections.emptyList()); // Add an empty list to prevent template error
        return "dashboard";
    }
    
    @GetMapping("/dashboard/add")
    public String addJobs(Model model){
        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-jobs";
    }
    @PostMapping("/dashboard/addNew")
    public String addNew(JobPostActivity jobPostActivity, Model model){

        Users user = usersService.getCurrentUser();
        if(user != null){
            jobPostActivity.setPostedById(user);
        }
        jobPostActivity.setPostedDate(new Date());
        model.addAttribute("jobPostActivity", jobPostActivity);
        JobPostActivity saved = jobPostActivityService.addNew(jobPostActivity);
        return "redirect:/dashboard";
    }
}
