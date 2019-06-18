package web.crud.system.webcrudsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import web.crud.system.webcrudsystem.models.Post;
import web.crud.system.webcrudsystem.models.PostRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public String posts(Model model){
        List<Post> posts = (List<Post>) postRepository.findAll();
        model.addAttribute("posts", posts);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("getdetails"+auth.isAuthenticated());
        System.out.println("getDetails" + auth.getName());
        return "posts";
    }

    @GetMapping("/admin/posts/create")
    public String postsCreate(Post post){
         return "posts_create";
    }


    @PostMapping("posts")
    public String addPost(@Valid Post post, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "posts_create";
        }

        postRepository.save(post);
        return "redirect:posts";
    }

    @GetMapping("posts/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("post", post);
        return "posts_update";
    }

    @PostMapping("posts/update/{id}")
    public String updateStudent(@PathVariable("id") long id, @Valid Post post, BindingResult result, Model model) {
        if (result.hasErrors()) {
            post.setId(id);
            return "posts_update";
        }

        postRepository.save(post);
        model.addAttribute("posts", postRepository.findAll());
        return "posts";
    }

    @GetMapping("posts/delete/{id}")
    public String deleteStudent(@PathVariable("id") long id, Model model) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        postRepository.delete(post);
        model.addAttribute("posts", postRepository.findAll());
        return "posts";
    }



}
