package br.gus.blog.ifmt.ifmtblog.controller;

import br.gus.blog.ifmt.ifmtblog.model.Post;
import br.gus.blog.ifmt.ifmtblog.repository.CreateNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

    @GetMapping("/postar")
    public String post(@RequestParam(name = "user", required = false, defaultValue = "") String user,
                       @RequestParam(name = "pwd", required = false, defaultValue = "") String pwd,
                       @RequestParam(name = "title", required = false, defaultValue = "") String title,
                       @RequestParam(name = "content", required = false, defaultValue = "") String content) {
        if (user != null && user.equals("root") && pwd != null && pwd.equals("root")) {
            Post post = new Post(title, content);
            if (post != null && post.getTitle() != null && post.getContent() != null) {
                CreateNode c = new CreateNode();
                c.createPost(post);
            }
            return "posted";
        } else {
            return "error";
        }
    }
}
