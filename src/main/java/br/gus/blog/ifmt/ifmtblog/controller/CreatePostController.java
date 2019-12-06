package br.gus.blog.ifmt.ifmtblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreatePostController {

    @GetMapping("/criarpost")
    public String createPost() {
       return "criarpost";
    }
}
