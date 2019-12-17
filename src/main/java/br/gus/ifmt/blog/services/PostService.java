package br.gus.ifmt.blog.services;

import br.gus.ifmt.blog.domain.Post;
import br.gus.ifmt.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Cacheable("postCache")
    @Transactional(readOnly = true)
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Collection<Post> findByTitleLike(String title) {
        return postRepository.findByTitleLike(title);
    }

    @Cacheable("postsCache")
    @Transactional(readOnly = true)
    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional
    public void save(Post newPost) {
        postRepository.save(newPost);
    }

    @Transactional
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
}
