package web.crud.system.webcrudsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import web.crud.system.webcrudsystem.models.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@Transactional
public class WebcrudsystemApplication implements CommandLineRunner {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder cryptPasswordEncoder =new BCryptPasswordEncoder();

    public static void main(String[] args) {
        SpringApplication.run(WebcrudsystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

/*

        postRepository.save(new Post("first title", "first Content",null));
        postRepository.save(new Post("second title", "second Content",null));
        postRepository.save(new Post("third title", "third Content",null));
        postRepository.save(new Post("fourth title", "fourth Content",null));
        postRepository.save(new Post("fifth title", "fifth Content",null));

        roleRepository.save(new Role("admin"));
        roleRepository.save(new Role("user"));

        User user = new User("rohan","dhiman","rohan@gmail.com","1223",1);

        Role role = roleRepository.findByRole("admin");

        user.addRoles(role);

        userRepository.save(user);

        Optional<User> u = userRepository.findById(8l);
        System.out.println("all users"+userRepository.findAll());
        System.out.println("all users"+ u.get().getRoles());

        System.out.println("pass"+ cryptPasswordEncoder.encode("123345678"));
*/

    }
}
