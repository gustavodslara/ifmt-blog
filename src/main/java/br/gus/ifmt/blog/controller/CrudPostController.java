package br.gus.ifmt.blog.controller;

import br.gus.ifmt.blog.domain.Post;
import br.gus.ifmt.blog.sec.Login;
import br.gus.ifmt.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/postar")
public class CrudPostController {

    @Autowired
    private PostService postService;

    @PostMapping("/inserir")
    public ResponseEntity inserirPost(@RequestHeader("Authorization") String auth,
                                      @RequestBody Post newPost) {
        try {
            if (Login.isAuth(auth)) {
                if (newPost != null) {
                    postService.save(newPost);
                    return new ResponseEntity(HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
                }
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
