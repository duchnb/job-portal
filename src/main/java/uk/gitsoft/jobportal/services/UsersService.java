package uk.gitsoft.jobportal.services;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.gitsoft.jobportal.entity.JobSeekerProfile;
import uk.gitsoft.jobportal.entity.RecruiterProfile;
import uk.gitsoft.jobportal.entity.Users;
import uk.gitsoft.jobportal.repository.JobSeekerProfileRepository;
import uk.gitsoft.jobportal.repository.RecruiterProfileRepository;
import uk.gitsoft.jobportal.repository.UsersRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;


    public UsersService(UsersRepository usersRepository, JobSeekerProfileRepository jobSeekerProfileRepository, RecruiterProfileRepository recruiterProfileRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Users addNew(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUsers = usersRepository.save(users);
        int userTypeId = users.getUserType().getUserTypeId();
        if(userTypeId == 1){
           recruiterProfileRepository.save(new RecruiterProfile(users));
        }else{
            jobSeekerProfileRepository.save(new JobSeekerProfile(users));
        }
        return savedUsers;
    }
    public Optional<Users> getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }

    public Object getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
          Users users = usersRepository.findByEmail(username).orElseThrow(()->new RuntimeException("User name not found"));
          int userId = users.getUserId();
          if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
             RecruiterProfile recruiteProfile = recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
              return recruiteProfile;
          }else {
              JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile());
              return jobSeekerProfile;
          }
        }
        return null;
    }

    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            return usersRepository.findByEmail(username).orElseThrow(()->new RuntimeException("User name not found"));
        }
        return null;
    }
}
