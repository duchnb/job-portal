package uk.gitsoft.jobportal.services;

import org.springframework.stereotype.Service;
import uk.gitsoft.jobportal.entity.Users;
import uk.gitsoft.jobportal.repository.UsersRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users addNew(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        return usersRepository.save(users);
    }
    public Optional<Users> getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }
}
