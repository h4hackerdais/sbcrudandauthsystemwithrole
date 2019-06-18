package web.crud.system.webcrudsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import web.crud.system.webcrudsystem.models.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@Transactional
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @GetMapping("register")
    public String register(User user){
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, Model model) {
        User u = userRepository.findUserByEmail(user.getEmail());

        if (u != null){
            result.rejectValue("email", "error.user", "This email already exists!");
        }

        if (result.hasErrors()) {
            return "register";
        }
         BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role role = roleRepository.findByRoles("ADMIN");
        user.addRoles(role);
        userRepository.save(user);
        return "redirect:posts";
    }

    @GetMapping("/login")
    public String login(User user){
        return "login";
    }

/*
    @PostMapping("/login")
    public String loginUser(@Valid User user, BindingResult result, Model model) {

        User user1 = userRepository.findUserByEmail(user.getEmail());

        if (result.hasErrors()) {
            return "login";
        }


        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

       boolean check = bCryptPasswordEncoder.matches(user.getPassword(), user1.getPassword());

        System.out.println("boolcheck " + check);


        return "redirect:posts";
    }


*/


}
