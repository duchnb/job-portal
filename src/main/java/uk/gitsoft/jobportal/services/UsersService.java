package uk.gitsoft.jobportal.services;

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

            
    public UsersService(UsersRepository usersRepository, JobSeekerProfileRepository jobSeekerProfileRepository, RecruiterProfileRepository recruiterProfileRepository) {
        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public Users addNew(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
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
}
