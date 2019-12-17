package br.gus.ifmt.blog.controller;

import br.gus.ifmt.blog.domain.Post;
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

    @PostMapping
    public ResponseEntity<String> inserirPost(@RequestBody Post post) {
        try {
            if (post != null) {
                postService.save(post);
                return new ResponseEntity<String>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<String> editar(@RequestBody Post post) {
        try {
            if (post != null) {
                postService.save(post);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirPost(@PathVariable Long id) {
        try {
            postService.deletePostById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
