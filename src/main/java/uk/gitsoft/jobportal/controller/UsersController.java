package uk.gitsoft.jobportal.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gitsoft.jobportal.entity.Users;
import uk.gitsoft.jobportal.entity.UsersType;
import uk.gitsoft.jobportal.services.UsersService;
import uk.gitsoft.jobportal.services.UsersTypeService;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {
    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersTypeService usersTypeService, UsersService usersService) {
        this.usersTypeService = usersTypeService;
        this.usersService = usersService;
    }
    @GetMapping("/register")
    public String register(Model model){
        List<UsersType> usersTypes = usersTypeService.getAll();
        System.out.println("Found " + usersTypes.size() + " user types");
        for (UsersType type : usersTypes) {
            System.out.println("ID: " + type.getUserTypeId() + ", Name: " + type.getUserTypeName());
        }
        model.addAttribute("getAllTypes", usersTypes);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users, Model model) {
           Optional<Users> optionalUser = usersService.getUserByEmail(users.getEmail());
           if (optionalUser.isPresent()) {
                model.addAttribute("error",
                        "Email already registered, try to login or register with other email.");
                List<UsersType> userTypes = usersTypeService.getAll();
                model.addAttribute("getAllTypes", userTypes);
                model.addAttribute("user", new Users());
                return "register";
            }
        usersService.addNew(users);
        return "dashboard";
    }



}
