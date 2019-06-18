package web.crud.system.webcrudsystem;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@RestController
@Controller
public class CrudController {

    @GetMapping("/")
    public String sayHello(Model m){
        List<Book> lists = Arrays.asList(
                new Book(1l, "master english", "rohan"),
                new Book(2l, "Spring boot", "sonali")
        );
        m.addAttribute("lists",lists);
        return "home";
    }

    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return Arrays.asList(
                new Book(1l,"master english","rohan" ),
                new Book(2l, "Spring boot","sonali")
        );
    }


}
