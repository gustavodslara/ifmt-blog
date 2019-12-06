package br.gus.blog.ifmt.ifmtblog.controller;

import br.gus.blog.ifmt.ifmtblog.model.Post;
import br.gus.blog.ifmt.ifmtblog.repository.MatchNodes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @GetMapping("/")
    public String createPost(Model model) {
        MatchNodes mC = new MatchNodes();
        List<Post> listaPosts = mC.getPosts();
        model.addAttribute("listaPosts", listaPosts);
        return "index";
    }
}
