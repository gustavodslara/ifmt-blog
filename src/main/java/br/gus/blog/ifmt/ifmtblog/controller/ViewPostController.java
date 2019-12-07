package br.gus.blog.ifmt.ifmtblog.controller;

import br.gus.blog.ifmt.ifmtblog.model.Post;
import br.gus.blog.ifmt.ifmtblog.repository.MatchNodes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ViewPostController {

    @GetMapping("/visualizar")
    public String createPost(@RequestParam(name = "title", required = false, defaultValue = "") String title, Model model) {
        MatchNodes mC = new MatchNodes();
        Post post = mC.getPost(title);
        model.addAttribute("post", post);
        return "post";
    }
}
