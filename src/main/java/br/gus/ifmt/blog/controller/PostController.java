package br.gus.ifmt.blog.controller;

import br.gus.ifmt.blog.domain.Post;
import br.gus.ifmt.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String getPosts(Model model) {
        model.addAttribute("listaPosts", postService.findAll());
        return "index";
    }

    @GetMapping("/visualizar")
    public String viewPost(@RequestParam("id") Long id, Model model) {
        Optional<Post> post = postService.findById(id);
        if (post.isPresent()) {
            post.get().setContent(post.get().getContent().replace("\n", "<br/>"));
        }
        model.addAttribute("post", post.isPresent() ? post.get() : "");
        return "post";
    }

    @GetMapping("/postar")
    public String postar() {
        return "createpost";
    }

}
