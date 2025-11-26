package uk.gitsoft.jobportal.controller;


import uk.gitsoft.jobportal.entity.UserType;
import uk.gitsoft.jobportal.entity.Users;
import uk.gitsoft.jobportal.services.UsersService;
import uk.gitsoft.jobportal.services.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersTypeService usersTypeService, UsersService usersService) {
        this.usersService = usersService;
        this.usersTypeService = usersTypeService;
    }

    @GetMapping("/register")
    public String register(Model model, @RequestParam(value = "type", required = false) String type){
        List<UserType> usersTypes = usersTypeService.getAll();

        Users user = new Users();
        if (type != null) {
            String t = type.trim().toLowerCase();
            UserType preselect = null;
            for (UserType ut : usersTypes) {
                String name = ut.getUserTypeName() != null ? ut.getUserTypeName().toLowerCase() : "";
                if (("seeker".equals(t) || "jobseeker".equals(t) || "job-seeker".equals(t) || "candidate".equals(t)) && name.contains("job") && name.contains("seeker")) {
                    preselect = ut; break;
                }
                if (("recruiter".equals(t) || "employer".equals(t)) && name.contains("recruiter")) {
                    preselect = ut; break;
                }
            }
            if (preselect == null) {
                // fallback: try exact contains words
                for (UserType ut : usersTypes) {
                    String name = ut.getUserTypeName() != null ? ut.getUserTypeName().toLowerCase() : "";
                    if (t.contains("recruit") && name.contains("recruit")) { preselect = ut; break; }
                    if (t.contains("seek") && name.contains("seek")) { preselect = ut; break; }
                }
            }
            if (preselect != null) {
                user.setUserTypeId(preselect);
            }
        }

        model.addAttribute("getAllTypes", usersTypes);
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users user, Model model){
//        System.out.println("User Registration: " + user);

        Optional<Users> optionalUsers = usersService.getUsersByEmail(user.getEmail());

        if(optionalUsers.isPresent()){
            model.addAttribute("error","Email already registered, try to login or register with other email.");
            List<UserType> usersTypes = usersTypeService.getAll();
            model.addAttribute("getAllTypes",usersTypes);
            model.addAttribute("user",new Users());
            return "register";
        }
        usersService.addNew(user);
        // Redirect based on selected user type: Job Seekers -> login, Recruiters -> dashboard
        try {
            int typeId = user.getUserTypeId() != null ? user.getUserTypeId().getUserTypeId() : -1;
            if (typeId == 1) {
                return "redirect:/dashboard";
            } else {
                return "redirect:/login";
            }
        } catch (Exception e) {
            // Safe fallback to login after registration
            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }
}
