package tn.iit.services;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import tn.iit.entities.Role;
import tn.iit.entities.User;
import tn.iit.entities.UserDto;
import tn.iit.repository.RoleRepository;
import tn.iit.repository.UserRepository;
import tn.iit.repository.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());  
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Password encryption

        // Recherche des rôles "ROLE_ADMIN" et "ROLE_USER"
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Role userRole = roleRepository.findByName("ROLE_USER");

        // Si le rôle "ROLE_ADMIN" n'existe pas, on le crée
        if (adminRole == null) {
            adminRole = checkRoleExists("ROLE_ADMIN");
        }

        // Si le rôle "ROLE_USER" n'existe pas, on le crée
        if (userRole == null) {
            userRole = checkRoleExists("ROLE_USER");
        }

        // Assignation des rôles à l'utilisateur
        user.setRoles(Arrays.asList(adminRole, userRole));  // Ajouter les deux rôles

        userRepository.save(user); // Sauvegarde de l'utilisateur dans la base de données
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    // Méthode pour vérifier si un rôle existe déjà, sinon le créer.
    private Role checkRoleExists(String roleName){
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }
}
