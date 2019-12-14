package movies.spring.data.neo4j.controller;

import movies.spring.data.neo4j.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String viewPost(@RequestParam("title")String title, Model model){
        model.addAttribute("post", postService.findByTitle(title));
        return "post";
    }

}
