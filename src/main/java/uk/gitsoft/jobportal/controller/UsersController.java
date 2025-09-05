package uk.gitsoft.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gitsoft.jobportal.entity.Users;
import uk.gitsoft.jobportal.entity.UsersType;
import uk.gitsoft.jobportal.services.UsersTypeService;

import java.util.List;

@Controller
public class UsersController {
    private final UsersTypeService usersTypeService;

    @Autowired
    public UsersController(UsersTypeService usersTypeService) {
        this.usersTypeService = usersTypeService;
    }
    @GetMapping("/register")
    public String register(Model model){
        List<UsersType> usersTypes = usersTypeService.getAll();
        model.addAttribute("getAllTypes", usersTypes);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String addNew(Users users) {
        // Add your user saving logic here
        return "redirect:/";
    }
}
