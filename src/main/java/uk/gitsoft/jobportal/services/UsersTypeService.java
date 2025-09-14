package uk.gitsoft.jobportal.services;

import uk.gitsoft.jobportal.entity.UserType;
import uk.gitsoft.jobportal.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {

    private final UsersTypeRepository usersTypeRepository;

    @Autowired
    public UsersTypeService(UsersTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }

    public List<UserType> getAll(){
        return usersTypeRepository.findAll();
    }
}
