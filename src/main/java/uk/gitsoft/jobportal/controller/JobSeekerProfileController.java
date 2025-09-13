package uk.gitsoft.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gitsoft.jobportal.entity.JobSeekerProfile;
import uk.gitsoft.jobportal.entity.Skills;
import uk.gitsoft.jobportal.entity.Users;
import uk.gitsoft.jobportal.repository.UsersRepository;
import uk.gitsoft.jobportal.services.JobSeekerProfileService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("job-seeker-profile")
public class JobSeekerProfileController {
    private JobSeekerProfileService jobSeekerProfileService;
    private UsersRepository usersRepository;

    @Autowired
    public JobSeekerProfileController(JobSeekerProfileService jobSeekerProfileService, UsersRepository usersRepository) {
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public String JobsiseekerProfile(Model model){
        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Skills> skills = new ArrayList<>();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(()->new UsernameNotFoundException("User not found"));
            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
            if(seekerProfile.isPresent()){
                jobSeekerProfile = seekerProfile.get();
                if(jobSeekerProfile.getSkills().isEmpty()){
                    skills.add(new Skills());
                    jobSeekerProfile.setSkills(skills);
                }
            }
            model.addAttribute("skills", skills);
            model.addAttribute("profile", jobSeekerProfile);
        }

        return "job-seeker-profile";
    }
}
