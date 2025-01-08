package tn.iit.repository;



import java.util.List;

import tn.iit.entities.User;
import tn.iit.entities.UserDto;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto>findAllUsers();
}
