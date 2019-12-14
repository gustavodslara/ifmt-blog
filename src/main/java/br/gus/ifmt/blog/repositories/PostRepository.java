package br.gus.ifmt.blog.repositories;

import br.gus.ifmt.blog.domain.Post;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "posts", path = "posts")
public interface PostRepository extends Neo4jRepository<Post, Long> {

    Post findByTitle(@Param("title") String title);

    Collection<Post> findByTitleLike(@Param("title") String title);

}
