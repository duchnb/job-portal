package uk.gitsoft.jobportal.controller;


import uk.gitsoft.jobportal.entity.RecruiterProfile;
import uk.gitsoft.jobportal.entity.Users;
import uk.gitsoft.jobportal.repository.UsersRepository;
import uk.gitsoft.jobportal.services.RecruiterProfileService;
import uk.gitsoft.jobportal.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UsersRepository usesRepository;
    private final RecruiterProfileService recruiterProfileService;

    @Autowired
    public RecruiterProfileController(UsersRepository usesRepository, RecruiterProfileService recruiterProfileService) {
        this.usesRepository = usesRepository;
        this.recruiterProfileService = recruiterProfileService;
    }


    @GetMapping("/")
    public String recruiterProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users =  usesRepository.findByEmail(currentUsername).orElseThrow(
                    () -> new UsernameNotFoundException("Could not find user " + currentUsername)
            );
            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(users.getUserId());

            recruiterProfile.ifPresent(profile -> model.addAttribute("profile", recruiterProfile.get()));
        }
        return "recruiter_profile";
    }

    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image")MultipartFile multipartFile, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users =  usesRepository.findByEmail(currentUsername).orElseThrow(
                    () -> new UsernameNotFoundException("Could not find user " + currentUsername)
            );
            recruiterProfile.setUserId(users);
            recruiterProfile.setUserAccountId(users.getUserId());
        }
        model.addAttribute("profile", recruiterProfile);
        String fileName = "";
        if(!Objects.equals(multipartFile.getOriginalFilename(), "")){
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(fileName);
        }
        RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);
        String uploadDir = "photos/recruiter/"+savedUser.getUserAccountId();
        try{
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/dashboard/";
    }
}
