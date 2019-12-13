package movies.spring.data.neo4j.services;

import movies.spring.data.neo4j.domain.Post;
import movies.spring.data.neo4j.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Cacheable("postCache")
    @Transactional(readOnly = true)
    public Post findByTitle(String title) {
        return postRepository.findByTitle(title);
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


}
