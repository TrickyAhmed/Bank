package tn.iit.controller;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import tn.iit.entities.User;
import tn.iit.entities.UserDto;
import tn.iit.repository.UserService;

import java.util.List;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService){
        this.userService=userService;
    }

    //handler method to handle home page.
    @GetMapping("/home")
    public String home(){
        return "/clients";

    }

    //handler method to handle user registeration form request.

    @GetMapping("/register")
    public String showRegistrationForm(Model model){

        UserDto userDto = new UserDto();
        model.addAttribute("user",userDto); //model object is used to store data that is entered from form.
        return "register";

    }
    //handler method to handle user registration form submit request.

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result,Model model){ //Model attribute is used to extract model object which is a form data.


        User existingUser = userService.findByEmail(userDto.getEmail()); //checking if entered email already exists or not.

        if(existingUser!=null && existingUser.getEmail()!=null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email",null,"there is already an account existed with this email");
        }

        if(result.hasErrors()){
            model.addAttribute("user",userDto);
            return "/register"; // if any form has errors it will be redirected to register page only.
        }

        userService.saveUser(userDto);
        return "redirect:/register?success"; // @Valid from jakarta.validation will enable the validation fields of dto objectsto be enabled.

    }

    //handler methods for getting list of users.
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users",users);
        return "users";
    }

    //handler methods for handling login request.

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
